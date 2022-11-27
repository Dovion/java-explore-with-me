package ru.practicum.explorewithme.publicAPI;

import ru.practicum.explorewithme.category.dto.CategoryFullDto;
import ru.practicum.explorewithme.compilation.dto.CompilationFullDto;
import ru.practicum.explorewithme.event.dto.EventFullDto;
import ru.practicum.explorewithme.exception.EntityNotFoundException;

import javax.servlet.http.HttpServletRequest;
import java.net.URISyntaxException;
import java.util.List;

public interface PublicService {
    List<EventFullDto> getAllEvents(String text,
                                    Long[] categories,
                                    Boolean paid,
                                    String rangeStart,
                                    String rangeEnd,
                                    Boolean onlyAvailable,
                                    String sort,
                                    Integer from,
                                    Integer size,
                                    HttpServletRequest request);

    EventFullDto getEventById(Long id, HttpServletRequest request) throws EntityNotFoundException, URISyntaxException;

    List<CompilationFullDto> getAllCompilations(Boolean pinned, Integer from, Integer size);

    CompilationFullDto getCompilationById(Long id) throws EntityNotFoundException;

    List<CategoryFullDto> getAllCategories(Integer from, Integer size);

    CategoryFullDto getCategoryById(Long id) throws EntityNotFoundException;

}
