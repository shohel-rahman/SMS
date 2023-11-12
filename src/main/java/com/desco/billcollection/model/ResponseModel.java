package com.desco.billcollection.model;

import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
//@Table(name = "BULK_RESPONSE")
@Table(name = "BULK_RESPONSE", schema = "OAP")
public class ResponseModel {
	@Id
	@Column(name = "CID")
	String cid;
	String chunk_id;
	String status;
	String scode;
	String details;
	String server;
	String sms_class;
	String processing_details;
	String credit_deducted;
	String current_credit;
	String credit_inheritance;
	LocalDateTime create_date;
	String current_credit_master;

	public String getCurrent_credit_master() {
		return current_credit_master;
	}

	public void setCurrent_credit_master(String current_credit_master) {
		this.current_credit_master = current_credit_master;
	}

	public LocalDateTime getCreateDate() {
		return create_date;
	}

	public void setCreateDate(LocalDateTime createDate) {
		this.create_date = createDate;
	}

	public String getCid() {
		return cid;
	}

	public void setCid(String cid) {
		this.cid = cid;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getScode() {
		return scode;
	}

	public void setScode(String scode) {
		this.scode = scode;
	}

	public String getDetails() {
		return details;
	}

	public void setDetails(String details) {
		this.details = details;
	}

	public String getServer() {
		return server;
	}

	public void setServer(String server) {
		this.server = server;
	}

	public String getSms_class() {
		return sms_class;
	}

	public void setSms_class(String sms_class) {
		this.sms_class = sms_class;
	}

	public String getProcessing_details() {
		return processing_details;
	}

	public void setProcessing_details(String processing_details) {
		this.processing_details = processing_details;
	}

	public String getCredit_deducted() {
		return credit_deducted;
	}

	public void setCredit_deducted(String credit_deducted) {
		this.credit_deducted = credit_deducted;
	}

	public String getCurrent_credit() {
		return current_credit;
	}

	public void setCurrent_credit(String current_credit) {
		this.current_credit = current_credit;
	}

	public String getcredit_inheritance() {
		return credit_inheritance;
	}

	public void setcredit_inheritance(String credit_inheritance) {
		this.credit_inheritance = credit_inheritance;
	}

	public String getChunk_id() {
		return chunk_id;
	}

	public void setChunk_id(String chunk_id) {
		this.chunk_id = chunk_id;
	}

	public ResponseModel() {}

}
