package com.DailyRoutine.Daily_Routine.dto.request;

public class RequestForLogin {

    private String username;
    private String password;

    public RequestForLogin(){}

    public RequestForLogin(String username,String password){
        this.username=username;
        this.password=password;
    }

    public String getUsername(){
        return username;
    }

    public String getPassword(){
        return password;
    }
}
