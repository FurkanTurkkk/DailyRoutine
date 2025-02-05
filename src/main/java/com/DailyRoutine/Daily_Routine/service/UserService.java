package com.DailyRoutine.Daily_Routine.service;

import com.DailyRoutine.Daily_Routine.dto.UserDto;
import com.DailyRoutine.Daily_Routine.dto.converter.UserDtoConverter;
import com.DailyRoutine.Daily_Routine.dto.request.RequestForCreateUser;
import com.DailyRoutine.Daily_Routine.dto.request.RequestForLogin;
import com.DailyRoutine.Daily_Routine.exception.UnauthorizedException;
import com.DailyRoutine.Daily_Routine.exception.UserAlreadyExistException;
import com.DailyRoutine.Daily_Routine.exception.UserNotFoundException;
import com.DailyRoutine.Daily_Routine.model.User;
import com.DailyRoutine.Daily_Routine.repository.UserRepository;
import com.DailyRoutine.Daily_Routine.util.jwt.JwtUtil;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserDtoConverter converter;
    private final JwtUtil jwtUtil;

    public UserService(UserRepository userRepository,
                       UserDtoConverter converter,
                       JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.passwordEncoder = new BCryptPasswordEncoder();
        this.converter = converter;
        this.jwtUtil = jwtUtil;
    }

    public UserDto createUser(RequestForCreateUser request) {
        if (userRepository.findByGmail(request.getGmail()).isPresent()){
            throw new UserAlreadyExistException("User already exist by gmail : "+request.getGmail());
        }

        if(userRepository.findByUsername(request.getUsername()).isPresent()){
            throw new UserAlreadyExistException("User already exist by username : "+request.getUsername());
        }

        User user=new User(request.getFirstname(),request.getLastname(),request.getGmail(),
                request.getUsername(),passwordEncoder.encode(request.getPassword()));
        userRepository.save(user);

        return converter.convert(user);
    }

    public UserDto findUserByUsername(User user,String username) {
        if (!user.getUsername().equals(username)){
            throw new UnauthorizedException("You are not allowed to this action");
        }
        return converter.convert(getUserByUsername(username));
    }

    public Object login(RequestForLogin request) {
        User user=getUserByUsername(request.getUsername());
        if(!passwordEncoder.matches(request.getPassword(),user.getPassword())){
            throw new RuntimeException("Password invalid");
        }
        return jwtUtil.generateToken(request.getUsername());
    }

    protected User findUserByUserId(Long userId){
        return userRepository.findById(userId)
                .orElseThrow(()->new UserNotFoundException("User could not found by id : "+userId));
    }

    public User getUserByUsername(String username){
        Optional<User> user = userRepository.findByUsername(username);
        if(user.isEmpty()){
            throw new UsernameNotFoundException("User could not found by username : "+username);
        }
        return user.get();
    }


    public UserDto updateUserForGmail(User user, String gmail) {
        User registeredUser = getUserByUsername(user.getUsername());
        registeredUser.changeGmail(gmail);
        userRepository.save(registeredUser);
        return converter.convert(registeredUser);
    }

    public List<User> findUserList(){
        return userRepository.findAll();
    }
}
