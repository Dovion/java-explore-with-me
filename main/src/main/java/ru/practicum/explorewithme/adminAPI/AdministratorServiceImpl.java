package ru.practicum.explorewithme.adminAPI;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.practicum.explorewithme.category.dto.CategoryDto;
import ru.practicum.explorewithme.category.dto.CategoryFullDto;
import ru.practicum.explorewithme.category.mapper.CategoryMapper;
import ru.practicum.explorewithme.category.model.Category;
import ru.practicum.explorewithme.category.repository.CategoryRepository;
import ru.practicum.explorewithme.compilation.dto.CompilationDto;
import ru.practicum.explorewithme.compilation.dto.CompilationFullDto;
import ru.practicum.explorewithme.compilation.mapper.CompilationMapper;
import ru.practicum.explorewithme.compilation.model.Compilation;
import ru.practicum.explorewithme.compilation.repository.CompilationRepository;
import ru.practicum.explorewithme.event.dto.EventDto;
import ru.practicum.explorewithme.event.dto.EventFullDto;
import ru.practicum.explorewithme.event.mapper.EventMapper;
import ru.practicum.explorewithme.event.model.Event;
import ru.practicum.explorewithme.event.model.EventState;
import ru.practicum.explorewithme.event.repository.EventRepository;
import ru.practicum.explorewithme.exception.ConflictException;
import ru.practicum.explorewithme.exception.EntityNotFoundException;
import ru.practicum.explorewithme.exception.EventStateException;
import ru.practicum.explorewithme.user.dto.UserDto;
import ru.practicum.explorewithme.user.mapper.UserMapper;
import ru.practicum.explorewithme.user.model.User;
import ru.practicum.explorewithme.user.repository.UserRepository;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class AdministratorServiceImpl implements AdministratorService {

    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final EventRepository eventRepository;
    private final CompilationRepository compilationRepository;

    @Override
    public List<EventFullDto> getAllEvents(Long[] users, String[] states, Long[] categories, String rangeStart, String rangeEnd, Integer from, Integer size) throws EntityNotFoundException {
        for (Long id : categories) {
            if (!categoryRepository.existsById(id)) {
                throw new EntityNotFoundException("Can`t get all events: category not found");
            }
        }
        for (Long id : users) {
            if (!userRepository.existsById(id)) {
                throw new EntityNotFoundException("Can`t get all events: user not found");
            }
        }
        List<EventState> eventStates = new ArrayList<>();
        if (states != null) {
            for (String state : states) {
                try {
                    eventStates.add(EventState.valueOf(state));
                } catch (IllegalArgumentException exception) {
                    throw new IllegalArgumentException("Can`t get all events: state '" + state + "' not found.");
                }
            }
        }
        LocalDateTime startDate = LocalDateTime.now();
        if (rangeStart != null) {
            startDate = LocalDateTime.parse(rangeStart, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        }
        LocalDateTime endDate = LocalDateTime.now();
        if (rangeStart != null) {
            endDate = LocalDateTime.parse(rangeEnd, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        }
        Pageable pageable = PageRequest.of(from / size, size);
        List<Event> eventList = eventRepository.getAllByUsersAndStatesAndCategoriesAndDates(users, eventStates, categories, startDate, endDate, pageable);
        List<EventFullDto> eventFullDtoList = new ArrayList<>();
        for (var event : eventList) {
            eventFullDtoList.add(EventMapper.eventToEventFullDto(event));
        }
        log.info("Getting succes");
        return eventFullDtoList;
    }

    @Override
    public EventFullDto updateEvent(Long id, EventDto eventDto) throws EntityNotFoundException {
        Event event = eventRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Can`t update event: event not found"));
        event.setAnnotation(eventDto.getAnnotation());
        event.setPaid(eventDto.getPaid());
        event.setDescription(eventDto.getDescription());
        event.setTitle(eventDto.getTitle());
        event.setParticipantLimit(eventDto.getParticipantLimit());
        eventRepository.save(event);
        log.info("Saving success");
        return EventMapper.eventToEventFullDto(event);
    }

    @Override
    public EventFullDto publishEvent(Long id) throws EntityNotFoundException, EventStateException {
        Event event = eventRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Can`t publish event: event not found"));
        if (event.getEventState() != EventState.PENDING) {
            throw new EventStateException("Can`t publish event: event state isn`t pending");
        }
        event.setPublishedOn(LocalDateTime.now());
        event.setEventState(EventState.PUBLISHED);
        eventRepository.save(event);
        log.info("Publish success");
        return EventMapper.eventToEventFullDto(event);
    }

    @Override
    public EventFullDto rejectEvent(Long id) throws EntityNotFoundException, EventStateException {
        Event event = eventRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Can`t reject event: event not found"));
        if (event.getEventState() != EventState.PENDING) {
            throw new EventStateException("Can`t reject event: event state isn`t pending");
        }
        event.setEventState(EventState.CANCELED);
        eventRepository.save(event);
        log.info("Reject success");
        return EventMapper.eventToEventFullDto(event);
    }

    @Override
    public CategoryFullDto updateCategory(CategoryFullDto categoryFullDto) throws EntityNotFoundException, ConflictException {
        Category category = categoryRepository.findById(categoryFullDto.getId()).orElseThrow(() -> new EntityNotFoundException("Can`t update category: category not found"));
        Category newCategory = CategoryMapper.categoryFullDtoToCategory(categoryFullDto);
        try {
            categoryRepository.save(newCategory);
        } catch (DataAccessException e) {
            throw new ConflictException("Can`t update category: category`s name already exist");
        }
        log.info("Update success");
        return CategoryMapper.categoryToCategoryFullDto(newCategory);
    }

    @Override
    public CategoryFullDto addCategory(CategoryDto categoryDto) throws ConflictException {
        Category category = CategoryMapper.categoryDtoToCategory(categoryDto);
        try {
            categoryRepository.save(category);
        } catch (DataAccessException e) {
            throw new ConflictException("Can`t add category: category`s name already exist");
        }
        log.info("Adding success");
        return CategoryMapper.categoryToCategoryFullDto(category);
    }

    @Override
    public void removeCategoryById(Long id) throws EntityNotFoundException {
        Category category = categoryRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Can`t remove category: category not found"));
        categoryRepository.deleteById(id);
        log.info("Remove success");
    }

    @Override
    public List<UserDto> getAllUsers(Long[] ids, Integer from, Integer size) {
        Pageable pageable = PageRequest.of(from / size, size);
        List<User> userList = userRepository.findAllById(ids, pageable);
        List<UserDto> userDtoList = new ArrayList<>();
        for (var user : userList) {
            userDtoList.add(UserMapper.userToUserDto(user));
        }
        log.info("Getting success");
        return userDtoList;
    }

    @Override
    public UserDto addUser(UserDto userDto) throws ConflictException {
        User user = UserMapper.userDtoToUser(userDto);
        try {
            userRepository.save(user);
        } catch (DataAccessException e) {
            throw new ConflictException("Can`t add user: user`s name already exist");
        }
        log.info("Adding success");
        return UserMapper.userToUserDto(user);
    }

    @Override
    public void removeUser(Long id) throws EntityNotFoundException {
        if (!userRepository.existsById(id)) {
            throw new EntityNotFoundException("Can`t remove user: user not found");
        }
        log.info("Removing success");
        userRepository.deleteById(id);
    }

    @Override
    public CompilationFullDto addCompilation(CompilationDto compilationDto) {
        Compilation compilation = CompilationMapper.compilationDtoToCompilation(compilationDto, eventRepository.
                findAllById(compilationDto.getEvents()));
        compilationRepository.save(compilation);
        log.info("Saving success");
        return CompilationMapper.compilationToCompilationFullDto(compilation);
    }

    @Override
    public void removeCompilation(Long id) throws EntityNotFoundException {
        Compilation compilation = compilationRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Can`t remove compilation: compilation not found"));
        log.info("Remove success");
        compilationRepository.deleteById(id);
    }

    @Override
    public void removeEventFromCompilation(Long compId, Long eventId) throws EntityNotFoundException {
        Compilation compilation = compilationRepository.findById(compId).orElseThrow(() -> new EntityNotFoundException("Can`t remove event from compilation: compilation not found"));
        List<Event> events = compilation.getCompilationEvents();
        if (events != null) {
            try {
                for (Event event : events) {
                    if (event.getId() == eventId) {
                        compilation.getCompilationEvents().remove(event);
                        event.getEventCompilations().remove(compilation);
                        eventRepository.save(event);
                    }
                }
            } catch (ConcurrentModificationException e) {
                compilationRepository.save(compilation);
            }
        }
        log.info("Saving success");
        compilationRepository.save(compilation);
    }

    @Override
    public void addEventInCompilation(Long compId, Long eventId) throws EntityNotFoundException {
        Compilation compilation = compilationRepository.findById(compId).orElseThrow(() -> new EntityNotFoundException("Can`t add event in compilation: compilation not found"));
        Event event = eventRepository.findById(eventId).orElseThrow(() -> new EntityNotFoundException("Can`t add event in compilation: event not found"));
        compilation.getCompilationEvents().add(event);
        event.getEventCompilations().add(compilation);
        eventRepository.save(event);
        compilationRepository.save(compilation);
        log.info("Adding success");
    }

    @Override
    public void unpinCompilation(Long id) throws EntityNotFoundException {
        Compilation compilation = compilationRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Can`t unpin compilation: compilation not found"));
        compilation.setPinned(false);
        compilationRepository.save(compilation);
        log.info("Adding success");
    }

    @Override
    public void pinCompilation(Long id) throws EntityNotFoundException {
        Compilation compilation = compilationRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Can`t pin compilation: compilation not found"));
        compilation.setPinned(true);
        compilationRepository.save(compilation);
        log.info("Saving success");
    }
}
