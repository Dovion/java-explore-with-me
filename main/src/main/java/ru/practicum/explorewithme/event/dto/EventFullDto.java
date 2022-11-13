package ru.practicum.explorewithme.event.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.explorewithme.category.dto.CategoryFullDto;
import ru.practicum.explorewithme.event.model.EventState;
import ru.practicum.explorewithme.user.dto.UserDto;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EventFullDto {

    private String annotation;
    private CategoryFullDto category;
    private Long confirmedRequests;
    private LocalDateTime createdOn;
    private String description;
    private LocalDateTime eventDate;
    private Long id;
    private UserDto initiator;
    private LocationDto location;
    private boolean paid;
    private Long participantLimit;
    private LocalDateTime publishedOn;
    private boolean requestModeration;
    private EventState state;
    private String title;
    private Long views;

}
