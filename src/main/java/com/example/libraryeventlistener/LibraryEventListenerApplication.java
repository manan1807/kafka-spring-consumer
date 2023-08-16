package com.example.libraryeventlistener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

//import com.example.libraryeventlistener.consumer.LibraryEventConsumerApproach2;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;



@SpringBootApplication
public class LibraryEventListenerApplication {
//
//	@Autowired
//	private LibraryEventConsumerApproach2 obj;
	
	public static void main(String[] args) throws JsonMappingException, JsonProcessingException, InterruptedException {
//		ApplicationContext context = SpringApplication.run(LibraryEventListenerApplication.class, args);
//		LibraryEventListenerApplication mainObj = context.getBean(LibraryEventListenerApplication.class);
//		mainObj.call();
		SpringApplication.run(LibraryEventListenerApplication.class, args);
	}

//	private void call() throws JsonMappingException, JsonProcessingException, InterruptedException {
//		obj.runConsumer();
//		
//	}

}
