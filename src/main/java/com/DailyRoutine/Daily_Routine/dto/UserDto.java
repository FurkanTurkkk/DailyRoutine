package com.DailyRoutine.Daily_Routine.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class UserDto {

    private String firstname;
    private String lastname;
    private String gmail;
    private List<TaskDto> taskDtoList;

    public UserDto(){
    }

    public UserDto(String firstname,String lastname,String gmail,List<TaskDto> taskDtoList){
        this.firstname=firstname;
        this.lastname=lastname;
        this.gmail=gmail;
        this.taskDtoList=taskDtoList;
    }

    public String getFirstname(){
        return firstname;
    }

    public String getLastname(){
        return lastname;
    }

    public String getGmail(){
        return gmail;
    }

    public List<TaskDto> getTaskDtoList(){
        return taskDtoList;
    }
}
