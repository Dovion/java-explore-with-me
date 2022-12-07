package ru.practicum.explorewithme.event.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.practicum.explorewithme.event.model.Event;
import ru.practicum.explorewithme.event.model.EventState;
import ru.practicum.explorewithme.user.model.User;

import java.time.LocalDateTime;
import java.util.List;

public interface EventRepository extends JpaRepository<Event, Long> {

    @Query("SELECT e FROM Event e " +
            " WHERE e.initiator.id IN :users " +
            " AND e.eventState  IN :states " +
            " AND e.category.id IN :categories " +
            " AND e.date BETWEEN :startDate AND :endDate "
    )
    List<Event> getAllByUsersAndStatesAndCategoriesAndDates(List<Long> users, List<EventState> states, List<Long> categories, LocalDateTime startDate, LocalDateTime endDate, Pageable pageable);

    @Query("SELECT e FROM Event e" +
            " WHERE e.annotation LIKE %:text% OR e.description LIKE %:text%" +
            " AND e.category.id IN :categories" +
            " AND e.paid = :paid" +
            " AND e.date BETWEEN :rangeStart AND :rangeEnd" +
            " AND ((:onlyAvailable = false) OR (:onlyAvailable = true AND e.participantLimit = 0) OR (:onlyAvailable = true AND e.participantLimit > e.confirmedRequests))")
    List<Event> getAllByTextAndCategoriesAndPaidAndDatesAndOnlyAvailable(@Param("text") String text,
                                                                         List<Long> categories,
                                                                         Boolean paid,
                                                                         LocalDateTime rangeStart,
                                                                         LocalDateTime rangeEnd,
                                                                         Boolean onlyAvailable,
                                                                         Pageable pageable);

    List<Event> getAllByInitiatorId(Long userId, Pageable pageable);

    @Modifying
    @Query("UPDATE Event as e set e.views = e.views + 1" +
            " WHERE e.id = :eventId")
    void addViewByEventId(Long eventId);

    @Query("SELECT e FROM Event as e WHERE e.id IN :ids")
    public List<Event> findAllByIdWithoutPage(List<Long> ids);

}
