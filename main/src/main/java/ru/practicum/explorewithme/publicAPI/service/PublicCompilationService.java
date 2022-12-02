package ru.practicum.explorewithme.publicAPI.service;

import ru.practicum.explorewithme.compilation.dto.CompilationFullDto;
import ru.practicum.explorewithme.exception.EntityNotFoundException;

import java.util.List;

public interface PublicCompilationService {

    List<CompilationFullDto> getAllCompilations(Boolean pinned, Integer from, Integer size);

    CompilationFullDto getCompilationById(Long id) throws EntityNotFoundException;
}
