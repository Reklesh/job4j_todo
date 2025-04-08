package ru.job4j.todo.repository.user;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import ru.job4j.todo.model.User;
import ru.job4j.todo.repository.CrudRepository;

import java.util.Map;
import java.util.Optional;

@Slf4j
@Repository
@AllArgsConstructor
public class HbmUserRepository implements UserRepository {

    private final CrudRepository crudRepository;

    @Override
    public Optional<User> save(User user) {
        try {
            crudRepository.run(session -> session.save(user));
            return Optional.of(user);
        } catch (Exception e) {
            log.error("Ошибка при сохранении пользователя: пользователь с login {} уже существует", user.getLogin(), e);
        }
        return Optional.empty();
    }

    @Override
    public Optional<User> findByLoginAndPassword(String login, String password) {
        try {
            return crudRepository.optional(
                    "from User where login = :fLogin AND password = :fPassword", User.class,
                    Map.of("fLogin", login, "fPassword", password));
        } catch (Exception e) {
            log.error("Произошла ошибка при попытке найти пользователя с login {} и password {}", login, password, e);
        }
        return Optional.empty();
    }
}
