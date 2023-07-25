package com.example.libraryeventlistener.config;

import java.util.Collection;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.common.TopicPartition;
import org.springframework.kafka.listener.ConsumerAwareRebalanceListener;
import org.springframework.stereotype.Component;


import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class CustomConsumerRebalanceListener implements ConsumerAwareRebalanceListener {
	private String consumerInstance;

	public void onPartitionsRevokedBeforeCommit(Consumer<?, ?> consumer, Collection<TopicPartition> partitions) {
		log.debug("****Rebalance - Partitions revoked before commit:\n");
		for (TopicPartition partition : partitions) {
			log.debug("*****Topic: " + partition.topic() + ", Partition: " + partition.partition()+", Consumer: "+consumer.toString());
		}
	}

	public void onPartitionsRevokedAfterCommit(Consumer<?, ?> consumer, Collection<TopicPartition> partitions) {
		log.debug("****Rebalance - Partitions revoked after commit:\n");
		for (TopicPartition partition : partitions) {
			log.debug("*****Topic: " + partition.topic() + ", Partition: " + partition.partition()+", Consumer: "+consumer.toString());
		}
	}

	public void onPartitionsAssigned(Consumer<?, ?> consumer, Collection<TopicPartition> partitions) {
		log.debug("****Rebalance - Partitions Assigned:\n");
		for (TopicPartition partition : partitions) {
			log.debug("*****Topic: " + partition.topic() + ", Partition: " + partition.partition()+", Consumer: "+consumer);
			this.consumerInstance = consumer.toString();
		}
	}
	@Override
	public void onPartitionsRevoked(Collection<TopicPartition> partitions) {
		log.debug("****Rebalance - Partitions revoked \n");
		for (TopicPartition partition : partitions) {
			log.debug("*****Topic: " + partition.topic() + ", Partition: " + partition.partition());
		}
	}

	public String getRecordAssignedConsumerInstance() {
		return consumerInstance;
	}
}
