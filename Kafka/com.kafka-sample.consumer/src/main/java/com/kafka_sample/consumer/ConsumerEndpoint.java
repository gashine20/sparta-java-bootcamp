package com.kafka_sample.consumer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ConsumerEndpoint {

    @KafkaListener(groupId = "group_a", topics = "topic1")
    public void consumeFromGroupA(String message){
        log.info("Group A consumed message from topic1: " + message);
    }

    @KafkaListener(groupId = "group_b", topics = "topic1")
    public void consumeFromGroupB(String message){
        log.info("Group B consumed message from topic1: " + message);
    }

    @KafkaListener(groupId = "group_c", topics = "topic2")
    public void consumeFromGroupC(String message){
        log.info("Group C consumed message from topic2: " + message);
    }

    @KafkaListener(groupId = "group_c", topics = "topic3")
    public void consumeFromGroupD(String message){
        log.info("Group C consumed message from topic3: " + message);
    }

    @KafkaListener(groupId = "group_d", topics = "topic4")
    public void consumeFromGroup0(String message){
        log.info("Group D consumed message from topic4: " + message);
    }
}
