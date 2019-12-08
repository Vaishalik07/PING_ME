package com.javasampleapproach.kafka.model;

public class ChannelMessage {
    //String channelId;
    String from;
    String channelName;
    String messageContent;
    String timestamp;

    public ChannelMessage(String from, String channelName, String messageContent, String timestamp)
    {
        //this.channelId = channelId;
        this.from = from;
        this.channelName = channelName;
        this.messageContent = messageContent;
        this.timestamp =  timestamp;
    }
    public ChannelMessage(){

    }


    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getChannelName() {
        return channelName;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }

    public String getMessageContent() {
        return messageContent;
    }

    public void setMessageContent(String messageContent) {
        this.messageContent = messageContent;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String toString(){
        String info = String.format("{ 'from':%s, 'ChannelName': %s, 'messageContent':%s, 'timestamp':%s}", from, channelName,messageContent,timestamp);
        return info;
    }
}
