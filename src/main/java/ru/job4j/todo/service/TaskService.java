package ru.job4j.todo.service;

import ru.job4j.todo.model.Task;

import java.util.Collection;
import java.util.Optional;

public interface TaskService {

    Task save(Task task);

    void deleteById(Integer id);

    boolean update(Task task);

    Optional<Task> findById(Integer id);

    Collection<Task> findAll(String filter);

    boolean markAsDone(Integer id);
}
