package com.example.libraryeventlistener.consumer;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.example.libraryeventlistener.service.LibraryEventService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class LibraryEventsRetryConsumer {

	@Value("${topics.retry}")
	private String retryTopic;

	@Autowired
	private LibraryEventService eventService;

	@KafkaListener(topics = { "${topics.retry}" }, concurrency = "3", groupId = "retry-listener-group")
	public void message(ConsumerRecord<String, String> record) throws JsonMappingException, JsonProcessingException {

		log.debug("****Consumer Record In Retry Consumer:(key= {}, value= {}, partition= {}, offSet= {}, consumer={})\n", record.key(),
				record.value(), record.partition(), record.offset());
		eventService.processLibraryEvent(record);

	}

}
