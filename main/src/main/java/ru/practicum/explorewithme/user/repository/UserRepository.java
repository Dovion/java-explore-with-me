package ru.practicum.explorewithme.user.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.practicum.explorewithme.user.model.User;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {

    @Query("SELECT u FROM User as u WHERE u.id IN :ids")
    public List<User> findAllById(List<Long> ids, Pageable pageable);

    @Query("SELECT u FROM User as u WHERE u.id IN :ids")
    public List<User> findAllByIdWithoutPage(List<Long> ids);
}
