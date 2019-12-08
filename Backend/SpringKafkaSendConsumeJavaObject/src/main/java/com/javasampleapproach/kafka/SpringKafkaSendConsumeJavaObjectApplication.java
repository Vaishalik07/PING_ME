package com.javasampleapproach.kafka;

import com.javasampleapproach.kafka.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.javasampleapproach.kafka.services.KafkaProducer;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@SpringBootApplication
public class SpringKafkaSendConsumeJavaObjectApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringKafkaSendConsumeJavaObjectApplication.class, args);
	}
}

	/*@Autowired
	KafkaProducer producer1;
	*/

	//@Override
	//public void run(String... arg0) throws Exception {


		/*
		User user01 = new User("user01@gmail.com", "user101","first101", "last101","pass101" );
		producer.send(user01); */
		
		// Send Peter customer
		/*
		User user02 = new User("user02@gmail.com", "user102","first102", "last102", "pass102");
		producer.send(user02);
		*/

		/*
		//Send Message
		Message message01 = new Message("heyhello01","ISender01","YouReceiver01", "TalkAnytime01");
		//Message message01 = new Message("user02@gmail.com", "user102","first102", "last102", "pass102");
		producer.sendMessage(message01);
		*/

		/*
		//Send Channel Message
		ChannelMessage channelText = new ChannelMessage("ch101", "talker101", "ch101name", "Hey allllll !!!!!", "todayyouall");
		producer.sendChannelMessage(channelText);

		 */
		/*
		Channel channel = new Channel("ch101", "ch101Name", "user101");
		producer.sendChannelRequest(channel);

		 */
/*
		GitStuff gitGet = new GitStuff("randomId", "buyMeCoffee", "spellBee", "Action404", "nocommit");
		producer.sendGitRelated(gitGet);
	}*/

