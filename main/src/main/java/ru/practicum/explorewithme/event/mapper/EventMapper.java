package ru.practicum.explorewithme.event.mapper;

import ru.practicum.explorewithme.category.mapper.CategoryMapper;
import ru.practicum.explorewithme.category.model.Category;
import ru.practicum.explorewithme.event.dto.EventDto;
import ru.practicum.explorewithme.event.dto.EventFullDto;
import ru.practicum.explorewithme.event.dto.EventPublicDto;
import ru.practicum.explorewithme.event.dto.LocationDto;
import ru.practicum.explorewithme.event.model.Event;
import ru.practicum.explorewithme.event.model.Location;
import ru.practicum.explorewithme.user.mapper.UserMapper;

public class EventMapper {

    public static Event eventDtoToEvent(EventDto eventDto, Category category){
        return new Event(eventDto.getAnnotation(),
                category,
                null,
                eventDto.getEventDate(),
                null,
                null,
                eventDto.isPaid(),
                eventDto.getTitle(),
                null,
                null,
                eventDto.getDescription(),
                eventDto.getParticipantLimit(),
                eventDto.isRequestModeration(),
                null, //state
                eventDto.getLocation().getLongitude(),
                eventDto.getLocation().getLatitude(),
                null);
    }

    public static EventPublicDto eventToEventPublicDto(Event event){
        return new EventPublicDto(event.getAnnotation(),
                CategoryMapper.categoryToCategoryFullDto(event.getCategory()),
                event.getConfirmedRequests(),
                event.getDate(),
                event.getId(),
                UserMapper.userToUserDto(event.getInitiatior()),
                event.isPaid(),
                event.getTitle(),
                event.getViews());
    }

    public static EventFullDto eventToEventFullDto(Event event){
        return new EventFullDto(event.getAnnotation(),
                CategoryMapper.categoryToCategoryFullDto(event.getCategory()),
                event.getConfirmedRequests(),
                event.getCreated(),
                event.getDescription(),
                event.getDate(),
                event.getId(),
                UserMapper.userToUserDto(event.getInitiatior()),
                new LocationDto(event.getLatitude(), event.getLongitude()),
                event.isPaid(),
                event.getParticipantLimit(),
                event.getPublishedOn(),
                event.isRequestModeration(),
                event.getEventState(),
                event.getTitle(),
                event.getViews());
    }
}
