package ru.practicum.explorewithme.request.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RequestDto {
    @FutureOrPresent
    private LocalDateTime created;
    @NotNull
    @Positive
    private Long event;
    private Long id;
    @NotNull
    @Positive
    private Long requester;
    private String status;
}
