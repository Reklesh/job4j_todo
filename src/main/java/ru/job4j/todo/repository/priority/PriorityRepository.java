package ru.job4j.todo.repository.priority;

import ru.job4j.todo.model.Priority;

import java.util.Collection;
import java.util.Optional;

public interface PriorityRepository {

    Collection<Priority> findAll();
}