package com.DailyRoutine.Daily_Routine.controller;

import com.DailyRoutine.Daily_Routine.model.User;
import com.DailyRoutine.Daily_Routine.service.ProductivityService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/productivity")
public class ProductivityController {

    private final ProductivityService productivityService;

    public ProductivityController(ProductivityService productivityService) {
        this.productivityService = productivityService;
    }

    @GetMapping("/daily")
    public ResponseEntity<String> getDailyProductivity(@AuthenticationPrincipal User user){
        double percent = productivityService.calculateDailyProductivity(user.getId());
        return ResponseEntity.ok("Your daily productivity is : "+percent);
    }

    @GetMapping("/weekly")
    public ResponseEntity<String> getWeeklyProductivity(@AuthenticationPrincipal User user){
        double percent = productivityService.calculateWeeklyProductivity(user.getId());
        return ResponseEntity.ok("Your weekly productivity is : "+percent);
    }

    @GetMapping("monthly")
    public ResponseEntity<String> getMonthlyProductivity(@AuthenticationPrincipal User user){
        double percent = productivityService.calculateMonthlyProductivity(user.getId());
        return ResponseEntity.ok("Your monthly productivity is : "+percent);
    }
}
