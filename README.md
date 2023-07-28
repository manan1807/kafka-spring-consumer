# kafka-spring-consumer
Applications that need to read data from kafka use a KafkaConsumer to subscribe to a Kafka topics and recieve messages from these topics.
Reading data from Kafka is a bit different than reading data from other messaging systems, and there are few unique concepts and ideas involved.

## Kafka Consumer API
KafkaConsumer API is used to make a subscription to a specific topic on the Kafka Broker.

We can also add Consumer Rebalance Listener to it so that every time a new partition or a new consumer is added or leave, we can log them for tracking purpose.


### Consumer poll
Consumer poll is the method that actually poll the records from the Kafka Producer,
These records are stored inside the offsets of the partitions of the topic.

### RecordManager Table: 
Here we are storing the offsets number and partitions numbers of the topics and the consumer-id's that are associated with the partitions and topics so that it can be easily evaluated that which consumer-instance is associated with which topic and related meta-data information about the topic.

Snapshot of Data stored in H2 in memory database for all the tables that has been created

![Capture2](https://github.com/manan1807/kafka-spring-consumer/assets/72210797/09781e53-60b8-422c-b060-648ba8a2878e)
