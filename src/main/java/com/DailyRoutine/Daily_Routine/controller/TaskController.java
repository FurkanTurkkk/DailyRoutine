package com.DailyRoutine.Daily_Routine.controller;

import com.DailyRoutine.Daily_Routine.dto.TaskDto;
import com.DailyRoutine.Daily_Routine.dto.request.RequestForCreateTask;
import com.DailyRoutine.Daily_Routine.model.User;
import com.DailyRoutine.Daily_Routine.service.TaskService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/task")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @PostMapping
    public ResponseEntity<String> createTaskByUser(@AuthenticationPrincipal User user,
                                                     @RequestBody RequestForCreateTask request){

        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User not authenticated");
        }

        taskService.createTaskToUser(user,request);
        return ResponseEntity.ok("Task created successfully... You can do this !!!");
    }

    @PutMapping("/{taskId}/cancel")
    public ResponseEntity<TaskDto> updateTask(@AuthenticationPrincipal User user,
                                              @PathVariable("taskId")Long taskId){
        return ResponseEntity.ok(taskService.updateTaskForCancel(user,taskId));
    }

    @PutMapping("/{taskId}/completed")
    public ResponseEntity<String> taskCompeted(@AuthenticationPrincipal User user,
                                               @PathVariable("taskId")Long taskId){
        taskService.updateTaskForCompleted(user,taskId);
        return ResponseEntity.ok("Congratulations !! You can finish one task ");
    }

    @GetMapping
    public ResponseEntity<List<TaskDto>> findTaskListByUser(@AuthenticationPrincipal User user){
        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        return ResponseEntity.ok(taskService.findTaskListByUser(user));
    }
}
