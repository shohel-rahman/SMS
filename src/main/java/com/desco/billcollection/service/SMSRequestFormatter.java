package com.desco.billcollection.service;

public class SMSRequestFormatter {
	String op;
	String chunk;
	String user;
	String pass;
	String servername;
	String smsclass;
	String sms;
	String mobile;
	String charset;
	String validity;
	String a_key;
	String p_key;
	public String cid;

	public String getCid() {
		return cid;
	}

	public void setCid(String cid) {
		this.cid = cid;
	}

	public String getOp() {
		return op;
	}

	public void setOp(String op) {
		this.op = op;
	}

	public String getChunk() {
		return chunk;
	}

	public void setChunk(String chunk) {
		this.chunk = chunk;
	}

	public String getUser() {
		return user;
	}
	
	public void setUser(String user) {
		this.user = user;
	}

	public String getPass() {
		return pass;
	}

	public void setPass(String pass) {
		this.pass = pass;
	}

	public String getServername() {
		return servername;
	}

	public void setServername(String servername) {
		this.servername = servername;
	}

	public String getSmsclass() {
		return smsclass;
	}

	public void setSmsclass(String smsclass) {
		this.smsclass = smsclass;
	}

	public String getSms() {
		return sms;
	}

	public void setSms(String sms) {
		this.sms = sms;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		
		this.mobile = mobile;
	}

	public String getCharset() {
		return charset;
	}

	public void setCharset(String charset, int repeatitions) {
			if(repeatitions <= 1) {
				this.charset = charset;
			}
			else {
				this.charset = charset; 
				for(int i= 1; i<repeatitions; i++) {
					this.charset += "|" + charset;
				}
			}
		}

	public String getValidity() {
		return validity;
	}

	public void setValidity(String validity) {
		this.validity = validity;
	}

	public String getA_key() {
		return a_key;
	}

	public void setA_key(String a_key) {
		this.a_key = a_key;
	}

	public String getP_key() {
		return p_key;
	}

	public void setP_key(String p_key) {
		this.p_key = p_key;
	}
}
