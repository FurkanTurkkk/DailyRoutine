package com.DailyRoutine.Daily_Routine.service;

import com.DailyRoutine.Daily_Routine.dto.TaskDto;
import com.DailyRoutine.Daily_Routine.dto.converter.TaskDtoConverter;
import com.DailyRoutine.Daily_Routine.model.Task;
import com.DailyRoutine.Daily_Routine.model.TaskType;
import com.DailyRoutine.Daily_Routine.model.User;
import com.DailyRoutine.Daily_Routine.repository.TaskRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class TaskServiceTest {

    private TaskService taskService;
    private TaskRepository taskRepository;
    private TaskDtoConverter converter;

    @BeforeEach
    public void setUp(){
        taskRepository = Mockito.mock(TaskRepository.class);
        converter = Mockito.mock(TaskDtoConverter.class);

        taskService = new TaskService(taskRepository,converter);
    }

    @Test
    void shouldSaveUser(){

        Task task = new Task(
                "software",
                "Work Git Version Control",
                LocalDate.of(2025,10,1),
                new User()
        );

        taskRepository.save(task);

        Mockito.verify(taskRepository).save(task);
    }

    @Test
    void shouldReturnTaskListOfUser_whenResourceForFindByUserInTaskRepository(){
        User user = new User();

        Task task1 = new Task(
                "software",
                "Work Git Version Control",
                LocalDate.of(2025,10,1),
                user
        );

        Task task2 = new Task(
                "software",
                "Work Docker ",
                LocalDate.of(2025,10,5),
                user
        );

        TaskDto taskDto1 = new TaskDto(
                task1.getHeadline(),
                task1.getExplanation(),
                (long)task1.getFinishDay().getDayOfYear()-task1.getCreationDay().getDayOfYear(),
                task1.getState()
        );

        TaskDto taskDto2 = new TaskDto(
                task2.getHeadline(),
                task2.getExplanation(),
                (long)task2.getFinishDay().getDayOfYear()-task2.getCreationDay().getDayOfYear(),
                task2.getState()
        );

        List<Task> taskList = List.of(task1,task2);
        List<TaskDto> taskDtoList = List.of(taskDto1,taskDto2);


        Mockito.when(taskRepository.findByUser(user)).thenReturn(taskList);
        Mockito.when(converter.convert(task1)).thenReturn(taskDto1);
        Mockito.when(converter.convert(task2)).thenReturn(taskDto2);

        List<TaskDto> result = taskService.findTaskListByUser(user);

        assertEquals(taskDtoList,result);

        Mockito.verify(taskRepository).findByUser(user);
        Mockito.verify(converter).convert(task1);
        Mockito.verify(converter).convert(task2);
    }

    @Test
    void shouldCalculateAllTaskForUserForProductivity(){
        LocalDate today = LocalDate.now();
        LocalDate finishDay = LocalDate.of(2026,10,10);
        Long userId = 1L;

        taskRepository.countByUserIdAndCreationDayBetween(userId,finishDay,today);

        Mockito.verify(taskRepository).countByUserIdAndCreationDayBetween(userId,finishDay,today);
    }

    @Test
    void shouldCalculateCompletedTaskForUserForProductivity(){
        LocalDate today = LocalDate.now();
        LocalDate finishDay = LocalDate.of(2026,10,10);
        Long userId = 1L;

        taskRepository.countByUserIdAndStateAndCreationDayBetween(userId, TaskType.COMPLETED,finishDay,today);

        Mockito.verify(taskRepository).countByUserIdAndStateAndCreationDayBetween(userId,TaskType.COMPLETED,finishDay,today);
    }

}
