package com.desco.billcollection.service;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.desco.billcollection.model.ResponseModel;
import com.desco.billcollection.model.SmsModel;
import com.desco.billcollection.repository.SmsRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class SmsService {

	@Autowired
	GetSMS getSmsService = new GetSMS();
	@Autowired
	UpdateSMSTable updateSmsTableService = new UpdateSMSTable();
	@Autowired
	SmsGeneration smsGenerateService = new SmsGeneration();
	@Autowired
	SendSms sendSmsService = new SendSms();
	@Autowired
	DecodeSmsResponse responseDecodingService = new DecodeSmsResponse();
	@Autowired
	SmsRepository smsRepo;
	private final static Logger logger = Logger.getLogger("");


	public void bulkSms() {
		
		int pendingSms = notSentSmsCount();		
		if (pendingSms >= 50 ) {			
			CompletableFuture<List<SmsModel>> smsPullThread1 = getSmsService.getSmsFromDB();
			try {
				List<SmsModel> thrd1SmsList = smsPullThread1.get();
				if (thrd1SmsList.size() > 0) {					
					CompletableFuture<String> frmtedSmsJsonThrd1 = smsGenerateService.smsGenerator(thrd1SmsList);
					CompletableFuture<ResponseModel> smsSendTrhd1 = sendSmsService
							.callSendSmsApi(frmtedSmsJsonThrd1.get(), thrd1SmsList.size());
					ObjectMapper jsonMapper = new ObjectMapper();
					responseDecodingService.responseCollector(smsSendTrhd1.get(),
							jsonMapper.readValue(frmtedSmsJsonThrd1.get(), SMSRequestFormatter.class).cid,
							thrd1SmsList);
				} else {
					logger.info("No SMS Pulled from DB");
				}
			} catch (InterruptedException | ExecutionException | JsonProcessingException e) {
				logger.warning("Exception in SmsService Class : " + e.getMessage());
			}
		} else if(pendingSms ==0) {
			logger.info("No pending SMS to be sent found.");
		} else if(pendingSms < 50 && pendingSms > 0) {
			logger.info(pendingSms + " valid pending SMS found, will be sent in next cycle.This cycle is completed.");
		}
	}

	public int notSentSmsCount() {
		return smsRepo.pendingSmsCount();
	}
}
