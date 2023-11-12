package com.desco.billcollection.service;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Logger;

import org.springframework.scheduling.annotation.Async;

public class KeyGeneration {
	//DESCO User ID =31
	//POSTPAIDBC User ID =35
	private static final int userID = 35;
	private final static Logger logger = Logger.getLogger("ExceptionLogging");
	@Async
	public String getPKey(long currentTime) {		 
		// unique 16 digit number
		String P_KEY = "100" + currentTime;
		return P_KEY;
	}
	@Async
	public String getAKey(long currentTime) {	
		String A_KEY = getMd5("100" + (currentTime + userID) + "****");
		return A_KEY;
	}
	@Async
	public static String getMd5(String input) {
		try {

			// Static getInstance method is called with hashing MD5
			MessageDigest md = MessageDigest.getInstance("MD5");

			// digest() method is called to calculate message digest
			// of an input digest() return array of byte
			byte[] messageDigest = md.digest(input.getBytes());

			// Convert byte array into signum representation
			BigInteger no = new BigInteger(1, messageDigest);

			// Convert message digest into hex value
			String hashtext = no.toString(16);
			while (hashtext.length() < 32) {
				hashtext = "0" + hashtext;
			}
			return hashtext;
		}

		// For specifying wrong message digest algorithms
		catch (NoSuchAlgorithmException e) {
			logger.warning("Exception while calculating MD5 value : "+e.getMessage());
			throw new RuntimeException(e);
		}
	}
}
