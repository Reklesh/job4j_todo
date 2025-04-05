package ru.job4j.todo.repository.task;

import ru.job4j.todo.model.Task;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Optional;

public interface TaskRepository {

    Task save(Task task);

    boolean deleteById(Integer id);

    boolean update(Task task);

    boolean updateDone(Integer id, boolean isDone);

    Optional<Task> findById(Integer id);

    Collection<Task> findAll();

    Collection<Task> findByCreatedAfter(LocalDateTime localDateTime);

    Collection<Task> findByDone(Boolean b);
}

