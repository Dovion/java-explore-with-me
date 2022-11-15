package ru.practicum.explorewithme.event.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.explorewithme.event.model.Location;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EventDto {
    private String annotation;
    private Long categoryId;
    private String description;
    private LocalDateTime eventDate;
    private Location location;
    private boolean paid;
    private Long participantLimit;
    private boolean requestModeration;
    private String title;

}
