package com.DailyRoutine.Daily_Routine.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;




@SpringBootTest
public class EmailServiceTest {

    private JavaMailSender javaMailSender;

    @BeforeEach
    public void setUp(){
        javaMailSender = Mockito.mock(JavaMailSender.class);
    }

    @Test
    void shouldSendMailFromGmailToAnotherGmail(){
        String mail1 = "test1@gmail.com";
        String mail2 = "test2@gmail.com";
        String subject = "Test";
        String text = "Test";

        SimpleMailMessage mail = new SimpleMailMessage();
        mail.setFrom(mail1);
        mail.setTo(mail2);
        mail.setSubject(subject);
        mail.setText(text);

        javaMailSender.send(mail);

        Mockito.verify(javaMailSender).send(mail);

    }

}
