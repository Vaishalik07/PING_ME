package com.javasampleapproach.kafka.services;

import com.javasampleapproach.kafka.model.GitStuff;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaGitConsumer {
    private static final Logger LOG = LoggerFactory.getLogger(GitStuff.class);
    @KafkaListener(topics="${jsa.kafka.topicGit}", group = "foo", containerFactory = "kafkaListenerContainerFactoryGit")
    // @Payload(required = false)
    public void processMessage(GitStuff gitGate) {
        System.out.println("User made git changes from consumer= " + gitGate);
    }
}
