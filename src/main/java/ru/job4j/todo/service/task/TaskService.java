package ru.job4j.todo.service.task;

import ru.job4j.todo.model.Task;

import java.util.Collection;
import java.util.Optional;

public interface TaskService {

    Task save(Task task);

    boolean deleteById(Integer id);

    boolean update(Task task);

    Optional<Task> findById(Integer id);

    Collection<Task> findAll();

    Collection<Task> findCompleted();

    Collection<Task> findNew();

    boolean updateDone(Integer id, boolean isDone);
}
