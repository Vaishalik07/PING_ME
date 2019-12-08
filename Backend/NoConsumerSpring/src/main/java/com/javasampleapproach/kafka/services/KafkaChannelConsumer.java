//NOT REQUIRED
package com.javasampleapproach.kafka.services;

import com.javasampleapproach.kafka.model.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaChannelConsumer {
    private static final Logger LOG = LoggerFactory.getLogger(Channel.class);
    @KafkaListener(topics="${jsa.kafka.topicChannel}", group = "foo", containerFactory = "kafkaListenerContainerFactoryChannel")
    // @Payload(required = false)
    public void processMessage(Channel channel) {
        System.out.println("Consumer Posting channel  = " + channel);
    }

}
