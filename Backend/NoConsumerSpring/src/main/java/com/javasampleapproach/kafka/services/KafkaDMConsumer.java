package com.javasampleapproach.kafka.services;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Session;
import com.javasampleapproach.kafka.model.Message;
        import com.javasampleapproach.kafka.model.User;
        import org.springframework.kafka.annotation.KafkaListener;
        import org.springframework.messaging.MessageHeaders;
        import org.springframework.messaging.handler.annotation.Headers;
        import org.springframework.messaging.handler.annotation.Payload;
        import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

@Service
public class KafkaDMConsumer {
    /*Cluster cluster = Cluster.builder().addContactPoints("cassandra-321448472.us-west-1.elb.amazonaws.com").build();
    Session session = cluster.connect("pingme");
    //CassandraOperations template = new CassandraTemplate(session);
    @KafkaListener(topics="${jsa.kafka.topicDM}", group = "goo", containerFactory="kafkaListenerContainerFactoryDM")
   // @Payload(required = false)
    public String processMessage(@RequestBody Message message) {
        System.out.println("Received publish direct Message = " + message);
        UUID uuid = UUID.randomUUID();
        String randomUUIDString = uuid.toString();
        String Str=   "INSERT INTO pingme.messages (messageid, messagecontent, sender, receiver, timestamp, avatar) values("+ randomUUIDString + ",'" + message.getText().toString() + "', '" + message.getFrom().toString() + "','" + message.getTo().toString() +"','" + message.getTimeStamp().toString() +  "' , '" + "https://www.gravatar.com/avatar/94d093eda664addd6e450d7e98" +  "');";
        System.out.println("Query = " + Str);
        session.execute(Str);
        return "Message send Successfully";
    }

*/
}

