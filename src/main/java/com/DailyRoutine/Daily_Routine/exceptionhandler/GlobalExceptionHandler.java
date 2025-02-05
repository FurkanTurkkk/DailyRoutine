package com.DailyRoutine.Daily_Routine.exceptionhandler;

import com.DailyRoutine.Daily_Routine.exception.UnauthorizedException;
import com.DailyRoutine.Daily_Routine.exception.UserAlreadyExistException;
import com.DailyRoutine.Daily_Routine.exception.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UserAlreadyExistException.class)
    public ResponseEntity<String> handleUserAlreadyExistException(UserAlreadyExistException e){
        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<String> handleUsernameNotFoundException(UsernameNotFoundException e){
        return new ResponseEntity<>(e.getMessage(),HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<String> handleRuntimeException(RuntimeException e){
        if (e.getMessage().contains("invalid")){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.UNAUTHORIZED);
        }
        return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<String> handleUserNotFoundException(UserNotFoundException e){
        return new ResponseEntity<>(e.getMessage(),HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<String> handleUnauthorizedException(UnauthorizedException e){
        return new ResponseEntity<>(e.getMessage(),HttpStatus.UNAUTHORIZED);
    }
}
