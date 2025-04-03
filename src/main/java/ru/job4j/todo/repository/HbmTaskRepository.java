package ru.job4j.todo.repository;

import lombok.AllArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
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
    public void deleteById(Integer id) {
        Session session = sf.openSession();
        try {
            session.beginTransaction();
            session.createQuery("DELETE Task WHERE id = :fId")
                    .setParameter("fId", id)
                    .executeUpdate();
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
    }

    @Override
    public boolean update(Task task) {
        Session session = sf.openSession();
        try {
            session.beginTransaction();
            session.update(task);
            session.getTransaction().commit();
            return true;
        } catch (Exception e) {
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
        return false;
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
