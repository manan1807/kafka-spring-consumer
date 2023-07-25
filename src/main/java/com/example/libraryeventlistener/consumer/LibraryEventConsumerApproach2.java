package com.example.libraryeventlistener.consumer;

import java.sql.Time;
import java.time.Duration;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import org.apache.kafka.clients.consumer.ConsumerRebalanceListener;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.TopicPartition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.libraryeventlistener.config.LibraryEventConsumerConfig;
import com.example.libraryeventlistener.service.LibraryEventService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class LibraryEventConsumerApproach2 {

	@Autowired
	private LibraryEventService eventService;
	private String topic = "library-events";


	public void runConsumer() throws JsonMappingException, JsonProcessingException, InterruptedException {

		ExecutorService executorService = Executors.newFixedThreadPool(4);
		for (int i = 0; i < 3; i++) {
			String consumerId = Integer.toString(i + 1);
			executorService.execute(() -> {
				try {
					startConsumer(consumerId);
				} catch (JsonProcessingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			});
		}
		executorService.shutdown();
		executorService.awaitTermination(10, TimeUnit.MINUTES);
	}

	private void startConsumer(String name) throws JsonMappingException, JsonProcessingException {

		KafkaConsumer<String, String> consumer = LibraryEventConsumerConfig.getConsumerProps();
		consumer.subscribe(Collections.singleton(topic));

		while (true) {
			log.debug("****starting consumerName: {}", name);
			ConsumerRecords<String, String> records = consumer.poll(Duration.ofSeconds(10000));
			if (records.count() != 0) {
				records.forEach(record -> log.debug("****Consumer Record:(key= {}, value= {}, partition= {}, offSet= {}, consumer-id= {})\n",
						record.key(), record.value(), record.partition(), record.offset(), name));
			eventService.processLibraryEvent(records, "consumerId-"+name);
			} else {
				log.debug("******There are no consumer records present///");
			}
			log.debug("****closing consumerName: {}", name);
			consumer.commitAsync();
//			consumer.close();
		}
	}
//
//	private static List<String> formatPartitions(Collection<TopicPartition> partitions) {
//		return partitions.stream().map(t -> String.format("topic: %s, partition: %s", t.topic(), t.partition()))
//				.collect(Collectors.toList());
//	}

}
