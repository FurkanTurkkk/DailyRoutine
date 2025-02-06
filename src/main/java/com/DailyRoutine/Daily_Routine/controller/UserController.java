package com.DailyRoutine.Daily_Routine.controller;

import com.DailyRoutine.Daily_Routine.dto.UserDto;
import com.DailyRoutine.Daily_Routine.dto.request.RequestForCreateUser;
import com.DailyRoutine.Daily_Routine.dto.request.RequestForLogin;
import com.DailyRoutine.Daily_Routine.model.User;
import com.DailyRoutine.Daily_Routine.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<String> createUser(@RequestBody RequestForCreateUser request){
        userService.createUser(request);
        return ResponseEntity.ok("Registration has been completed successfully.");
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody RequestForLogin request){
        return ResponseEntity.ok(userService.login(request));
    }

    @PutMapping("/update")
    public ResponseEntity<UserDto> updateUserForGmail(@AuthenticationPrincipal User user,
                                                      @RequestBody String gmail){
        return ResponseEntity.ok(userService.updateUserForGmail(user,gmail));
    }

    @GetMapping("/username/{username}")
    public ResponseEntity<UserDto> findUserByUsername(@AuthenticationPrincipal User user,
                                                      @PathVariable("username")String username){
        return ResponseEntity.ok(userService.findUserByUsername(user,username));
    }

    @DeleteMapping("/userId/{userId}")
    public ResponseEntity<String> deleteUserByUserId(@PathVariable("userId")Long userId){
        userService.deleteUserByUserId(userId);
        return ResponseEntity.ok("User deleted successfully");
    }


}
