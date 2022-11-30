package ru.practicum.explorewithme.compilation.model;

import lombok.*;
import org.hibernate.Hibernate;
import ru.practicum.explorewithme.event.model.Event;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "compilation")
public class Compilation {

    @JoinTable(name = "compilation_event",
            joinColumns = @JoinColumn(name = "compilation_id"),
            inverseJoinColumns = @JoinColumn(name = "event_id"))
    @ManyToMany
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private List<Event> compilationEvents;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "compilation_pinned")
    private boolean pinned;
    @Column(name = "compilation_title")
    private String title;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Compilation that = (Compilation) o;
        return id != null && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
