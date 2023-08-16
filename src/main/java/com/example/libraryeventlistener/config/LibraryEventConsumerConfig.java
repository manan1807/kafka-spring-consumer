package com.example.libraryeventlistener.config;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.kafka.ConcurrentKafkaListenerContainerFactoryConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.dao.RecoverableDataAccessException;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.listener.DeadLetterPublishingRecoverer;
import org.springframework.kafka.listener.DefaultErrorHandler;
import org.springframework.util.backoff.FixedBackOff;


import lombok.extern.slf4j.Slf4j;

@Configuration
@EnableKafka
@Slf4j
public class LibraryEventConsumerConfig {

	@Value("${topics.retry}")
	private String retryTopic;

	@Value("${topics.dead}")
	private String deadLetterTopic;

	@Autowired
	private KafkaTemplate<String, String> kafkaTemplate;

	@Bean
	public DeadLetterPublishingRecoverer publishingRecoverer() {
		log.debug("*****Start publishing record into Retry or Dead topic");
		DeadLetterPublishingRecoverer recoverer = new DeadLetterPublishingRecoverer(kafkaTemplate, (r, e) -> {
			if (e instanceof RecoverableDataAccessException) {
				log.debug("*****Exception is intanceOf RecoverableDataAccessException");
				return new TopicPartition(retryTopic, r.partition());
			} else {
				return new TopicPartition(deadLetterTopic, r.partition());
			}
		});

		return recoverer;
	}

	@Bean
	public DefaultErrorHandler errorHandler() {

		var backOff = new FixedBackOff(10000L, 2);

		return new DefaultErrorHandler(publishingRecoverer(), backOff);

	}
	


	@Bean
	ConcurrentKafkaListenerContainerFactory<?, ?> kafkaListenerContainerFactory(
			ConcurrentKafkaListenerContainerFactoryConfigurer configurer,
			ConsumerFactory<Object, Object> kafkaConsumerFactory) {
		ConcurrentKafkaListenerContainerFactory<Object, Object> factory = new ConcurrentKafkaListenerContainerFactory<>();
//		configurer.configure(factory, kafkaConsumerFactory);
//		factory.getContainerProperties().setAckMode(AckMode.MANUAL);
		factory.setConsumerFactory(consumerFactory());
		factory.setCommonErrorHandler(errorHandler());
		return factory;
	}

//	public static KafkaConsumer<String, String> getConsumerProps() {
//		Properties properties = new Properties();
//		properties.setProperty(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092,localhost:9093,localhost:9094");
//		properties.setProperty(ConsumerConfig.GROUP_ID_CONFIG, "library-events-listener-group");
//		properties.setProperty(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "false");
//		properties.setProperty(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
//		properties.setProperty(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer");
//		properties.setProperty(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer");
//		return new KafkaConsumer<>(properties);
//	}

	@Bean
	public ConsumerFactory<Object, Object> consumerFactory() {
		Map<String, Object> configProps = new HashMap<>();
		configProps.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092,localhost:9093,localhost:9094");
		configProps.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
		configProps.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
		configProps.put(ConsumerConfig.GROUP_ID_CONFIG, "library-events-listener-group");
		return new DefaultKafkaConsumerFactory<>(configProps);
	}
//	
//	@Bean
//	public ConcurrentKafkaListenerContainerFactory<String, String> kafkaListenerContainerFactory(){
//		ConcurrentKafkaListenerContainerFactory<String, String> factory = new ConcurrentKafkaListenerContainerFactory<>();
//		 factory.setConsumerFactory(consumerFactory());
//		 factory.getContainerProperties().setConsumerRebalanceListener(new CustomConsumerRebalanceListener());
//		 return factory;
//	}
}
