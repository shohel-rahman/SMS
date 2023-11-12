package com.desco.billcollection.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.desco.billcollection.model.SmsModel;


@Repository
public interface SmsRepository extends JpaRepository<SmsModel, Integer>{
	String pendingSmsCountQuery = "SELECT COUNT(*) FROM OAP.SMS_QUEUE WHERE SENT_STATUS = 'N' AND ( LENGTH(TRIM(mobile_no)) = 11 OR LENGTH(TRIM(mobile_no)) = 13)" + 
			" AND (TRIM(mobile_no) LIKE '88013%' OR TRIM(mobile_no) LIKE '88014%' OR TRIM(mobile_no) LIKE '88015%' OR TRIM(mobile_no) LIKE '88016%'" + 
			" OR TRIM(mobile_no) LIKE '88017%' OR TRIM(mobile_no) LIKE '88018%' OR TRIM(mobile_no) LIKE '88019%' OR TRIM(mobile_no) LIKE '013%'" + 
			" OR TRIM(mobile_no) LIKE '014%' OR TRIM(mobile_no) LIKE '015%' OR TRIM(mobile_no) LIKE '016%' OR TRIM(mobile_no) LIKE '017%'" +
			" OR TRIM(mobile_no) LIKE '018%' OR TRIM(mobile_no) LIKE '019%')";
//	String pendingSmsCountQuery = "SELECT COUNT(*) FROM SMS_QUEUE_TBL_TEST WHERE SENT_STATUS = 'N'";
	@Query(value = pendingSmsCountQuery, nativeQuery = true)
	int pendingSmsCount();
	
	String getSmsQuery = "SELECT * FROM OAP.SMS_QUEUE WHERE SENT_STATUS = 'N' AND ( LENGTH(TRIM(mobile_no)) = 11 OR LENGTH(TRIM(mobile_no)) = 13)" + 
			" AND (TRIM(mobile_no) LIKE '88013%' OR TRIM(mobile_no) LIKE '88014%' OR TRIM(mobile_no) LIKE '88015%' OR TRIM(mobile_no) LIKE '88016%'" + 
			" OR TRIM(mobile_no) LIKE '88017%' OR TRIM(mobile_no) LIKE '88018%' OR TRIM(mobile_no) LIKE '88019%' OR TRIM(mobile_no) LIKE '013%'" + 
			" OR TRIM(mobile_no) LIKE '014%' OR TRIM(mobile_no) LIKE '015%' OR TRIM(mobile_no) LIKE '016%' OR TRIM(mobile_no) LIKE '017%'" +
			" OR TRIM(mobile_no) LIKE '018%' OR TRIM(mobile_no) LIKE '019%') " +
			" ORDER BY ID ASC FETCH NEXT 100 ROWS ONLY";
//	String getSmsQuery = "SELECT * FROM SMS_QUEUE_TBL_TEST WHERE SENT_STATUS = 'N' AND ROWNUM < 101 ORDER BY ID ASC";
	@Query(value = getSmsQuery, nativeQuery = true)
	CompletableFuture<List<SmsModel>> getSms();
	
	String sentStatusUpdateByIdQuery = "UPDATE OAP.SMS_QUEUE SET SENT_STATUS = :STATUS, SENT_DATE = :SENT_DATE WHERE SENT_STATUS = 'N' AND ID IN (:ID) ";
//	String sentStatusUpdateByIdQuery = "UPDATE SMS_QUEUE_TBL_TEST SET SENT_STATUS = :STATUS, SENT_DATE = :SENT_DATE WHERE SENT_STATUS = 'N' AND ID IN (:ID)";
	@Modifying
	@Transactional
	@Query(value = sentStatusUpdateByIdQuery, nativeQuery = true)	
	int updateSentStatusById(@Param("ID") List<SmsModel> id, @Param("STATUS") String status, @Param("SENT_DATE") LocalDateTime sentDate);

	String tableUpdateQuery = "UPDATE OAP.SMS_QUEUE SET SENT_STATUS = 'Y',SENT_DATE = :SENT_DATE, HANDSET_DELIVERY = :HANDSET_DELIVERY, OPERATOR_ID = :OPERATOR_ID where ID = :ID";
//	String tableUpdateQuery = "UPDATE SMS_QUEUE_TBL_TEST SET SENT_STATUS = 'YT', SENT_DATE = :SENT_DATE, HANDSET_DELIVERY = :HANDSET_DELIVERY, OPERATOR_ID = :OPERATOR_ID WHERE ID = :ID";
	@Modifying
	@Query(value = tableUpdateQuery, nativeQuery = true)
	@Transactional
	void updateSmsQueueTable(@Param("SENT_DATE") LocalDateTime sentDate,
			@Param("HANDSET_DELIVERY") String handsetDelivery, @Param("OPERATOR_ID") String operatorID, @Param("ID") Integer smsID);
}
