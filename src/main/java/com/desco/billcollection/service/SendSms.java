package com.desco.billcollection.service;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.concurrent.CompletableFuture;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;

import com.desco.billcollection.model.ResponseModel;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class SendSms {
	@Value("${sms.vendor.url}")
	String baseUrl;
	private final static Logger logger = Logger.getLogger("");
	
	@Async
	public CompletableFuture<ResponseModel> callSendSmsApi(String jsonData, int chunkSize) {
		// Setting up the HTTP Request Header
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		
		// Post For Object Method
		HttpEntity<String> request = new HttpEntity<String>(jsonData, headers);
		ObjectMapper jsonMapper = new ObjectMapper();
		String teletalkResponseJsonStr = "";
		ResponseModel blkResponse = new ResponseModel();
		try {
			logger.info("Strat API Calling with " + chunkSize + " SMS at : " + LocalDateTime.now() + " by " + Thread.currentThread().getName());
			teletalkResponseJsonStr = restTemplate.postForObject(baseUrl, request, String.class);
			logger.info("Resoponse Received at : " + LocalDateTime.now() + " by " + Thread.currentThread().getName());
			blkResponse = jsonMapper.readValue(teletalkResponseJsonStr, ResponseModel.class);
			return CompletableFuture.completedFuture(blkResponse);
		} catch (Exception e) {
			logger.warning("Exception While Calling TBL SMS API by " + Thread.currentThread().getName() + e.getMessage());			
			return CompletableFuture.completedFuture(blkResponse);
		}		
	}
}
