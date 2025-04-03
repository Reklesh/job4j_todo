package ru.job4j.todo.repository;

import ru.job4j.todo.model.Task;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Optional;

public interface TaskRepository {

    Task save(Task task);

    void deleteById(Integer id);

    boolean update(Task task);

    Optional<Task> findById(Integer id);

    Collection<Task> findAll();

    Collection<Task> findByCreatedAfter(LocalDateTime localDateTime);

    Collection<Task> findByDone(Boolean b);
}

