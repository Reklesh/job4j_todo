package ru.job4j.todo.service.task;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.todo.model.Task;
import ru.job4j.todo.repository.task.TaskRepository;

import java.time.LocalDateTime;
import java.util.Collection;
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
    public boolean deleteById(Integer id) {
        return repository.deleteById(id);
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
    public Collection<Task> findAll() {
        return repository.findAll();
    }

    @Override
    public Collection<Task> findCompleted() {
        return repository.findByDone(true);
    }

    @Override
    public Collection<Task> findNew() {
        return repository.findByCreatedAfter(LocalDateTime.now().minusDays(1));
    }

    public boolean updateDone(Integer id, boolean isDone) {
        return repository.updateDone(id, isDone);
    }
}
