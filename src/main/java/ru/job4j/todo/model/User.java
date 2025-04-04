package ru.job4j.todo.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "todo_user")
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String login;
    private String name;
    private String password;
}
