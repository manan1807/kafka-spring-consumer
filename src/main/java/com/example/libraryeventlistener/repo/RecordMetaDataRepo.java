package com.example.libraryeventlistener.repo;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.example.libraryeventlistener.entity.RecordManager;

public interface RecordMetaDataRepo extends CrudRepository<RecordManager, String>{
	
	
	@Query(nativeQuery = true, name = "SELECT offset_number FROM RECORD_MANAGER  where partition_number = ?1  order by  offset_number desc LIMIT 1")
	String findByNumberOffsetNumber(String i);

}
