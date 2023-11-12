package com.desco.billcollection.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.desco.billcollection.model.ResponseModel;
import com.desco.billcollection.model.SmsModel;
import com.desco.billcollection.repository.ResponseRepository;
import com.desco.billcollection.repository.SmsRepository;

@Service
public class DecodeSmsResponse {
	@Autowired
	ResponseRepository ResponseRepo;
	@Autowired
	SmsRepository smsRepo;
	
	private final static Logger logger = Logger.getLogger("");

	// Collecting Bulk and Individual Responses and storing them
	@Async
	public void responseCollector(ResponseModel blkResponse, String providedCid, List<SmsModel> smsList) {
		Queue<Integer> smsIDQueue = new ArrayBlockingQueue<Integer>(100);
		Queue<SmsModel> smsResponseQueue = new ArrayBlockingQueue<SmsModel>(100);	
		Queue<String> detailsQueue;
		Queue<String> processingDetailsQueue;
		logger.info("Decoding response started at : " + LocalDateTime.now() + " by " + Thread.currentThread().getName());
		for (SmsModel sms : smsList) {
			smsIDQueue.add(sms.getId());
		}

		if (blkResponse.getScode().equals("1") && blkResponse.getCid().equals(providedCid)) {

			// Extracting Details and Processing Details parameter value from the response
			detailsQueue = detailsResponseDecoder(blkResponse.getDetails());
			processingDetailsQueue = processingDetailsResponseDecoder(blkResponse.getProcessing_details());

			// Extracting Details and Processing Details parameter value from the response
			// for Bulk Response
			blkResponse.setDetails(blkResponse.getDetails().substring(0, blkResponse.getDetails().indexOf("|")));
			blkResponse.setProcessing_details(
					blkResponse.getProcessing_details().substring(0, blkResponse.getProcessing_details().indexOf("|")));
			blkResponse.setCreateDate(LocalDateTime.now());

			// Saving the Bulk Response for insertion in Bulk Response Table
			while (processingDetailsQueue.size() > 0) {
				String processingCode = processingDetailsQueue.poll();

				SmsModel individualSmsResponse = new SmsModel();
				individualSmsResponse.setId(smsIDQueue.poll());
				individualSmsResponse.setSentDate(LocalDateTime.now());
				individualSmsResponse.setHandsetDelivery(processingCode);

				String operatorId = "";
				if (processingCode.equals("RS")) {
					operatorId = detailsQueue.poll();
					individualSmsResponse.setOperatorId(operatorId);
				}
				smsResponseQueue.add(individualSmsResponse);
			}
		} else {
			smsIDQueue.clear();
			blkResponse.setCreateDate(LocalDateTime.now());
		}
		logger.info("Decoding response ended at : " + LocalDateTime.now() + " by " + Thread.currentThread().getName());
		
		logger.info("Inserting a Bulk Response row at : " + LocalDateTime.now() + " by " + Thread.currentThread().getName());
		ResponseRepo.insertBulkResponse(blkResponse.getCid(), blkResponse.getChunk_id(), blkResponse.getStatus(),
				blkResponse.getScode(), blkResponse.getDetails(), blkResponse.getProcessing_details(),
				blkResponse.getCredit_deducted(), blkResponse.getCurrent_credit(),
				blkResponse.getCurrent_credit_master(), blkResponse.getcredit_inheritance(),
				blkResponse.getCreateDate());
		logger.info("Inserting finished at : " + LocalDateTime.now() + " by " + Thread.currentThread().getName());

		// SMS Queue Table is updated here.
		saveResponse(smsResponseQueue, smsList.size());
	}

	@Transactional
	@Async
	public void saveResponse(Queue<SmsModel> smsResponseToSave, int chunkSize) {
		logger.info("Updating " + chunkSize + " rows of SMS_QUEUE started at : " + LocalDateTime.now() + " by " + Thread.currentThread().getName());
		int noOfResponse = smsResponseToSave.size();
		while (noOfResponse > 0) {
			SmsModel smsResponse = smsResponseToSave.poll();
			smsRepo.updateSmsQueueTable(LocalDateTime.now(), smsResponse.getHandsetDelivery(),
					smsResponse.getOperatorId(), smsResponse.getId());
			noOfResponse--;
		}
		logger.info("Updated " + chunkSize + " rows finished at : " + LocalDateTime.now() + " by " + Thread.currentThread().getName());		
	}

	@Async
	public Queue<String> detailsResponseDecoder(String line) {
		Queue<String> Q = new ArrayBlockingQueue<String>(100);
		int start = line.indexOf("=");
		int end = line.indexOf("|", start);
		int noOfRecords = 0;
		if (start > 0 && end > start) {
			noOfRecords = Integer.parseInt(line.substring(start + 1, end));
			for (int i = 0; i < noOfRecords; i++) {
				start = line.indexOf("ID=", end) + 3;
				end = line.indexOf(",", start);
				String ID = line.substring(start, end);
				Q.add(ID);
			}
		}
		return Q;
	}

	@Async
	public Queue<String> processingDetailsResponseDecoder(String line) {
		Queue<String> Q = new ArrayBlockingQueue<String>(100);
		String result = "";
		int start = line.indexOf("=");
		int end = line.indexOf("|", start);
		int noOfRecords = 0;
		if (start > 0 && end > start) {
			noOfRecords = Integer.parseInt(line.substring(start + 1, end));
			for (int i = 1; i < noOfRecords; i++) {
				start = end;
				end = line.indexOf("|", start + 1);
				result = line.substring(start + 1, end);
				Q.add(result);
			}
		}
		result = line.substring(end + 1, line.length());
		Q.add(result);
		return Q;
	}

}
