package ru.practicum.explorewithme.compilation.mapper;

import ru.practicum.explorewithme.compilation.dto.CompilationDto;
import ru.practicum.explorewithme.compilation.dto.CompilationFullDto;
import ru.practicum.explorewithme.compilation.model.Compilation;
import ru.practicum.explorewithme.event.dto.EventFullDto;
import ru.practicum.explorewithme.event.mapper.EventMapper;
import ru.practicum.explorewithme.event.model.Event;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CompilationMapper {

    public static Compilation compilationDtoInCompilation(CompilationDto compilationDto, List<Event> eventList){
        return new Compilation(eventList,
                null,
                compilationDto.isPinned(),
                compilationDto.getTitle());
    }

    public static CompilationFullDto compilationToCompilationFullDto(Compilation compilation){
        List<EventFullDto> eventFullDtoList = new ArrayList<>();
        for(var event: compilation.getCompilationEvents()){
         //   eventFullDtoList.add(EventMapper.eventToDtoEvent(event)); todo
        }
        return new CompilationFullDto(eventFullDtoList,
                compilation.getId(),
                compilation.isPinned(),
                compilation.getTitle());
    }
}
