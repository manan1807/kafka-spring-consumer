package com.example.libraryeventlistener.consumer;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.listener.AcknowledgingMessageListener;
import org.springframework.kafka.support.Acknowledgment;

import lombok.extern.slf4j.Slf4j;

//@Component
@Slf4j
public class LibraryEventConsumerManualOffet implements AcknowledgingMessageListener<String, String>{

	
//	@KafkaListener(topics = {"library-events"})
//	public void message(ConsumerRecord<Integer, String> record) {
//		
//		log.debug("Consumed revord: {} ", record);
//		
//	}

	@Override
	@KafkaListener(topics = {"library-events"})
	public void onMessage(ConsumerRecord<String, String> record, Acknowledgment acknowledgment) {
		log.debug("Consumed revord: {} ", record);
		acknowledgment.acknowledge();
		
	}
	
}
