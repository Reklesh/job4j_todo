package ru.job4j.todo.repository.category;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.job4j.todo.model.Category;
import ru.job4j.todo.repository.CrudRepository;

import java.util.List;
import java.util.Map;

@Repository
@AllArgsConstructor
public class HbmCategoryRepository implements CategoryRepository {

    private final CrudRepository crudRepository;

    @Override
    public List<Category> findAll() {
        return crudRepository.query("from Category ORDER BY id", Category.class);
    }

    @Override
    public List<Category> findAllByIds(List<Integer> categoryIds) {
        return crudRepository.query(
                "from Category WHERE id IN (:fCategoryIds) ORDER BY id", Category.class,
                Map.of("fCategoryIds", categoryIds));
    }
}