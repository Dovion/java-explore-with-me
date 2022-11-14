package ru.practicum.explorewithme.publicAPI;

import ru.practicum.explorewithme.category.dto.CategoryFullDto;
import ru.practicum.explorewithme.compilation.dto.CompilationFullDto;
import ru.practicum.explorewithme.event.dto.EventFullDto;

import java.util.List;

public interface PublicService {
    List<EventFullDto> getAllEvents(String text,
                              Long[] categories,
                              boolean paid,
                              String rangeStart,
                              String rangeEnd,
                              boolean onlyAvailable,
                              String sort,
                              Integer size);

    EventFullDto getEventById(Long id);

    List<CompilationFullDto> getAllCompilations(boolean pinned, Integer from, Integer size);

    CompilationFullDto getCompilationById(Long id);

    List<CategoryFullDto> getAllCategories(Integer from, Integer size);

    CategoryFullDto getCategoryById(Long id);

}
