package ru.practicum.explorewithme.event.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.practicum.explorewithme.event.model.Event;
import ru.practicum.explorewithme.event.model.EventState;

import java.time.LocalDateTime;
import java.util.List;

public interface EventRepository extends JpaRepository<Event, Long> {

    @Query("SELECT e FROM Event e " +
            " WHERE e.initiatior.id IN :users " +
            " AND e.eventState IN :states " +
            " AND e.category.id IN :categories " +
            " AND e.date BETWEEN :startDate AND :endDate "
    )
    List<Event> getAllByUsersAndStatesAndCategories(Long[] users, List<EventState> states, Long[] categories, LocalDateTime startDate, LocalDateTime endDate, Pageable pageable);
}
