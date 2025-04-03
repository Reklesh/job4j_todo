package ru.job4j.todo.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.todo.model.Task;
import ru.job4j.todo.repository.TaskRepository;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Objects;
import java.util.Optional;

@Service
@AllArgsConstructor
public class SimpleTaskService implements TaskService {

    private final TaskRepository repository;

    @Override
    public Task save(Task task) {
        return repository.save(task);
    }

    @Override
    public void deleteById(Integer id) {
        repository.deleteById(id);
    }

    @Override
    public boolean update(Task task) {
        return repository.update(task);
    }

    @Override
    public Optional<Task> findById(Integer id) {
        return repository.findById(id);
    }

    @Override
    public Collection<Task> findAll(String filter) {
        if (Objects.equals(filter, "completed")) {
            return repository.findByDone(true);
        }
        if (Objects.equals(filter, "new")) {
            return repository.findByCreatedAfter(LocalDateTime.now().minusDays(1));
        }
        return repository.findAll();
    }

    public boolean markAsDone(Integer id) {
        return repository.findById(id)
                .map(task -> {
                    task.setDone(true);
                    repository.update(task);
                    return true;
                })
                .orElse(false);
    }
}
