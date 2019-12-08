package com.javasampleapproach.kafka.model;

import java.util.UUID;

public class Message {
    String text;
    String from;
    String to;
    String timeStamp;
    public Message(String text, String from, String to, String timeStamp)
    {
        this.text=text;
        this.from=from;
        this.to=to;
        this.timeStamp=timeStamp;
    }

    public Message()
    {}

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String toString(){

        String info = String.format("{'text': %s, 'from': %s, 'to':%s, 'timestamp':%s}", text, from,to,timeStamp );
        return info;
    }
}




