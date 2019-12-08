package com.javasampleapproach.kafka.model;

public class User {
    String emailId;
    String firstName;
    String lastName;
    String password;
    String userId;
    String imageUrl;

    public User(String emailId, String userId, String firstName, String lastName, String password, String imageUrl) {
        this.emailId = emailId;
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
        this.imageUrl = imageUrl;
    }

    public User() {
    }

//    @Override

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
//    public String toString() {
//        return emailId + " " + userId + " " + firstName + " " +  lastName + " " + password;
//    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String toString(){
            String info = String.format("{ 'emailId': %s, 'userId': %s, 'firstName':%s, 'lastName':%s, 'password':%s, 'imageUrl'}", emailId, userId, firstName, lastName, password, imageUrl);
        return info;
    }
}
