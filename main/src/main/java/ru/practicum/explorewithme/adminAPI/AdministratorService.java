package ru.practicum.explorewithme.adminAPI;

import ru.practicum.explorewithme.category.dto.CategoryDto;
import ru.practicum.explorewithme.category.dto.CategoryFullDto;
import ru.practicum.explorewithme.compilation.dto.CompilationDto;
import ru.practicum.explorewithme.compilation.dto.CompilationFullDto;
import ru.practicum.explorewithme.event.dto.EventDto;
import ru.practicum.explorewithme.event.dto.EventFullDto;
import ru.practicum.explorewithme.exception.ConflictException;
import ru.practicum.explorewithme.exception.EntityNotFoundException;
import ru.practicum.explorewithme.exception.EventStateException;
import ru.practicum.explorewithme.exception.ValidationException;
import ru.practicum.explorewithme.user.dto.UserDto;

import java.util.List;

public interface AdministratorService {

    List<EventFullDto> getAllEvents(Long[] users,
                                    String[] states,
                                    Long[] categories,
                                    String rangeStart,
                                    String rangeEnd,
                                    Integer from,
                                    Integer size) throws EntityNotFoundException;

    EventFullDto updateEvent(Long id, EventDto eventDto) throws EntityNotFoundException;

    EventFullDto publishEvent(Long id) throws EntityNotFoundException, EventStateException;

    EventFullDto rejectEvent(Long id) throws EntityNotFoundException, EventStateException;

    CategoryFullDto updateCategory(CategoryFullDto categoryFullDto) throws EntityNotFoundException, ValidationException, ConflictException;

    CategoryFullDto addCategory(CategoryDto categoryDto) throws ValidationException, ConflictException;

    void removeCategoryById(Long id) throws EntityNotFoundException;

    List<UserDto> getAllUsers(Long[] ids,
                              Integer from,
                              Integer size);

    UserDto addUser(UserDto userDto) throws ValidationException, ConflictException;

    void removeUser(Long id) throws EntityNotFoundException;

    CompilationFullDto addCompilation(CompilationDto compilationDto);

    void removeCompilation(Long id) throws EntityNotFoundException;

    void removeEventFromCompilation(Long compId, Long eventId) throws EntityNotFoundException;

    void addEventInCompilation(Long compId, Long eventId) throws EntityNotFoundException;

    void unpinCompilation(Long id) throws EntityNotFoundException;

    void pinCompilation(Long id) throws EntityNotFoundException;
}
