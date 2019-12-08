package com.javasampleapproach.kafka.model;

public class Channel {
    String channelId;
    String channelName;
    String participants;

    public Channel(String channelId, String channelName, String participants)
    {
        this.channelId = channelId;
        this.channelName = channelName;
        this.participants = participants;
    }
    public Channel()
    {

    }

    public String getChannelId() {
        return channelId;
    }

    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }

    public String getChannelName() {
        return channelName;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }

    public String getParticipants() {
        return participants;
    }

    public void setParticipants(String participants) {
        this.participants = participants;
    }

    public String toString(){
        String info = String.format("{ 'ChannelId': %s, 'ChannelName': %s, 'Participant':%s}", channelId, channelName,participants);
        return info;
    }
}
