package ru.practicum.explorewithme.publicAPI.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.explorewithme.compilation.dto.CompilationFullDto;
import ru.practicum.explorewithme.compilation.mapper.CompilationMapper;
import ru.practicum.explorewithme.compilation.model.Compilation;
import ru.practicum.explorewithme.compilation.repository.CompilationRepository;
import ru.practicum.explorewithme.exception.EntityNotFoundException;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class PublicCompilationServiceImpl implements PublicCompilationService {

    private final CompilationRepository compilationRepository;

    @Override
    public List<CompilationFullDto> getAllCompilations(Boolean pinned, Integer from, Integer size) {
        Pageable pageable = PageRequest.of(from / size, size);
        if (pinned != null) {
            List<CompilationFullDto> compilationFullDtoList = new ArrayList<>();
            List<Compilation> compilationList = compilationRepository.findAllByPinned(pinned, pageable);
            for (var compilation : compilationList) {
                compilationFullDtoList.add(CompilationMapper.compilationToCompilationFullDto(compilation));
            }
            return compilationFullDtoList;
        }
        List<CompilationFullDto> compilationFullDtoList = new ArrayList<>();
        List<Compilation> compilationList = compilationRepository.findAll(pageable).toList();
        for (var compilation : compilationList) {
            compilationFullDtoList.add(CompilationMapper.compilationToCompilationFullDto(compilation));
        }
        log.info("Getting success");
        return compilationFullDtoList;

    }

    @Override
    public CompilationFullDto getCompilationById(Long id) throws EntityNotFoundException {
        Compilation compilation = compilationRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Can`t get compilation: compilation not found"));
        log.info("Getting success");
        return CompilationMapper.compilationToCompilationFullDto(compilation);
    }
}
