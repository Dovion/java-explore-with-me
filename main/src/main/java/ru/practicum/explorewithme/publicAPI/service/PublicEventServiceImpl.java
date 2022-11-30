package ru.practicum.explorewithme.publicAPI.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.explorewithme.client.StatisticClient;
import ru.practicum.explorewithme.dto.StatisticDto;
import ru.practicum.explorewithme.event.dto.EventFullDto;
import ru.practicum.explorewithme.event.mapper.EventMapper;
import ru.practicum.explorewithme.event.model.Event;
import ru.practicum.explorewithme.event.model.EventState;
import ru.practicum.explorewithme.event.repository.EventRepository;
import ru.practicum.explorewithme.exception.EntityNotFoundException;
import ru.practicum.explorewithme.publicAPI.EventSortState;

import javax.servlet.http.HttpServletRequest;
import java.net.URISyntaxException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class PublicEventServiceImpl implements PublicEventService {

    private final EventRepository eventRepository;
    private final StatisticClient statisticClient;

    @Override
    public List<EventFullDto> getAllEvents(String text, List categories, Boolean paid, String rangeStart, String rangeEnd, Boolean onlyAvailable, String sortString, Integer from, Integer size, HttpServletRequest request) {
        EventSortState eventSortState;
        Sort sort;
        try {
            eventSortState = EventSortState.valueOf(sortString);
        } catch (IllegalArgumentException exception) {
            throw new IllegalArgumentException("Can`t get all events: sort state'" + sortString + "' not found");
        }
        if (eventSortState == EventSortState.EVENT_DATE) {
            sort = Sort.sort(Event.class).by(Event::getDate);
        } else if (eventSortState == EventSortState.VIEWS) {
            sort = Sort.sort(Event.class).by(Event::getViews);
        } else {
            throw new IllegalArgumentException("Can`t get all events: sort state'" + sortString + "' not found");
        }
        Pageable pageable = PageRequest.of(from / size, size, sort);
        LocalDateTime startDate = LocalDateTime.now();
        if (rangeStart != null) {
            startDate = LocalDateTime.parse(rangeStart, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        }
        LocalDateTime endDate = LocalDateTime.now();
        if (rangeStart != null) {
            endDate = LocalDateTime.parse(rangeEnd, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        }
        List<Event> eventList = eventRepository.getAllByTextAndCategoriesAndPaidAndDatesAndOnlyAvailable(text, categories, paid, startDate, endDate, onlyAvailable, pageable);
        List<EventFullDto> eventFullDtoList = new ArrayList<>();
        for (var event : eventList) {
            eventRepository.addViewByEventId(event.getId());
            event.setViews(event.getViews() + 1);
            eventFullDtoList.add(EventMapper.eventToEventFullDto(event));
        }
        Thread thread = new Thread(
                () -> {
                    try {
                        statisticClient.save(new StatisticDto(
                                null,
                                "PUBLIC SERVICE",
                                request.getServletPath(),
                                request.getRemoteAddr(),
                                LocalDateTime.now()
                        ));
                    } catch (Exception e) {
                        log.warn("Statistic`s request is failed:{}", e.getMessage());
                    }
                });
        thread.start();
        log.info("Getting success");
        return eventFullDtoList;
    }

    @Override
    public EventFullDto getEventById(Long id, HttpServletRequest request) throws EntityNotFoundException, URISyntaxException {
        Event event = eventRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Cant get event: event not found"));
        if (event.getEventState() != EventState.PUBLISHED) {
            throw new EntityNotFoundException("Cant get event: event is not published");
        }
        eventRepository.addViewByEventId(id);
        event.setViews(event.getViews() + 1);

        Thread thread = new Thread(
                () -> {
                    try {
                        statisticClient.save(new StatisticDto(
                                null,
                                "PUBLIC SERVICE",
                                request.getServletPath(),
                                request.getRemoteAddr(),
                                LocalDateTime.now()
                        ));
                    } catch (Exception e) {
                        log.warn("Statistic`s request is failed:{}", e.getMessage());
                    }
                });
        thread.start();
        log.info("Getting success");
        return EventMapper.eventToEventFullDto(event);
    }
}
