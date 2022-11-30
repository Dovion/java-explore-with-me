package ru.practicum.explorewithme.adminAPI.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.explorewithme.compilation.dto.CompilationDto;
import ru.practicum.explorewithme.compilation.dto.CompilationFullDto;
import ru.practicum.explorewithme.compilation.mapper.CompilationMapper;
import ru.practicum.explorewithme.compilation.model.Compilation;
import ru.practicum.explorewithme.compilation.repository.CompilationRepository;
import ru.practicum.explorewithme.event.model.Event;
import ru.practicum.explorewithme.event.repository.EventRepository;
import ru.practicum.explorewithme.exception.EntityNotFoundException;

import java.util.ConcurrentModificationException;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class AdminCompilationServiceImpl implements AdminCompilationService {

    private final EventRepository eventRepository;
    private final CompilationRepository compilationRepository;

    @Override
    public CompilationFullDto addCompilation(CompilationDto compilationDto) {
        Compilation compilation = CompilationMapper.compilationDtoToCompilation(compilationDto, eventRepository
                .findAllById(compilationDto.getEvents()));
        compilationRepository.saveAndFlush(compilation);
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
                    if (Objects.equals(event.getId(), eventId)) {
                        compilation.getCompilationEvents().remove(event);
                        event.getEventCompilations().remove(compilation);
                        eventRepository.saveAndFlush(event);
                    }
                }
            } catch (ConcurrentModificationException e) {
                compilationRepository.saveAndFlush(compilation);
            }
        }
        log.info("Saving success");
        compilationRepository.saveAndFlush(compilation);
    }

    @Override
    public void addEventInCompilation(Long compId, Long eventId) throws EntityNotFoundException {
        Compilation compilation = compilationRepository.findById(compId).orElseThrow(() -> new EntityNotFoundException("Can`t add event in compilation: compilation not found"));
        Event event = eventRepository.findById(eventId).orElseThrow(() -> new EntityNotFoundException("Can`t add event in compilation: event not found"));
        compilation.getCompilationEvents().add(event);
        event.getEventCompilations().add(compilation);
        eventRepository.saveAndFlush(event);
        compilationRepository.saveAndFlush(compilation);
        log.info("Adding success");
    }

    @Override
    public void unpinCompilation(Long id) throws EntityNotFoundException {
        Compilation compilation = compilationRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Can`t unpin compilation: compilation not found"));
        compilation.setPinned(false);
        compilationRepository.saveAndFlush(compilation);
        log.info("Adding success");
    }

    @Override
    public void pinCompilation(Long id) throws EntityNotFoundException {
        Compilation compilation = compilationRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Can`t pin compilation: compilation not found"));
        compilation.setPinned(true);
        compilationRepository.saveAndFlush(compilation);
        log.info("Saving success");
    }
}
