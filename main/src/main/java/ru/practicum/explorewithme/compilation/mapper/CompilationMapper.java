package ru.practicum.explorewithme.compilation.mapper;

import ru.practicum.explorewithme.compilation.dto.CompilationDto;
import ru.practicum.explorewithme.compilation.dto.CompilationFullDto;
import ru.practicum.explorewithme.compilation.model.Compilation;
import ru.practicum.explorewithme.event.dto.EventFullDto;
import ru.practicum.explorewithme.event.mapper.EventMapper;
import ru.practicum.explorewithme.event.model.Event;

import java.util.ArrayList;
import java.util.List;

public class CompilationMapper {

    public static Compilation compilationDtoToCompilation(CompilationDto compilationDto, List<Event> eventList) {
        return new Compilation(eventList,
                null,
                compilationDto.getPinned(),
                compilationDto.getTitle());
    }

    public static CompilationFullDto compilationToCompilationFullDto(Compilation compilation) {
        List<EventFullDto> eventFullDtoList = new ArrayList<>();
        for (var event : compilation.getCompilationEvents()) {
            eventFullDtoList.add(EventMapper.eventToEventFullDto(event));
        }
        return new CompilationFullDto(eventFullDtoList,
                compilation.getId(),
                compilation.isPinned(),
                compilation.getTitle());
    }
}
