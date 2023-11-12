package com.desco.billcollection.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.desco.billcollection.model.SmsModel;
import com.desco.billcollection.repository.SmsRepository;

@Service
public class GetSMS {
	@Autowired
	SmsRepository smsRepo;
	@Autowired
	UpdateSMSTable updateSmsTableService = new UpdateSMSTable();
	
	private final static Logger logger = Logger.getLogger("");
	@Async
	public CompletableFuture<List<SmsModel>> getSmsFromDB()
	{
		CompletableFuture<List<SmsModel>> fullSmsList = new CompletableFuture<List<SmsModel>>();
		logger.info("Pulling Strated : " + LocalDateTime.now()+ " by " + Thread.currentThread().getName());		
		int noOfSmsPulled = 0;		
		try {
			fullSmsList = smsRepo.getSms();			
			noOfSmsPulled = fullSmsList.get().size();
			logger.info(noOfSmsPulled + " SMS Pulled at: " + LocalDateTime.now()+ " by " + Thread.currentThread().getName());
		}catch (Exception ex) {
			logger.warning("Exception While Pulling SMS from DB : " + ex.getMessage());			
		}
		
		if(noOfSmsPulled > 0 ) {
			try {
				int smsUpdated = updateSmsTableService.updateSentStatus(fullSmsList.get(),"P");				
				if(smsUpdated != noOfSmsPulled) {
					fullSmsList.get().clear();
				}
			} catch (InterruptedException | ExecutionException e) {
				logger.warning("Exception While Updating Sent Status : " + e.getMessage());	
			}
		}
		return fullSmsList;
	}
}