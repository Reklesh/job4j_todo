package ru.job4j.todo.repository.task;

import lombok.AllArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;
import ru.job4j.todo.model.Task;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class HbmTaskRepository implements TaskRepository {

    private final SessionFactory sf;

    @Override
    public Task save(Task task) {
        Session session = sf.openSession();
        try {
            session.beginTransaction();
            session.save(task);
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
        return task;
    }

    @Override
    public boolean deleteById(Integer id) {
        Session session = sf.openSession();
        boolean isDeleted = false;
        try {
            session.beginTransaction();
            Query<Task> query = session.createQuery("DELETE Task WHERE id = :fId")
                    .setParameter("fId", id);
            isDeleted = query.executeUpdate() > 0;
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
        return isDeleted;
    }

    @Override
    public boolean update(Task task) {
        Session session = sf.openSession();
        boolean isUpdated = false;
        try {
            session.beginTransaction();
            Query<Task> query = session.createQuery(
                            "UPDATE Task SET name = :fName, description = :fDescription "
                                    + "WHERE id = :fId")
                    .setParameter("fName", task.getName())
                    .setParameter("fDescription", task.getDescription())
                    .setParameter("fId", task.getId());
            isUpdated = query.executeUpdate() > 0;
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
        return isUpdated;
    }

    @Override
    public boolean updateDone(Integer id, boolean isDone) {
        Session session = sf.openSession();
        boolean isUpdated = false;
        try {
            session.beginTransaction();
            Query<Task> query = session.createQuery(
                            "UPDATE Task SET done = :fDone WHERE id = :fId")
                    .setParameter("fDone", isDone)
                    .setParameter("fId", id);
            isUpdated = query.executeUpdate() > 0;
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
        return isUpdated;
    }

    @Override
    public Optional<Task> findById(Integer id) {
        try (Session session = sf.openSession()) {
            return session.createQuery("from Task where id = :fId", Task.class)
                    .setParameter("fId", id)
                    .uniqueResultOptional();
        }
    }

    @Override
    public Collection<Task> findAll() {
        try (Session session = sf.openSession()) {
            return session.createQuery("from Task ORDER BY id", Task.class)
                    .list();
        }
    }

    @Override
    public Collection<Task> findByCreatedAfter(LocalDateTime localDateTime) {
        try (Session session = sf.openSession()) {
            return session.createQuery("from Task WHERE created > :fCreated ORDER BY id", Task.class)
                    .setParameter("fCreated", localDateTime)
                    .list();
        }
    }

    @Override
    public Collection<Task> findByDone(Boolean isDone) {
        try (Session session = sf.openSession()) {
            return session.createQuery("from Task WHERE done = :fDone ORDER BY id", Task.class)
                    .setParameter("fDone", isDone)
                    .list();
        }
    }
}
