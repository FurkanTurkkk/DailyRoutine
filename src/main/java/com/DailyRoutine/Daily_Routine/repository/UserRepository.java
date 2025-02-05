package com.DailyRoutine.Daily_Routine.repository;

import com.DailyRoutine.Daily_Routine.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    Optional<User> findByGmail(String gmail);
    Optional<User> findByUsername(String username);
}
