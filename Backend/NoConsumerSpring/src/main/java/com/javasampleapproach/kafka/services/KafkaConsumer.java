//NOT REQUIRED
package com.javasampleapproach.kafka.services;

import com.javasampleapproach.kafka.model.Message;
import com.javasampleapproach.kafka.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumer {
    private static final Logger LOG = LoggerFactory.getLogger(User.class);
	@KafkaListener(topics="${jsa.kafka.topic}", group = "foo", containerFactory = "kafkaListenerContainerFactory")
   // @Payload(required = false)
    public void processMessage(User user) {
		System.out.println("Received User Info = " + user);
    }
}
