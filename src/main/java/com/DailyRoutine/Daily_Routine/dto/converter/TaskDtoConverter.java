package com.DailyRoutine.Daily_Routine.dto.converter;

import com.DailyRoutine.Daily_Routine.dto.TaskDto;
import com.DailyRoutine.Daily_Routine.model.Task;
import org.springframework.stereotype.Component;

@Component
public class TaskDtoConverter {

    public TaskDto convert(Task task){
        Long remainingTime = (long) (task.getFinishDay().getDayOfYear() - task.getCreationDay().getDayOfYear());
        return new TaskDto(
                task.getHeadline(),
                task.getExplanation(),
                remainingTime,
                task.getState()
        );
    }
}
