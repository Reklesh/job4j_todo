package ru.job4j.todo.dispatch;

import org.springframework.stereotype.Component;
import ru.job4j.todo.model.Task;
import ru.job4j.todo.repository.task.TaskRepository;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.Predicate;

@Component
public class TaskFilterDispatcher {

    private final TaskRepository repository;

    private final LinkedHashMap<Predicate<String>, Function<String, Collection<Task>>> dispatch
            = new LinkedHashMap<>();

    public TaskFilterDispatcher(TaskRepository repository) {
        this.repository = repository;
        init();
    }

    private void init() {
        load(
                filter -> Objects.equals(filter, "completed"),
                filter -> repository.findByDone(true)
        );
        load(
                filter -> Objects.equals(filter, "new"),
                filter -> repository.findByCreatedAfter(LocalDateTime.now().minusDays(1))
        );
        load(
                filter -> Objects.equals(filter, "all"),
                filter -> repository.findAll()
        );
    }

    public void load(Predicate<String> predicate, Function<String, Collection<Task>> handle) {
        dispatch.put(predicate, handle);
    }

    public Collection<Task> access(String filter) {
        for (Predicate<String> predicate : this.dispatch.keySet()) {
            if (predicate.test(filter)) {
                return dispatch.get(predicate).apply(filter);
            }
        }
        throw new IllegalStateException("Не найден обработчик для фильтра: " + filter);
    }
}
