package ru.practicum.explorewithme.request.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.explorewithme.event.model.Event;
import ru.practicum.explorewithme.user.model.User;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "request")
public class Request {

    @Column(name = "request_created")
    private LocalDateTime created;
    @Column(name = "request_event_id")
    @ManyToOne
    private Event event;
    @Column(name = "request_requester_id")
    @ManyToOne
    private User requester;
    @Column(name = "request_status")
    private RequestState state;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;


}
