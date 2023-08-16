package com.example.libraryeventlistener;

import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.kafka.config.KafkaListenerEndpointRegistry;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.listener.MessageListenerContainer;
import org.springframework.kafka.test.EmbeddedKafkaBroker;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.kafka.test.utils.ContainerTestUtils;
import org.springframework.test.context.TestPropertySource;

import com.example.libraryeventlistener.consumer.LibraryEventConsumer;
import com.example.libraryeventlistener.service.LibraryEventService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;


@SpringBootTest
@EmbeddedKafka(topics = "library-events", partitions = 3)
@TestPropertySource(properties = { "spring.kafka.producer.bootstrap-servers=${spring.embedded.kafka.brokers}" 
		, "spring.kafka.consumer.bootstrap-servers=${spring.embedded.kafka.brokers}"
})
public class LibraryEventsConsumerIntgTest {
	
	
	@Autowired
	EmbeddedKafkaBroker broker;
	
	@Autowired
	KafkaTemplate<String, String> kafkaTemplate;
	
	@Autowired
	KafkaListenerEndpointRegistry endpointRegistry;
	
	@SpyBean
	LibraryEventConsumer consumer;
	
	@SpyBean
	LibraryEventService eventService;
	
	@BeforeEach
	void setUp() {
		for(MessageListenerContainer container : endpointRegistry.getAllListenerContainers()) {
			ContainerTestUtils.waitForAssignment(container, broker.getPartitionsPerTopic());
		}
	}
	
	@Test
	void publishLibraryEvent() throws InterruptedException, ExecutionException, JsonMappingException, JsonProcessingException {
		String json = "{\"libraryEventId\":null,\"eventType\":\"UPDATE\",\"book\":{\"bookId\":101,\"bookName\":\"Kafka Demo35\",\"bookAuthor\":\"Manan\"}}";

		kafkaTemplate.sendDefault(json).get();
		
		CountDownLatch latch = new CountDownLatch(1);
		latch.await(3, TimeUnit.SECONDS);
		
		verify(consumer, times(1)).message(isA(ConsumerRecord.class));
		verify(eventService, times(1)).processLibraryEvent(isA(ConsumerRecord.class));
		
		
	}
	

}
