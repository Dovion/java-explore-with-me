package ru.practicum.explorewithme.event.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.explorewithme.event.model.Location;

import javax.validation.constraints.Future;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EventDto {
    private Long eventId;
    @NotBlank
    private String annotation;
    @Positive
    private Long category;
    @NotBlank
    private String description;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    @NotNull
    @Future
    private LocalDateTime eventDate;
    private Location location;
    private Boolean paid;
    @Positive
    private Long participantLimit;
    private Boolean requestModeration;
    @NotBlank
    private String title;

    public EventDto(String annotation,
                    Long categoryId,
                    String description,
                    LocalDateTime eventDate,
                    Location location,
                    Boolean paid,
                    Long participantLimit,
                    Boolean requestModeration,
                    String title) {
        this.annotation = annotation;
        this.category = categoryId;
        this.description = description;
        this.eventDate = eventDate;
        this.location = location;
        this.paid = paid;
        this.participantLimit = participantLimit;
        this.requestModeration = requestModeration;
        this.title = title;
    }
}
