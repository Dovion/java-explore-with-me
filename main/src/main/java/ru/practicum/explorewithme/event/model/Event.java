package ru.practicum.explorewithme.event.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.explorewithme.category.model.Category;
import ru.practicum.explorewithme.user.model.User;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "event")
public class Event {
    @Column(name = "event_annotation")
    private String annotation;
    @Column(name = "event_category_id")
    @OneToOne
    private Category categoryId;
    @Column(name = "event_confirmed_requests")
    private Long confirmedRequests;
    @Column(name = "event_date")
    private LocalDateTime date;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "event_initiator_id")
    @OneToOne
    private User initiatior;
    @Column(name = "event_paid")
    private boolean paid;
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
    private boolean requestModeration;
    @Column(name = "event_state")
    private EventState eventState;
    /// @Column(name = "event_location_lon")
  ///  private double longitude;
   /// @Column(name = "event_location_lat")
  ///  private double latitude;
}
