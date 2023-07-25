package com.example.libraryeventlistener.service;

import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.stereotype.Service;

import com.example.libraryeventlistener.config.CustomConsumerRebalanceListener;
import com.example.libraryeventlistener.entity.LibraryEvent;
import com.example.libraryeventlistener.entity.PartOff;
import com.example.libraryeventlistener.entity.RecordManager;
import com.example.libraryeventlistener.repo.LibraryEventsRepository;
import com.example.libraryeventlistener.repo.RecordMetaDataRepo;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class LibraryEventService {
	@Autowired
	private ObjectMapper mapper;
	@Autowired
	private LibraryEventsRepository eventsRepository;
	@Autowired
	private RecordMetaDataRepo metaDataRepo;
	@Autowired
	private CustomConsumerRebalanceListener rebalanceListener;

	public void processLibraryEvent(ConsumerRecord<String, String> record)
			throws JsonMappingException, JsonProcessingException {
		log.debug("*******Inside processLibraryEvent method");

		var libraryEvent = mapper.readValue(record.value(), LibraryEvent.class);
		log.debug("*****LibraryEvent: {}", libraryEvent);

		switch (libraryEvent.getEventType()) {
		case NEW:
		case UPDATE:
		case CANCEL:
		case PAYMENT:
			save(libraryEvent);
			saveRecordMetaData(record, libraryEvent, null);
			break;
		default:
			log.debug("**** Invalid Library Event Type");

		}
	}
	
	public void processLibraryEvent(ConsumerRecords<String, String> records, String consumerId)
			throws JsonMappingException, JsonProcessingException {
		log.debug("*******Inside processLibraryEvent method");

		for (ConsumerRecord<String, String> record : records) {

		var libraryEvent = mapper.readValue(record.value(), LibraryEvent.class);
		log.debug("*****LibraryEvent: {}", libraryEvent);

		switch (libraryEvent.getEventType()) {
		case NEW:
		case UPDATE:
		case CANCEL:
		case PAYMENT:
			save(libraryEvent);
			saveRecordMetaData(record, libraryEvent, consumerId);
			break;
		default:
			log.debug("**** Invalid Library Event Type");

		}
		}
	}

	private void saveRecordMetaData(ConsumerRecord<String, String> record, LibraryEvent libraryEvent, String consumerId)
			throws JsonProcessingException {
		var partOff = new PartOff(mapper.writeValueAsString(record.partition()),
				mapper.writeValueAsString(record.offset()));
		libraryEvent.getBook().setLibraryEvent(libraryEvent);
		metaDataRepo.save(new RecordManager(partOff, record.topic(), libraryEvent.getEventType(), consumerId, libraryEvent));

	}

	private void save(LibraryEvent libraryEvent) {
		libraryEvent.getBook().setLibraryEvent(libraryEvent);
		eventsRepository.save(libraryEvent);
		log.debug("*****Successfully persisted the library event: {}", libraryEvent);
	}

}
