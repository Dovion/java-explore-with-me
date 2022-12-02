package ru.practicum.explorewithme.request.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.practicum.explorewithme.request.model.Request;

import java.util.List;

public interface RequestRepository extends JpaRepository<Request, Long> {

    @Query("SELECT r FROM Request r" +
            " JOIN Event e ON r.event.id = e.id" +
            " WHERE e.initiator.id = :userId AND e.id = :eventId"
    )
    List<Request> getAllByUserIdAndEventId(Long userId, Long eventId);

    @Query("UPDATE Request r " +
            " SET r.state = ru.practicum.explorewithme.request.model.RequestState.REJECTED" +
            " WHERE r.event.id = :eventId AND r.state = ru.practicum.explorewithme.request.model.RequestState.PENDING " +
            " ")
    void rejectAllPendingRequest(Long eventId);

    List<Request> getAllByRequesterId(Long requesterId);
}
