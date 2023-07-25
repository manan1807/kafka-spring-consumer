package com.example.libraryeventlistener.repo;

import org.springframework.data.repository.CrudRepository;

import com.example.libraryeventlistener.entity.LibraryEvent;

public interface LibraryEventsRepository extends CrudRepository<LibraryEvent, String>{

}
