package com.example.libraryeventlistener.config;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.boot.autoconfigure.kafka.ConcurrentKafkaListenerContainerFactoryConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.ContainerProperties.AckMode;
import org.springframework.kafka.support.serializer.JsonDeserializer;

@Configuration
@EnableKafka
public class LibraryEventConsumerConfig {

//	@Bean
//	ConcurrentKafkaListenerContainerFactory<?, ?> kafkaListenerContainerFactory(
//			ConcurrentKafkaListenerContainerFactoryConfigurer configurer,
//			ConsumerFactory<Object, Object> kafkaConsumerFactory) {
//		ConcurrentKafkaListenerContainerFactory<Object, Object> factory = new ConcurrentKafkaListenerContainerFactory<>();
//		configurer.configure(factory, kafkaConsumerFactory);
//		factory.getContainerProperties().setAckMode(AckMode.MANUAL);
////		factory.setConcurrency(3);
//		return factory;
//	}

	public static KafkaConsumer<String, String> getConsumerProps() {
		Properties properties = new Properties();
		properties.setProperty(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092,localhost:9093,localhost:9094");
		properties.setProperty(ConsumerConfig.GROUP_ID_CONFIG, "library-events-listener-group");
		properties.setProperty(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "false");
		properties.setProperty(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
		properties.setProperty(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer");
		properties.setProperty(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer");
		return new KafkaConsumer<>(properties);
	}
	
	
//	@Bean
//	public ConsumerFactory<String, String> consumerFactory(){
//		Map<String, Object> configProps = new HashMap<>();
//		configProps.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092,localhost:9093,localhost:9094");
//		configProps.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
//		configProps.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
//		configProps.put(ConsumerConfig.GROUP_ID_CONFIG, "library-events-listener-group");
//		return new DefaultKafkaConsumerFactory<>(configProps);
//	}
//	
//	@Bean
//	public ConcurrentKafkaListenerContainerFactory<String, String> kafkaListenerContainerFactory(){
//		ConcurrentKafkaListenerContainerFactory<String, String> factory = new ConcurrentKafkaListenerContainerFactory<>();
//		 factory.setConsumerFactory(consumerFactory());
//		 factory.getContainerProperties().setConsumerRebalanceListener(new CustomConsumerRebalanceListener());
//		 return factory;
//	}
}
