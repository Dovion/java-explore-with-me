package ru.practicum.explorewithme.comment.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.practicum.explorewithme.comment.model.Comment;
import ru.practicum.explorewithme.comment.model.CommentStatus;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    @Query("SELECT c FROM Comment as c WHERE c.event.id = :eventId and c.author.id = :userId")
    public List<Comment> getAllByEventIdAndUserId(Long eventId, Long userId, Pageable pageable);

    @Query("SELECT c FROM Comment as c WHERE c.status = ru.practicum.explorewithme.comment.model.CommentStatus.PUBLISHED")
    public List<Comment> getAllPublished();

    @Query("SELECT c FROM Comment c" +
            " WHERE c.text LIKE %:text%" +
            " AND c.author.id IN :users" +
            " AND c.event.id IN :events" +
            " AND c.status IN :states")
    List<Comment> getAllByUsersAndEventsAndStatesAndText(@Param("text") String text,
                                                         List<Long> users,
                                                         List<Long> events,
                                                         List<CommentStatus> states,
                                                         Pageable pageable);
}
