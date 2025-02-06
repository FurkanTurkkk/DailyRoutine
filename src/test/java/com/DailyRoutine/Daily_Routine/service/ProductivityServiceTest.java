package com.DailyRoutine.Daily_Routine.service;

import com.DailyRoutine.Daily_Routine.model.TaskType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class ProductivityServiceTest {

    private ProductivityService productivityService;
    private TaskService taskService;

    @BeforeEach
    public void setUp(){

        taskService = Mockito.mock(TaskService.class);
        productivityService = new ProductivityService(taskService);
    }

    @Test
    void shouldReturnDailyProductivity_whenTodayIsFinishDay(){
        Long userId = 1L;
        int totalTask = 2;
        int completedTask = 1;
        double productivity = (completedTask/(double)totalTask)*100;

        Mockito.when(taskService.getAllTaskCountByUserId(userId,LocalDate.now(),LocalDate.now())).thenReturn(totalTask);
        Mockito.when(taskService.getCompletedTaskCountByUserId(userId, TaskType.COMPLETED,LocalDate.now(),LocalDate.now())).thenReturn((int)completedTask);


        double result = productivityService.calculateDailyProductivity(userId);

        assertEquals(productivity,result);

        Mockito.verify(taskService).getAllTaskCountByUserId(userId,LocalDate.now(),LocalDate.now());
        Mockito.verify(taskService).getCompletedTaskCountByUserId(userId, TaskType.COMPLETED,LocalDate.now(), LocalDate.now());
    }

    @Test
    void shouldReturnWeeklyProductivity_whenFromCreationDayToFinishedDay(){
        Long userId = 1L;
        LocalDate today = LocalDate.now();
        LocalDate weekStart = today.minusDays(6);

        int totalTask = 2;
        int completedTask = 1;
        double productivity = (completedTask/(double)totalTask)*100;

        Mockito.when(taskService.getAllTaskCountByUserId(userId,weekStart,today)).thenReturn(totalTask);
        Mockito.when(taskService.getCompletedTaskCountByUserId(userId, TaskType.COMPLETED,weekStart,today)).thenReturn(completedTask);

        double result = productivityService.calculateWeeklyProductivity(userId);

        assertEquals(productivity,result);

        Mockito.verify(taskService).getAllTaskCountByUserId(userId,weekStart,today);
        Mockito.verify(taskService).getCompletedTaskCountByUserId(userId, TaskType.COMPLETED,weekStart,today);

    }

    @Test
    void shouldReturnMonthlyProductivity_whenFromCreationDayToFinishedDay(){
        Long userId = 1L;
        LocalDate today = LocalDate.now();
        LocalDate monthDay = today.withDayOfMonth(1);

        int totalTask = 2;
        int completedTask = 1;
        double productivity = (completedTask/(double)totalTask)*100;

        Mockito.when(taskService.getAllTaskCountByUserId(userId,monthDay,today)).thenReturn(totalTask);
        Mockito.when(taskService.getCompletedTaskCountByUserId(userId, TaskType.COMPLETED,monthDay,today)).thenReturn(completedTask);

        double result = productivityService.calculateMonthlyProductivity(userId);

        assertEquals(productivity,result);

        Mockito.verify(taskService).getAllTaskCountByUserId(userId,monthDay,today);
        Mockito.verify(taskService).getCompletedTaskCountByUserId(userId, TaskType.COMPLETED,monthDay,today);

    }
}
