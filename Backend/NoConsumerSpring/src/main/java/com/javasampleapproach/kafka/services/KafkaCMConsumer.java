package com.javasampleapproach.kafka.services;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Session;
import com.javasampleapproach.kafka.model.ChannelMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class KafkaCMConsumer
{
/*    Cluster cluster = Cluster.builder().addContactPoints("cassandra-321448472.us-west-1.elb.amazonaws.com").build();
    Session session = cluster.connect("pingme");
    private static final Logger LOG = LoggerFactory.getLogger(ChannelMessage.class);
    @KafkaListener(topics="${jsa.kafka.topicCM}", group = "foo", containerFactory = "kafkaListenerContainerFactoryCM")
    //@Payload(required = false)
    public String processMessage(ChannelMessage channelPing) {
        System.out.println("Received Channel Message = " + channelPing);
        UUID uuidCh = UUID.randomUUID();   //ChannelId
        String randomUUIDStringCh = uuidCh.toString();
        UUID uuidM = UUID.randomUUID();   //MessageId
        String randomUUIDStringM = uuidM.toString();
        String Str=   "INSERT INTO pingme.channelmessages (messageid, channelname, messagecontent, sender, timestamp, avatar) values("+ randomUUIDStringM + ",'" + channelPing.getChannelName().toString() + "','" + channelPing.getMessageContent().toString() + "','" + channelPing.getFrom().toString() + "','" + channelPing.getTimestamp().toString() +   "' , '" + "https://www.gravatar.com/avatar/94d093eda664addd6e450d7e98" +  "');";
        System.out.println("Query = " + Str);
        session.execute(Str);
        return "Channel Message send Successfully";
    }*/
}
