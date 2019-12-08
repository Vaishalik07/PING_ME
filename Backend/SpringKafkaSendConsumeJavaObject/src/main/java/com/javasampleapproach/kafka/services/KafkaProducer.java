package com.javasampleapproach.kafka.services;

import com.javasampleapproach.kafka.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Service
public class KafkaProducer {
	@Autowired
	private KafkaTemplate<String, User> kafkaTemplate;

	@Autowired
	private KafkaTemplate<String, Message> kafkaTemplateDM;

	@Autowired
	private KafkaTemplate<String, ChannelMessage> kafkaTemplateCM;

	@Autowired
	private KafkaTemplate<String, Channel> kafkaTemplateChannel;

	@Autowired
	private KafkaTemplate<String, GitStuff> kafkaTemplateGit;

	@Value("${jsa.kafka.topic}")
	String kafkaTopic = "jsa-test";
	public void send(@Payload User user) {
	    System.out.println("Sending data=" + user);
	    kafkaTemplate.send(kafkaTopic, user);
	}

	@Value("${jsa.kafka.topicDM}")
	String kafkaTopicMessage ="jsa-test";
	public void sendMessage(@Payload Message message) {
		System.out.println("sending Message=" + message);
		kafkaTemplateDM.send(kafkaTopicMessage, message);
	}

	@Value("${jsa.kafka.topicCM}")
	String kafkaTopicCM ="jsa-test";
	public void sendChannelMessage(@Payload ChannelMessage channelText) {
		System.out.println("sending Message=" + channelText);
		kafkaTemplateCM.send(kafkaTopicCM, channelText);
	}

	@Value("${jsa.kafka.topicChannel}")
	String kafkaTopicChannel ="jsa-test";
	public void sendChannelRequest(@Payload Channel channel) {
		System.out.println("Requesting to post channel=" + channel);
		kafkaTemplateChannel.send(kafkaTopicChannel, channel);
	}

	@Value("${jsa.kafka.topicGit}")
	String kafkaTopicGit ="jsa-test";
	public void sendGitRelated(@Payload GitStuff gitGet) {
		System.out.println("Producing Git changes =" + gitGet);
		kafkaTemplateGit.send(kafkaTopicGit, gitGet);
	}

}
