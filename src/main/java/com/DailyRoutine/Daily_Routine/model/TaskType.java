package com.DailyRoutine.Daily_Routine.model;

public enum TaskType {
    CANCELED("CANCELED"),
    COMPLETED("COMPLETED"),
    PENDING("PENDING");

    private String value;

    TaskType(String value){
        this.value=value;
    }

    public String getValue(){
        return value;
    }
}
