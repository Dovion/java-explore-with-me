package ru.practicum.explorewithme.adminAPI;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.practicum.explorewithme.category.dto.CategoryDto;
import ru.practicum.explorewithme.category.dto.CategoryFullDto;
import ru.practicum.explorewithme.category.repository.CategoryRepository;
import ru.practicum.explorewithme.compilation.dto.CompilationDto;
import ru.practicum.explorewithme.compilation.dto.CompilationFullDto;
import ru.practicum.explorewithme.event.dto.EventDto;
import ru.practicum.explorewithme.event.dto.EventFullDto;
import ru.practicum.explorewithme.event.mapper.EventMapper;
import ru.practicum.explorewithme.event.model.Event;
import ru.practicum.explorewithme.event.model.EventState;
import ru.practicum.explorewithme.event.repository.EventRepository;
import ru.practicum.explorewithme.user.dto.UserDto;
import ru.practicum.explorewithme.user.mapper.UserMapper;
import ru.practicum.explorewithme.user.model.User;
import ru.practicum.explorewithme.user.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AdministratorServiceImpl implements AdministratorService{

    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final EventRepository eventRepository;
    @Override
    public List<EventFullDto> getAllEvents(Long[] users, String[] states, Long[] categories, String rangeStart, String rangeEnd, Integer from, Integer size) {
        for (Long id : categories) {
            if (!categoryRepository.existsById(id)) {
              //  throw Exception();
            }
        }
        for (Long id : users) {
            if (!userRepository.existsById(id)) {
                //  throw Exception();
            }
        }
        List<EventState> eventStates = new ArrayList<>();
        if (states != null) {
            for (String state : states) {
                try {
                    eventStates.add(EventState.valueOf(state));
                } catch (IllegalArgumentException exception) {
                    throw new IllegalArgumentException("Stats: " + state + " not found.");
                }
            }
        }
        LocalDateTime startDate = LocalDateTime.now();
        if (rangeStart != null) {
            startDate = LocalDateTime.parse(rangeStart);
        }
        LocalDateTime endDate = LocalDateTime.now();
        if (rangeStart != null) {
            endDate = LocalDateTime.parse(rangeEnd);
        }
        Pageable pageable = PageRequest.of(from / size, size);
        List<Event> eventList = eventRepository.getAllByUsersAndStatesAndCategories(users, eventStates, categories, startDate, endDate, pageable);
        List<EventFullDto> eventFullDtoList = new ArrayList<>();
        for (var event: eventList){
            eventFullDtoList.add(EventMapper.eventToEventFullDto(event));
        }
        return eventFullDtoList;
    }
//TODO
    @Override
    public EventFullDto updateEvent(Long id, EventDto eventDto) {
        return null;
    }

    @Override
    public EventFullDto publishEvent(Long id) {
        return null;
    }

    @Override
    public EventFullDto rejectEvent(Long id) {
        return null;
    }

    @Override
    public CategoryFullDto updateCategory(CategoryFullDto categoryFullDto) {
        return null;
    }

    @Override
    public CategoryFullDto addCategory(CategoryDto categoryDto) {
        return null;
    }

    @Override
    public void removeCategoryById(Long id) {

    }

    @Override
    public List<UserDto> getAllUsers(Long[] ids, Integer from, Integer size) {
        Pageable pageable = PageRequest.of(from/size, size);
        List<User> userList = userRepository.findAllById(ids, pageable);
        List<UserDto> userDtoList = new ArrayList<>();
        for(var user: userList){
            userDtoList.add(UserMapper.userToUserDto(user));
        }
        return userDtoList;
    }

    @Override
    public UserDto addUser(UserDto userDto) {
        if(userDto.getEmail() == null || userDto.getName() == null){
            //throw new Exception();
        }
        User user = userRepository.save(UserMapper.userDtoToUser(userDto));
        return UserMapper.userToUserDto(user);
    }

    @Override
    public void removeUser(Long id) {
        if(!userRepository.existsById(id)){
           // throw new Exception();
        }
        userRepository.delete(userRepository.getReferenceById(id));
    }

    @Override
    public CompilationFullDto addCompilation(CompilationDto compilationDto) {
        return null;
    }

    @Override
    public void removeCompilation(Long id) {

    }

    @Override
    public void removeEventFromCompilation(Long compId, Long eventId) {

    }

    @Override
    public void addEventInCompilation(Long compId, Long eventId) {

    }

    @Override
    public void unpinCompilation(Long id) {

    }

    @Override
    public void pinCompilation(Long id) {

    }
}
