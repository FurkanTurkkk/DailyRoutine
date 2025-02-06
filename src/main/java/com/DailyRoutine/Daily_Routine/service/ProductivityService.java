package com.DailyRoutine.Daily_Routine.service;

import com.DailyRoutine.Daily_Routine.model.TaskType;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class ProductivityService {

    private final TaskService taskService;

    public ProductivityService(TaskService taskService) {
        this.taskService = taskService;
    }

    public double calculateDailyProductivity(Long userId) {
        LocalDate today = LocalDate.now();
        return calculateProductivity(userId,today,today);
    }

    public double calculateWeeklyProductivity(Long userId) {
        LocalDate today = LocalDate.now();
        LocalDate weekStart = today.minusDays(6);
        return calculateProductivity(userId,weekStart,today);
    }

    public double calculateMonthlyProductivity(Long userId) {
        LocalDate today = LocalDate.now();
        LocalDate monthStart = today.withDayOfMonth(1);
        return calculateProductivity(userId,monthStart,today);
    }

    private double calculateProductivity(Long userId, LocalDate start, LocalDate end){

        int totalTask = taskService.getAllTaskCountByUserId(userId,start,end);
        int completedTask = taskService.getCompletedTaskCountByUserId(userId, TaskType.COMPLETED,start,end);

        if(totalTask==0) return 0.0;
        if(completedTask==0) return 0.0;

        return (completedTask/(double)totalTask)*100;
    }
}
