package ru.practicum.explorewithme.adminAPI;

import ru.practicum.explorewithme.category.dto.CategoryDto;
import ru.practicum.explorewithme.category.dto.CategoryFullDto;
import ru.practicum.explorewithme.compilation.dto.CompilationDto;
import ru.practicum.explorewithme.compilation.dto.CompilationFullDto;
import ru.practicum.explorewithme.event.dto.EventDto;
import ru.practicum.explorewithme.event.dto.EventFullDto;
import ru.practicum.explorewithme.user.dto.UserDto;

import java.util.List;

public interface AdministratorService {

    List<EventFullDto> getAllEvents(Long[] users,
                                    String[] states,
                                    Long[] categories,
                                    String rangeStart,
                                    String rangeEnd,
                                    Integer from,
                                    Integer size);

    EventFullDto updateEvent(Long id, EventDto eventDto);

    EventFullDto publishEvent(Long id);

    EventFullDto rejectEvent(Long id);

    CategoryFullDto updateCategory(CategoryFullDto categoryFullDto);

    CategoryFullDto addCategory(CategoryDto categoryDto);

    void removeCategoryById(Long id);

    UserDto getAllUsers(Long[] ids,
                        Integer from,
                        Integer size);

    UserDto addUser(UserDto userDto);

    void removeUser(Long id);

    CompilationFullDto addCompilation(CompilationDto compilationDto);

    void removeCompilation(Long id);

    void removeEventFromCompilation(Long compId, Long eventId);

    void addEventInCompilation(Long compId, Long eventId);

    void unpinCompilation(Long id);

    void pinCompilation(Long id);
}
