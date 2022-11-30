package ru.practicum.explorewithme.category.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.practicum.explorewithme.category.model.Category;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    @Query("SELECT c FROM Category as c WHERE c.id IN :ids")
    public List<Category> findAllByIdWithoutPage(List<Long> ids);
}
