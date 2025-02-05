package com.DailyRoutine.Daily_Routine.dto.converter;

import com.DailyRoutine.Daily_Routine.dto.TaskDto;
import com.DailyRoutine.Daily_Routine.dto.UserDto;
import com.DailyRoutine.Daily_Routine.model.User;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserDtoConverter {

    private final TaskDtoConverter converter;

    public UserDtoConverter(TaskDtoConverter converter) {
        this.converter = converter;
    }

    public UserDto convert(User user){
        List<TaskDto> taskDtoList = user.getTaskList().stream()
                .map(converter::convert)
                .toList();

        return new UserDto(
                user.getFirstname(),
                user.getLastname(),
                user.getGmail(),
                taskDtoList
        );
    }
}
