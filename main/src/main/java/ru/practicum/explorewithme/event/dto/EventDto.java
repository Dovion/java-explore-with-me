package ru.practicum.explorewithme.event.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EventDto {
    private String annotation;
    private Long categoryId;
    private String description;
    private LocalDateTime eventDate;
    private boolean paid;
    private String title;
    private Long views;
    private LocalDateTime created;
    private Long limit;
    private boolean requestModeration;
    private String state;
    private double longitude;
    private double latitude;


}
