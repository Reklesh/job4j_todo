package ru.job4j.todo.repository.task;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.job4j.todo.model.Task;
import ru.job4j.todo.repository.CrudRepository;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Map;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class HbmTaskRepository implements TaskRepository {

    private final CrudRepository crudRepository;

    @Override
    public Task save(Task task) {
        crudRepository.run(session -> session.save(task));
        return task;
    }

    @Override
    public boolean deleteById(Integer id) {
        return crudRepository.execute(
                "DELETE Task WHERE id = :fId",
                Map.of("fId", id));
    }

    @Override
    public boolean update(Task task) {
        return crudRepository.execute(
                "UPDATE Task SET name = :fName, description = :fDescription WHERE id = :fId",
                Map.of("fName", task.getName(), "fDescription", task.getDescription(),
                        "fId", task.getId()));
    }

    @Override
    public boolean updateDone(Integer id, boolean isDone) {
        return crudRepository.execute(
                "UPDATE Task SET done = :fDone WHERE id = :fId",
                Map.of("fDone", isDone, "fId", id));
    }

    @Override
    public Optional<Task> findById(Integer id) {
        return crudRepository.optional(
                "from Task where id = :fId", Task.class,
                Map.of("fId", id));
    }

    @Override
    public Collection<Task> findAll() {
        return crudRepository.query(
                "from Task ORDER BY id", Task.class);
    }

    @Override
    public Collection<Task> findByCreatedAfter(LocalDateTime localDateTime) {
        return crudRepository.query(
                "from Task WHERE created > :fCreated ORDER BY id", Task.class,
                Map.of("fCreated", localDateTime));
    }

    @Override
    public Collection<Task> findByDone(Boolean isDone) {
        return crudRepository.query("from Task WHERE done = :fDone ORDER BY id", Task.class,
                Map.of("fDone", isDone));
    }
}
