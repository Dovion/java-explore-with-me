package ru.practicum.explorewithme.adminAPI.service;

import ru.practicum.explorewithme.compilation.dto.CompilationDto;
import ru.practicum.explorewithme.compilation.dto.CompilationFullDto;
import ru.practicum.explorewithme.exception.EntityNotFoundException;

public interface AdminCompilationService {

    CompilationFullDto addCompilation(CompilationDto compilationDto);

    void removeCompilation(Long id) throws EntityNotFoundException;

    void removeEventFromCompilation(Long compId, Long eventId) throws EntityNotFoundException;

    void addEventInCompilation(Long compId, Long eventId) throws EntityNotFoundException;

    void unpinCompilation(Long id) throws EntityNotFoundException;

    void pinCompilation(Long id) throws EntityNotFoundException;
}
