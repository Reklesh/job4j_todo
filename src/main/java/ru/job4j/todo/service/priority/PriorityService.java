package ru.job4j.todo.service.priority;

import ru.job4j.todo.model.Priority;

import java.util.Collection;
import java.util.Optional;

public interface PriorityService {

    Collection<Priority> findAll();
}