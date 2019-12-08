package com.javasampleapproach.kafka.controllers;


import com.javasampleapproach.kafka.model.ChannelMessage;
import com.javasampleapproach.kafka.model.Message;
import com.javasampleapproach.kafka.services.KafkaProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/kafka")
public class KafkaController {

    private final KafkaProducer producer;

    @Autowired
    public KafkaController(KafkaProducer producer) {
        this.producer = producer;
    }

    /*//Publish User {NO USER TOPIC REQUIRED}
    @PostMapping(value = "/user")
    public void publishUserToKafkaTopic(@RequestBody User use){
        this.producer.send(use);
    }*/


    //Publish Direct Message
    @CrossOrigin(origins = "*", allowedHeaders="*")
    @PostMapping(value = "/directMessage")
    public String sendMessageToKafkaTopic(@RequestBody Message message){
        try{
            System.out.println("MESSAGE SENT");
            this.producer.sendMessage(message);
            return "Message sent Successfully";
        }catch (Exception e)
        {
            return "Message unsuccessful";
        }

    }

    //Publish Channel Message
    @CrossOrigin(origins = "*")
    @PostMapping(value = "/channelMessage")
    public String channelMessageToKafkaTopic(@RequestBody ChannelMessage mess){
        try{
            this.producer.sendChannelMessage(mess);
            return "Message sent Successfully";
        }catch (Exception e)
        {
            return "Message unsuccessful";
        }

    }

    /*//Publish channel  {NO CHANNEL TOPIC REQUIRED}
    @PostMapping(value = "/channel")
    public void ChannelMessageToKafkaTopic(@RequestBody Channel cha){
        this.producer.sendChannelMessage(mess);
    }*/

    /*
    //Git channel
    @PostMapping(value = "/git")
    public void channelMessageToKafkaTopic(@RequestBody GitStuff gitGet){
        this.producer.sendGitRelated(gitGet);
    }*/

}
