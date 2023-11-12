package com.desco.billcollection.model;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
//@Table(name = "SMS_QUEUE_TBL_TEST")
@Table(name = "SMS_QUEUE" , schema = "OAP")
public class SmsModel {
	@Id
	@Column(name = "ID")
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Integer id;

	@Column(name = "MOBILE_NO")
	private String mobileNo;

	@Column(name = "SMS_TEXT")
	private String smsTexT;
	
	@Column(name = "HANDSET_DELIVERY")
	private String handsetDelivery;
	
	@Column(name = "SENT_DATE")
	private LocalDateTime sentDate;
	
	@Column(name = "SENT_STATUS")
	private String sentStatus;
	
	@Column(name = "TRACKING_NUMBER")
	private String trackingNumber;
	

	@Column(name = "OPERATOR_ID")
	private String operatorId;

	public String getOperatorId() {
		return operatorId;
	}

	public void setOperatorId(String operatorId) {
		this.operatorId = operatorId;
	}

	public String getHandsetDelivery() {
		return handsetDelivery;
	}

	public void setHandsetDelivery(String handsetDelivery) {
		this.handsetDelivery = handsetDelivery;
	}

	public LocalDateTime getSentDate() {
		return sentDate;
	}

	public void setSentDate(LocalDateTime sentDate) {
		this.sentDate = sentDate;
	}

	public String getSentStatus() {
		return sentStatus;
	}

	public void setSentStatus(String sentStatus) {
		this.sentStatus = sentStatus;
	}

	public String getTrackingNumber() {
		return trackingNumber;
	}

	public void setTrackingNumber(String trackingNumber) {
		this.trackingNumber = trackingNumber;
	}

	public SmsModel() {
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getMobileNo() {
		return mobileNo;
	}

	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}

	public String getSmsTexT() {
		 return smsTexT;
	}

	public void setSmsTexT(String smsTexT) {
		this.smsTexT = smsTexT;
	}
}
