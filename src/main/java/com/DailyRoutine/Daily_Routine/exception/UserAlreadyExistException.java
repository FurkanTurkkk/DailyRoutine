package com.DailyRoutine.Daily_Routine.exception;

import com.DailyRoutine.Daily_Routine.dto.UserDto;

public class UserAlreadyExistException extends RuntimeException {
    public UserAlreadyExistException(String s) {
        super(s);
    }
}
