package com.DailyRoutine.Daily_Routine.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstname;
    private String lastname;
    private String gmail;
    private String username;
    private String password;

    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL)
    private List<Task> taskList;

    public User(){
    }

    public User(String firstname,String lastname,String gmail,String username,String password){
        this.firstname=firstname;
        this.lastname=lastname;
        this.gmail=gmail;
        this.username=username;
        this.password=password;
        this.taskList=new ArrayList<>();
    }

    public Long getId(){
        return id;
    }

    public String getFirstname(){
        return firstname;
    }

    public String getLastname(){
        return lastname;
    }

    public String getGmail(){
        return gmail;
    }

    public String getUsername(){
        return username;
    }

    public String getPassword(){
        return password;
    }

    public List<Task> getTaskList(){
        return taskList;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(gmail, user.gmail);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(gmail);
    }

    public void changeGmail(String gmail){
        this.gmail=gmail;
    }
}
