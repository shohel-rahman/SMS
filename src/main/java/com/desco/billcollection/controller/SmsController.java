package com.desco.billcollection.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.desco.billcollection.service.SmsService;

@RestController
@RequestMapping("/sms/api")
@Configuration
@EnableScheduling
public class SmsController {
	@Autowired
	SmsService bulkSmsService = new SmsService();
	
//	@Scheduled(cron = " 'sec' 'min' 'hour' 'day of the month'   'month' 'days of week' ")	

	@GetMapping("/send/bulk")
	@Scheduled(cron = "*/30 * 7-22 * * *")
	public void sendBatchBulkSms() {
		bulkSmsService.bulkSms();
	}
}