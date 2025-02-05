package com.DailyRoutine.Daily_Routine.util.notification;

import com.DailyRoutine.Daily_Routine.model.Task;
import com.DailyRoutine.Daily_Routine.model.TaskType;
import com.DailyRoutine.Daily_Routine.model.User;
import com.DailyRoutine.Daily_Routine.service.EmailService;
import com.DailyRoutine.Daily_Routine.service.TaskService;
import com.DailyRoutine.Daily_Routine.service.UserService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ReminderScheduler {
    private final EmailService emailService;
    private final TaskService taskService;
    private final UserService userService;

    public ReminderScheduler(EmailService emailService, TaskService taskService, UserService userService) {
        this.emailService = emailService;
        this.taskService = taskService;
        this.userService = userService;
    }

    @Scheduled(cron = "0 10 21 * * ?") // 21.10 at pm send mail everyday.
    public void sendDailyTaskReminders(){
        List<User> userList = userService.findUserList();

        for (User user : userList){
            List<Task> pendingTasks = taskService.findTaskListByUserAndState(user, TaskType.PENDING);
            if (!pendingTasks.isEmpty()) {
                String message = generateTaskReminderMessage(pendingTasks);
                emailService.sendMail(user.getGmail(), "Reminder of Daily Tasks", message);
            }
        }
    }

    private String generateTaskReminderMessage(List<Task> tasks) {
        StringBuilder sb = new StringBuilder("You must complete task list :\n");
        for (Task task : tasks) {
            sb.append("- ").append(task.getExplanation()).
                    append(" last ").
                    append(task.getFinishDay().getDayOfYear() - task.getCreationDay().getDayOfYear()).
                    append(" day ").append("\n");
        }
        return sb.toString();
    }
}
