package ru.practicum.explorewithme.publicAPI;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.explorewithme.category.dto.CategoryFullDto;
import ru.practicum.explorewithme.compilation.dto.CompilationFullDto;
import ru.practicum.explorewithme.event.dto.EventFullDto;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PublicServiceImpl implements PublicService {
    @Override
    public List<EventFullDto> getAllEvents(String text, Long[] categories, boolean paid, String rangeStart, String rangeEnd, boolean onlyAvailable, String sort, Integer size) {
        return null;
    }

    @Override
    public EventFullDto getEventById(Long id) {
        return null;
    }

    @Override
    public List<CompilationFullDto> getAllCompilations(boolean pinned, Integer from, Integer size) {
        return null;
    }

    @Override
    public CompilationFullDto getCompilationById(Long id) {
        return null;
    }

    @Override
    public List<CategoryFullDto> getAllCategories(Integer from, Integer size) {
        return null;
    }

    @Override
    public CategoryFullDto getCategoryById(Long id) {
        return null;
    }
}
