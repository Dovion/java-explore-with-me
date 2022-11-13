package ru.practicum.explorewithme.compilation.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.explorewithme.event.model.Event;

import javax.persistence.*;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "compilation")
public class Compilation {

    @Column(name = "compilation_event_id")
    @ManyToMany
    private List<Event> compilationEvents;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "compilation_pinned")
    private boolean pinned;
    @Column(name = "compilation_title")
    private String title;

}
