package ru.practicum.explorewithme.event.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.hibernate.Hibernate;
import org.springframework.format.annotation.DateTimeFormat;
import ru.practicum.explorewithme.category.model.Category;
import ru.practicum.explorewithme.comment.model.Comment;
import ru.practicum.explorewithme.compilation.model.Compilation;
import ru.practicum.explorewithme.user.model.User;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "event")
public class Event {
    @Column(name = "event_annotation")
    private String annotation;
    @JoinColumn(name = "event_category_id")
    @ManyToOne
    private Category category;
    @Column(name = "event_confirmed_requests")
    private Long confirmedRequests;
    @Column(name = "event_date")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss", iso = DateTimeFormat.ISO.DATE_TIME)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime date;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @JoinColumn(name = "event_initiator_id")
    @ManyToOne
    private User initiator;
    @Column(name = "event_paid")
    private Boolean paid;
    @Column(name = "event_title")
    private String title;
    @Column(name = "event_views")
    private Long views;
    @Column(name = "event_created_on")
    private LocalDateTime created;
    @Column(name = "event_description")
    private String description;
    @Column(name = "event_participant_limit")
    private Long participantLimit;
    @Column(name = "event_request_moderation")
    private Boolean requestModeration;
    @Column(name = "event_state")
    @Enumerated(EnumType.STRING)
    private EventState eventState;
    @Column(name = "event_location_lon")
    private double longitude;
    @Column(name = "event_location_lat")
    private double latitude;
    @Column(name = "event_published_on")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss", iso = DateTimeFormat.ISO.DATE_TIME)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime publishedOn;
    @Column(name = "event_only_available")
    private Boolean onlyAvailable;
    @JoinTable(name = "compilation_event",
            joinColumns = @JoinColumn(name = "event_id"),
            inverseJoinColumns = @JoinColumn(name = "compilation_id"))
    @ManyToMany
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private List<Compilation> eventCompilations;
    @JoinTable(name = "comment_event",
            joinColumns = @JoinColumn(name = "event_id"),
            inverseJoinColumns = @JoinColumn(name = "comment_id"))
    @OneToMany
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private List<Comment> comments = new ArrayList<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Event event = (Event) o;
        return id != null && Objects.equals(id, event.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
