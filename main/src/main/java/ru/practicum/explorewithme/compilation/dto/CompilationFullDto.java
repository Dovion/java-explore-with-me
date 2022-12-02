package ru.practicum.explorewithme.compilation.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.explorewithme.event.dto.EventFullDto;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CompilationFullDto {

    private List<EventFullDto> events;
    private Long id;
    private boolean pinned;
    private String title;
}
