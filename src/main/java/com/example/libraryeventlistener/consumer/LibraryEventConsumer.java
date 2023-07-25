package com.example.libraryeventlistener.consumer;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.example.libraryeventlistener.service.LibraryEventService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

import lombok.extern.slf4j.Slf4j;

//@Component
@Slf4j
public class LibraryEventConsumer {

	@Autowired
	private LibraryEventService eventService;
	
	@KafkaListener(topics = {"library-events"}, concurrency = "3", groupId = "library-events-listener-group")
	public void message(ConsumerRecord<String, String> record ) throws JsonMappingException, JsonProcessingException {
		
		log.debug("Consumed record: {} ", record);
		log.debug("****Consumer Record:(key= {}, value= {}, partition= {}, offSet= {}, consumer={})\n", record.key(), 
				record.value(), record.partition(), record.offset()
		);
		eventService.processLibraryEvent(record);
		
	}
	 
}
