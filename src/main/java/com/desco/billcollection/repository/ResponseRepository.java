package com.desco.billcollection.repository;

import java.time.LocalDateTime;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.desco.billcollection.model.ResponseModel;


@Repository
public interface ResponseRepository extends JpaRepository<ResponseModel, String>{
	String customInsertQuery = "INSERT INTO OAP.BULK_RESPONSE (cid,chunk_id,status,scode,details,processing_details,credit_deducted,current_credit,current_credit_master,credit_inheritance,create_date)";
//	String customInsertQuery = "INSERT INTO BULK_RESPONSE (cid,chunk_id,status,scode,details,processing_details,credit_deducted,current_credit,current_credit_master,credit_inheritance,create_date)";
	@Modifying
	@Query(value = customInsertQuery
			+ " VALUES(:cid,:chunk_id,:status,:scode,:details,:processing_details,:credit_deducted,:current_credit,:current_credit_master,:credit_inheritance,:create_date)", nativeQuery = true)
	@Transactional
	public void insertBulkResponse(@Param("cid") String cId, @Param("chunk_id") String chunkId,
			@Param("status") String status, @Param("scode") String sCode, @Param("details") String details,
			@Param("processing_details") String processingDetails, @Param("credit_deducted") String creditDeducted,
			@Param("current_credit") String currentCredit, @Param("current_credit_master") String currentCreditMaster,
			@Param("credit_inheritance") String creditInhertance, @Param("create_date") LocalDateTime create_date);

}
