package com.DailyRoutine.Daily_Routine.service;

import com.DailyRoutine.Daily_Routine.dto.TaskDto;
import com.DailyRoutine.Daily_Routine.dto.UserDto;
import com.DailyRoutine.Daily_Routine.dto.converter.UserDtoConverter;
import com.DailyRoutine.Daily_Routine.dto.request.RequestForCreateUser;
import com.DailyRoutine.Daily_Routine.dto.request.RequestForLogin;
import com.DailyRoutine.Daily_Routine.exception.UnauthorizedException;
import com.DailyRoutine.Daily_Routine.exception.UserAlreadyExistException;
import com.DailyRoutine.Daily_Routine.model.User;
import com.DailyRoutine.Daily_Routine.repository.UserRepository;
import com.DailyRoutine.Daily_Routine.util.jwt.JwtUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class UserServiceTest {

    private UserService userService;
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;
    private UserDtoConverter userDtoConverter;
    private JwtUtil jwtUtil;

    @BeforeEach
    public void setUp(){
        userRepository = Mockito.mock(UserRepository.class);
        passwordEncoder = Mockito.mock(BCryptPasswordEncoder.class);
        userDtoConverter = Mockito.mock(UserDtoConverter.class);
        jwtUtil = Mockito.mock(JwtUtil.class);

        userService = new UserService(userRepository, userDtoConverter,jwtUtil);
    }

    @Test
    void shouldReturnUserDto_whenNotExistUsernameOrNotExistGmail(){
        RequestForCreateUser request = new RequestForCreateUser(
                "Furkan",
                "Türk",
                "fuurkan.tuurk@gmail.com",
                "fitmuhendis",
                "123321"
        );

        User user = new User(
                request.getFirstname(),
                request.getLastname(),
                request.getGmail(),
                request.getUsername(),
                passwordEncoder.encode(request.getPassword())
        );

        List<TaskDto> taskDtoList = new ArrayList<>();

        UserDto userDto = new UserDto(
                request.getFirstname(),
                request.getLastname(),
                request.getGmail(),
                taskDtoList
        );

        Mockito.when(userRepository.findByGmail(request.getGmail())).thenReturn(Optional.empty());
        Mockito.when(userRepository.findByUsername(request.getUsername())).thenReturn(Optional.empty());
        Mockito.when(userRepository.save(user)).thenReturn(user);
        Mockito.when(userDtoConverter.convert(user)).thenReturn(userDto);

        UserDto result = userService.createUser(request);

        assertEquals(userDto,result);

        Mockito.verify(userRepository).findByGmail(request.getGmail());
        Mockito.verify(userRepository).findByUsername(request.getUsername());
        Mockito.verify(userRepository).save(user);
        Mockito.verify(userDtoConverter).convert(user);
    }

    @Test
    void shouldThrowUserAlreadyExistException_whenExistGmail(){
        RequestForCreateUser request = new RequestForCreateUser(
                "Furkan",
                "Türk",
                "fuurkan.tuurk@gmail.com",
                "fitmuhendis",
                "123321"
        );

        User user = new User(
                request.getFirstname(),
                request.getLastname(),
                request.getGmail(),
                request.getUsername(),
                passwordEncoder.encode(request.getPassword())
        );

        Mockito.when(userRepository.save(user)).thenReturn(user);
        Mockito.when(userRepository.findByGmail(request.getGmail())).thenReturn(Optional.of(user));

        assertThrows(UserAlreadyExistException.class,
                ()->userService.createUser(request));

        Mockito.verify(userRepository).findByGmail(request.getGmail());
        Mockito.verify(userRepository,Mockito.never()).save(Mockito.any(User.class));
    }

    @Test
    void shouldThrowUserAlreadyExistException_whenExistUsername(){
        RequestForCreateUser request = new RequestForCreateUser(
                "Furkan",
                "Türk",
                "fuurkan.tuurk@gmail.com",
                "fitmuhendis",
                "123321"
        );

        User user = new User(
                request.getFirstname(),
                request.getLastname(),
                request.getGmail(),
                request.getUsername(),
                passwordEncoder.encode(request.getPassword())
        );

        Mockito.when(userRepository.findByUsername(request.getUsername())).thenReturn(Optional.of(user));

        assertThrows(UserAlreadyExistException.class,
                ()->userService.createUser(request));

        Mockito.verify(userRepository).findByUsername(request.getUsername());
        Mockito.verify(userRepository,Mockito.never()).save(Mockito.any(User.class));
    }

    @Test
    void shouldReturnUserDto_whenUserOfRequestIsUserGetUserByUserNameReturnExistUserByUsername(){

        RequestForCreateUser request = new RequestForCreateUser(
                "Furkan",
                "Türk",
                "fuurkan.tuurk@gmail.com",
                "fitmuhendis",
                "123321"
        );

        User user = new User(
                request.getFirstname(),
                request.getLastname(),
                request.getGmail(),
                request.getUsername(),
                passwordEncoder.encode(request.getPassword())
        );

        List<TaskDto> taskDtoList = new ArrayList<>();

        UserDto userDto = new UserDto(
                request.getFirstname(),
                request.getLastname(),
                request.getGmail(),
                taskDtoList
        );

        Mockito.when(userRepository.findByUsername(request.getUsername())).thenReturn(Optional.of(user));
        Mockito.when(userDtoConverter.convert(user)).thenReturn(userDto);

        UserDto result = userService.findUserByUsername(user,request.getUsername());

        assertEquals(userDto,result);

        Mockito.verify(userRepository).findByUsername(request.getUsername());
        Mockito.verify(userDtoConverter).convert(user);

    }

    @Test
    void shouldThrowUnauthorizedException_whenUserOfRequestIsDifferentUser(){
        User user1 = new User(
                "Furkan",
                "Türk",
                "fuurkan.tuurk@gmail.com",
                "fitmuhendis",
                passwordEncoder.encode("123321")
        );

        User user2 = new User(
                "Ahmet",
                "Unal",
                "ahmet.unal@gmail.com",
                "ahmett",
                passwordEncoder.encode("2222")
        );

        assertThrows(UnauthorizedException.class,
                ()->userService.findUserByUsername(user1,user2.getUsername()));

    }

    @Test
    void shouldReturnUniqueToken_whenUserCouldFindInUserRepositoryByUsernameAndMatchesPasswords(){
        PasswordEncoder realPasswordEncoder = new BCryptPasswordEncoder();
        RequestForCreateUser request = new RequestForCreateUser(
                "Furkan",
                "Türk",
                "fuurkan.tuurk@gmail.com",
                "fitmuhendis",
                "123321"
        );

        RequestForLogin loginRequest = new RequestForLogin(
                request.getUsername(),
                request.getPassword()
        );

        User user = new User(
                request.getFirstname(),
                request.getLastname(),
                request.getGmail(),
                request.getUsername(),
                realPasswordEncoder.encode(request.getPassword())
        );

        String expectedToken = "test-token";

        Mockito.when(userRepository.findByUsername(request.getUsername())).thenReturn(Optional.of(user));
        Mockito.when(passwordEncoder.matches(loginRequest.getPassword(), user.getPassword()))
                .thenReturn(realPasswordEncoder.matches(loginRequest.getPassword(), user.getPassword()));
        Mockito.when(jwtUtil.generateToken(user.getUsername())).thenReturn(expectedToken);

        Object result = userService.login(loginRequest);

        assertEquals(expectedToken,result);

        Mockito.verify(userRepository).findByUsername(request.getUsername());
        Mockito.verify(jwtUtil).generateToken(user.getUsername());
    }

    @Test
    void shouldThrowRuntimeException_whenUserEnterInvalidPassword(){
        RequestForLogin request = new RequestForLogin(
                "test",
                "123321"
        );
        User user = new User(
                "Test",
                "User",
                "test.user@gmail.com",
                "test",
                passwordEncoder.encode("correctPassword") // Doğru şifre
        );

        Mockito.when(passwordEncoder.matches(request.getPassword(), user.getPassword()))
                .thenReturn(false);

        assertThrows(RuntimeException.class, () -> userService.login(request));
    }

    @Test
    void shouldThrowUsernameNotFoundException_whenGetUserByUsernameReturnEmpty(){

        RequestForLogin request = new RequestForLogin(
                "test",
                "123321"
        );

        Mockito.when(userRepository.findByUsername(request.getUsername())).thenReturn(Optional.empty());

        assertThrows(UsernameNotFoundException.class,
                ()->userService.login(request));

        Mockito.verify(userRepository).findByUsername(request.getUsername());

    }

    @Test
    void shouldReturnUser_whenFindUserByUserNameInUserRepository(){

        User user = new User(
                "test",
                "test",
                "test@gmail.com",
                "test",
                passwordEncoder.encode("test")
        );

        String username = "test";

        Mockito.when(userRepository.findByUsername(username)).thenReturn(Optional.of(user));

        User result = userService.getUserByUsername(username);

        assertEquals(user,result);

        Mockito.verify(userRepository).findByUsername(username);
    }

    @Test
    void shouldThrowUsernameNotFoundException_whenCouldNotFindUserByUsername(){

        String username = "test-username";

        Mockito.when(userRepository.findByUsername(username)).thenReturn(Optional.empty());

        assertThrows(UsernameNotFoundException.class,
                ()->userService.getUserByUsername(username));

        Mockito.verify(userRepository).findByUsername(username);
    }

    @Test
    void shouldReturnUpdatedUserDto_whenUserExistAndChangeGmail() {
        // Test için kullanıcı nesnesi oluşturuluyor
        User user = new User(
                "test", // Kullanıcı adı
                "test", // Soyad
                "test@gmail.com", // Eski Gmail
                "test", // Kullanıcı adı
                passwordEncoder.encode("test") // Şifre
        );

        // Güncellenmiş Gmail adresi
        String updatedGmail = "updatedTest@gmail.com";

        // Mocklanmış UserDto nesnesi
        UserDto updatedUserDto = new UserDto(
                user.getFirstname(),
                user.getLastname(),
                updatedGmail, // Yeni Gmail
                new ArrayList<>() // Burada task'lar boş, ihtiyaca göre değiştirebilirsiniz
        );

        // Mock'lama: UserRepository'nin findByUsername metodunun doğru kullanıcıyı döndürmesini sağlıyoruz
        Mockito.when(userRepository.findByUsername(user.getUsername())).thenReturn(Optional.of(user));

        // Mock'lama: Converter'ın doğru şekilde User'ı UserDto'ya dönüştürmesini sağlıyoruz
        Mockito.when(userDtoConverter.convert(user)).thenReturn(updatedUserDto);

        // Testi çalıştırıyoruz
        UserDto result = userService.updateUserForGmail(user, updatedGmail);

        // Asıl test: Sonuç olarak döndürülen UserDto'nun beklenen değeri içerdiğinden emin oluyoruz
        assertEquals(updatedUserDto, result);

        // Ayrıca, kullanıcı bilgilerini güncellemek için save() metodunun çağrıldığını doğruluyoruz
        Mockito.verify(userRepository).save(user);
    }

    @Test
    void shouldListOfUserDto_whenRequestForNotification(){
        User user1 = new User(
                "Furkan",
                "Türk",
                "fuurkan.tuurk@gmail.com",
                "fitmuhendis",
                passwordEncoder.encode("123321")
        );

        User user2 = new User(
                "Ahmet",
                "Unal",
                "ahmet.unal@gmail.com",
                "ahmett",
                passwordEncoder.encode("2222")
        );

        List<User> userList = List.of(user1,user2);

        Mockito.when(userRepository.findAll()).thenReturn(userList);

        List<User> result = userService.findUserList();

        assertEquals(userList,result);

        Mockito.verify(userRepository).findAll();

    }


}
