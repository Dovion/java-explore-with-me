package ru.practicum.explorewithme.request.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RequestDto {

    private LocalDate created;
    private Long eventId;
    private Long id;
    private Long requesterId;
    private String status;
}
