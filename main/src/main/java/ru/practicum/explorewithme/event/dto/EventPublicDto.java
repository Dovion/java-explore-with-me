package ru.practicum.explorewithme.event.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.explorewithme.category.dto.CategoryFullDto;
import ru.practicum.explorewithme.user.dto.UserDto;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EventPublicDto {

    private String annotation;
    private CategoryFullDto category;
    private Long confirmedRequests;
    private LocalDateTime eventDate;
    private Long id;
    private UserDto initiator;
    private boolean paid;
    private String title;
    private Long views;
}
