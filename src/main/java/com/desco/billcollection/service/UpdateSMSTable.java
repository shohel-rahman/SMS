package com.desco.billcollection.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.desco.billcollection.model.SmsModel;
import com.desco.billcollection.repository.SmsRepository;

@Service
public class UpdateSMSTable {
	@Autowired
	SmsRepository smsRepo;
	private final static Logger logger = Logger.getLogger("");

	public int updateSentStatus(List<SmsModel> smsListtoUpdate, String status) {
		int noOfSms = smsListtoUpdate.size();		
		logger.info("Updation of " + noOfSms + " SMS with " + status + " started at: " + LocalDateTime.now() + " by "
				+ Thread.currentThread().getName());
		int noOfRowsUpdated = smsRepo.updateSentStatusById(smsListtoUpdate, status, LocalDateTime.now());
		if (noOfRowsUpdated == noOfSms) {			
			logger.info("Updated " + noOfRowsUpdated + " rows at: " + LocalDateTime.now() + " by "
					+ Thread.currentThread().getName());
		}else {			
			logger.warning("Updated " + noOfRowsUpdated + " rows at: " + LocalDateTime.now() + " by "
					+ Thread.currentThread().getName());
		}		
		return noOfRowsUpdated;
	}
}