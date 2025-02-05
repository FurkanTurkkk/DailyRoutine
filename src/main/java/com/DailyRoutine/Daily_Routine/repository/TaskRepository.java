package com.DailyRoutine.Daily_Routine.repository;

import com.DailyRoutine.Daily_Routine.model.Task;
import com.DailyRoutine.Daily_Routine.model.TaskType;
import com.DailyRoutine.Daily_Routine.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;


@Repository
public interface TaskRepository extends JpaRepository<Task,Long> {
    List<Task> findByUser(User user);
    int countByUserIdAndCreationDayBetween(Long userId, LocalDate start,LocalDate end);
    int countByUserIdAndStateAndCreationDayBetween(Long userId, TaskType type, LocalDate start, LocalDate end);
    List<Task> findByUserAndState(User user,TaskType state);
}
