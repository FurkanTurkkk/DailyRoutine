package com.DailyRoutine.Daily_Routine.dto.request;

public class RequestForCreateUser {
    private String firstname;
    private String lastname;
    private String gmail;
    private String username;
    private String password;

    public RequestForCreateUser(){

    }

    public RequestForCreateUser(String firstname, String lastname, String gmail, String username, String password) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.gmail = gmail;
        this.username = username;
        this.password = password;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public String getGmail() {
        return gmail;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
