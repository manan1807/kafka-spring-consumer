package com.example.libraryeventlistener.repo;

import org.springframework.data.repository.CrudRepository;

import com.example.libraryeventlistener.entity.RecordManager;

public interface RecordMetaDataRepo extends CrudRepository<RecordManager, String>{

}
