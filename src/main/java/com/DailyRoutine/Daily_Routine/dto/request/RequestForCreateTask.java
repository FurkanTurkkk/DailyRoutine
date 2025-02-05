package com.DailyRoutine.Daily_Routine.dto.request;

import com.DailyRoutine.Daily_Routine.model.TaskType;

import java.time.LocalDate;

public class RequestForCreateTask {

    private String headline;
    private String explanation;
    private LocalDate finishDay;
    private TaskType state;

    public RequestForCreateTask(){}

    public RequestForCreateTask(String headline,String explanation,LocalDate finishDay,TaskType state){
        this.headline=headline;
        this.explanation=explanation;
        this.finishDay=finishDay;
        this.state=state;
    }

    public String getHeadline(){
        return headline;
    }

    public String getExplanation(){
        return explanation;
    }

    public LocalDate getFinishDay(){
        return finishDay;
    }

    public TaskType getState(){
        return state;
    }
}
