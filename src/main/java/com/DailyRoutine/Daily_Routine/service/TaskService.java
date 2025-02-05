package com.DailyRoutine.Daily_Routine.service;

import com.DailyRoutine.Daily_Routine.dto.TaskDto;
import com.DailyRoutine.Daily_Routine.dto.converter.TaskDtoConverter;
import com.DailyRoutine.Daily_Routine.dto.request.RequestForCreateTask;
import com.DailyRoutine.Daily_Routine.exception.UnauthorizedException;
import com.DailyRoutine.Daily_Routine.model.Task;
import com.DailyRoutine.Daily_Routine.model.TaskType;
import com.DailyRoutine.Daily_Routine.model.User;
import com.DailyRoutine.Daily_Routine.repository.TaskRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class TaskService {

    private final TaskRepository taskRepository;
    private final TaskDtoConverter converter;

    public TaskService(TaskRepository taskRepository, TaskDtoConverter converter) {
        this.taskRepository = taskRepository;
        this.converter = converter;
    }


    public void createTaskToUser(User user, RequestForCreateTask request) {
        Task task = new Task(
                request.getHeadline(),
                request.getExplanation(),
                request.getFinishDay(),
                user
        );
        taskRepository.save(task);
    }

    public List<TaskDto> findTaskListByUser(User user) {
        List<Task> taskList = taskRepository.findByUser(user);
        return taskList.stream()
                .map(converter::convert)
                .toList();
    }

    public void updateTaskForCancel(User user,Long taskId) {
        Task task = taskRepository.findById(taskId).get();
        if(!user.equals(task.getUser())){
            throw new UnauthorizedException("You are not allowed to update this task!");
        }
        task.updateState(TaskType.CANCELED);
        taskRepository.save(task);
    }

    public void updateTaskForCompleted(User user,Long taskId) {
        Task task = taskRepository.findById(taskId).get();
        if (!task.getUser().getId().equals(user.getId())) {
            throw new UnauthorizedException("You are not allowed to update this task!");
        }
        task.updateState(TaskType.COMPLETED);
        taskRepository.save(task);
    }

    public List<Task> findTaskListByUserAndState(User user,TaskType state){
        return taskRepository.findByUserAndState(user,state);
    }

    protected int getAllTaskCountByUserId(Long userId, LocalDate start, LocalDate end){
        return taskRepository.countByUserIdAndCreationDayBetween(userId,start,end);
    }

    protected int getCompletedTaskCountByUserId(Long userId, TaskType type, LocalDate start, LocalDate end){
        return taskRepository.countByUserIdAndStateAndCreationDayBetween(userId,type,start,end);
    }
}
