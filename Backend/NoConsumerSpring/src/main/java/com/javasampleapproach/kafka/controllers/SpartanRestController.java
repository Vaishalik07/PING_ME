package com.javasampleapproach.kafka.controllers;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.Session;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonParser;
import com.javasampleapproach.kafka.model.User;
//import com.sun.tools.classfile.Synthetic_attribute;
import org.json.JSONArray;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;

@RestController
@RequestMapping("/api")
public class SpartanRestController {
    Cluster cluster = Cluster.builder().addContactPoints("cassandra-321448472.us-west-1.elb.amazonaws.com").build();
    Session session = cluster.connect("pingme");
    // User Sign up
    @CrossOrigin(origins = "*",allowedHeaders="*")
    @PostMapping(path = "/users/signup")
    public ResponseEntity<?> userSignup(@RequestBody User newuser) {
        User newObj = new User(newuser.getEmailId(), newuser.getUserId(), newuser.getFirstName(), newuser.getLastName(), newuser.getPassword(), newuser.getImageUrl());
	System.out.println("Obj :  " +newuser);    
    try {
            String Str = "INSERT INTO pingme.user(emailid, userid, firstname, avatar, lastname, password) values('" + newuser.getEmailId().toString() + "' , '" + newuser.getUserId().toString() + "','" + newuser.getFirstName().toString() + "','" + newuser.getImageUrl().toString() + "','" + newuser.getLastName().toString() + "','" + newuser.getPassword().toString() + "');";
            System.out.println("Query = " + Str);
            session.execute(Str);
            Str = "Select * from pingme.user where emailId= '" + newuser.getEmailId() + "' and password= '" + newuser.getPassword() + "' ALLOW FILTERING;";
            ObjectMapper mapper = new ObjectMapper();
            SimpleModule module = new SimpleModule();
            module.addSerializer(ResultSet.class, new ResultSetSerializer());
            mapper.registerModule(module);

            ResultSet result = session.execute(Str);
            String res = result.toString();
            String json = mapper.writeValueAsString(result);
            System.out.println(json);

            if (res.contains("exhausted: false"))
                return new ResponseEntity<>(json, null, HttpStatus.OK);
            else
                return new ResponseEntity<>("not Success", null, HttpStatus.INTERNAL_SERVER_ERROR);

        } catch (Exception e) {
            return new ResponseEntity<>("not Success", null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // User Login
    @CrossOrigin(origins = "*", allowedHeaders="*")
    @PostMapping(path = "/users/login")
    public ResponseEntity<?> userLogin(@RequestBody User user) throws JsonProcessingException {
        //  User newObj = new User(user.getUserId(), user.getPassword());
        String Str = "Select * from pingme.user where emailId= '" + user.getEmailId() + "' and password= '" + user.getPassword() + "' ALLOW FILTERING;";
        // String Str=  "INSERT INTO pingme.user(emailid, userid, firstname, imageurl, lastname, password) values('"+ newuser.getEmailId().toString() + "' , '" + newuser.getUserId().toString() + "','" + newuser.getFirstName().toString() + "','" + newuser.getImageUrl().toString() + "','" + newuser.getLastName().toString() + "','" + newuser.getPassword().toString() + "');";
        System.out.println("Query = " + Str);


        ObjectMapper mapper = new ObjectMapper();
        SimpleModule module = new SimpleModule();
        module.addSerializer(ResultSet.class, new ResultSetSerializer());
        mapper.registerModule(module);

        ResultSet result = session.execute(Str);
        String res = result.toString();
        String json = mapper.writeValueAsString(result);
        System.out.println(json);

        if (res.contains("exhausted: false"))
            return new ResponseEntity<>(json, null, HttpStatus.OK);
        else
            return new ResponseEntity<>("not Success", null, HttpStatus.INTERNAL_SERVER_ERROR);

    }

    //Get all users
    @CrossOrigin(origins = "*", allowedHeaders="*")
    @GetMapping(path = "/users/getallusers")
    public ResponseEntity<?> getAllUsers() throws JsonProcessingException {
        //  User newObj = new User(user.getUserId(), user.getPassword());
        String q = "Select * from pingme.user;";
       /* ResultSet res = session.execute(q);
        System.out.println("Query = " + q);
        List<Row> li = res.all();
        List<String> x = new ArrayList<>();
        for(Row row: li)
        {
            String str = row.toString();
            str = str.substring(0, str.length()-1);
            x.add(str.substring(4));
            System.out.println(row.toString());
        }
        String jsonText =  new Gson().toJson(x);

        return new ResponseEntity<>(jsonText, null, HttpStatus.OK);*/
        ObjectMapper mapper = new ObjectMapper();
        SimpleModule module = new SimpleModule();
        module.addSerializer(ResultSet.class, new ResultSetSerializer());
        mapper.registerModule(module);

        ResultSet result = session.execute(q);
        String json = mapper.writeValueAsString(result);
        System.out.println(json);
        return new ResponseEntity<>(json, null, HttpStatus.OK);
    }

    //Get all messages
    @CrossOrigin(origins = "*", allowedHeaders="*")
    @RequestMapping(value = "/getmessages", method = RequestMethod.GET)
    public ResponseEntity<?> getMessages(@RequestParam("channel") String ChannelName) throws JsonProcessingException {
        //  User newObj = new User(user.getUserId(), user.getPassword());
        String q = "Select sender, messagecontent, timestamp, avatar  from pingme.channelmessages where channelname = '" + ChannelName + "' ALLOW FILTERING;";
        ObjectMapper mapper = new ObjectMapper();
        SimpleModule module = new SimpleModule();
        module.addSerializer(ResultSet.class, new ResultSetSerializer());
        mapper.registerModule(module);

        ResultSet result = session.execute(q);
        String json = mapper.writeValueAsString(result);
        System.out.println(json);
        return new ResponseEntity<>(json, null, HttpStatus.OK);
    }

    //Get all channels
    @CrossOrigin(origins = "*", allowedHeaders="*")
    @RequestMapping(value = "/getchannels", method = RequestMethod.GET)
    public ResponseEntity<?> getChannels() throws JsonProcessingException {

        String q = "Select * from pingme.channel;";
        ObjectMapper mapper = new ObjectMapper();
        SimpleModule module = new SimpleModule();
        module.addSerializer(ResultSet.class, new ResultSetSerializer());
        mapper.registerModule(module);

        ResultSet result = session.execute(q);
        String json = mapper.writeValueAsString(result);
        System.out.println(json);
        return new ResponseEntity<>(json, null, HttpStatus.OK);
    }

    //Get all direct messages
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @RequestMapping(value = "/getdm", method = RequestMethod.GET)
    public ResponseEntity<?> getDirectMessage(@RequestParam("sender") String Sender, @RequestParam("receiver") String Receiver) throws JsonProcessingException {
        //  User newObj = new User(user.getUserId(), user.getPassword());
        String q = "Select sender, receiver, messagecontent, avatar, timestamp from pingme.messages where sender = '" + Sender + "'  and receiver = '" + Receiver + "' ALLOW FILTERING;";
        String x = "Select  sender, receiver,messagecontent, avatar, timestamp from pingme.messages where sender = '" + Receiver + "' and receiver = '" + Sender + "' ALLOW FILTERING;";

        System.out.println(q);
        System.out.println(x);
        ObjectMapper mapper = new ObjectMapper();
        SimpleModule module = new SimpleModule();
        module.addSerializer(ResultSet.class, new ResultSetSerializer());
        mapper.registerModule(module);
        ResultSet result = session.execute(q);
        String json = mapper.writeValueAsString(result);
        ResultSet result2 = session.execute(x);
        String json2 = mapper.writeValueAsString(result2);

        String res = "";

        if (json2.length() <= 2 && json.length() > 2 )
            return new ResponseEntity<>(json, null, HttpStatus.OK);
        if (json.length() <= 2 && json2.length() > 2)
            return new ResponseEntity<>(json2, null, HttpStatus.OK);
        if (json.length() > 2 && json2.length() > 2) {
            json = json.substring(0, json.length() - 1);
            json2 = json2.substring(1);
            System.out.println(json);
            System.out.println(json2);
            return new ResponseEntity<>(json + ',' + json2, null, HttpStatus.OK);
        }


            return new ResponseEntity<>("[]", null, HttpStatus.OK);

    }


    //Get all channels
    @CrossOrigin(origins = "*", allowedHeaders="*")
    @PostMapping(path = "/addchannel")
    public ResponseEntity<?> addChannel(@RequestParam("channelname") String Channel) throws JsonProcessingException {

        UUID uuid = UUID.randomUUID();
        String randomUUIDString = uuid.toString();
        String Str = "INSERT INTO pingme.channel(channelid, channelname) values(" + randomUUIDString + " , '" + Channel +  "');";
        ObjectMapper mapper = new ObjectMapper();
        SimpleModule module = new SimpleModule();
        module.addSerializer(ResultSet.class, new ResultSetSerializer());
        mapper.registerModule(module);
        ResultSet result = session.execute(Str);
        String json = mapper.writeValueAsString(result);
        System.out.println(json);
        return new ResponseEntity<>(json, null, HttpStatus.OK);
    }

}
