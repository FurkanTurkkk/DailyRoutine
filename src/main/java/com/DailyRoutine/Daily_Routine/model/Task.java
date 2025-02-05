package com.DailyRoutine.Daily_Routine.model;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String headline;
    private String explanation;
    private LocalDate creationDay;
    private LocalDate finishDay;

    @Enumerated(EnumType.STRING)
    private TaskType state;

    @ManyToOne
    @JoinColumn(name = "userId",nullable = false)
    private User user;

    public Task(){}

    public Task(String headline, String explanation, LocalDate finishDay, User user) {
        this.headline = headline;
        this.explanation = explanation;
        this.creationDay=LocalDate.now();
        this.finishDay = finishDay;
        this.state = TaskType.PENDING;
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public String getHeadline() {
        return headline;
    }

    public String getExplanation() {
        return explanation;
    }

    public LocalDate getCreationDay() {
        return creationDay;
    }

    public LocalDate getFinishDay() {
        return finishDay;
    }

    public TaskType getState() {
        return state;
    }

    public User getUser() {
        return user;
    }

    public void updateState(TaskType state){
        this.state=state;
    }
}
