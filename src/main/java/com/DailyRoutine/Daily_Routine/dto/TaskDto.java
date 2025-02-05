package com.DailyRoutine.Daily_Routine.dto;

import com.DailyRoutine.Daily_Routine.model.TaskType;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

public class TaskDto {
    private String headline;
    private String explanation;
    private Long remainingTime;

    @Enumerated(EnumType.STRING)
    private TaskType state;

    public TaskDto(){}

    public TaskDto(String headline,String explanation,Long remainingTime,TaskType state){
        this.headline=headline;
        this.explanation=explanation;
        this.remainingTime=remainingTime;
        this.state=state;
    }

    public String getHeadline(){
        return headline;
    }

    public String getExplanation(){
        return explanation;
    }

    public Long getRemainingTime(){
        return remainingTime;
    }

    public TaskType getState(){
        return state;
    }

}
