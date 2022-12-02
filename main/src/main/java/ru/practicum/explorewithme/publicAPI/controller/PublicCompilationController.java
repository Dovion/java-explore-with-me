package ru.practicum.explorewithme.publicAPI.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.explorewithme.compilation.dto.CompilationFullDto;
import ru.practicum.explorewithme.exception.EntityNotFoundException;
import ru.practicum.explorewithme.publicAPI.service.PublicCompilationService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

@RestController
@Validated
@Slf4j
@RequiredArgsConstructor
@RequestMapping
public class PublicCompilationController {

    private final PublicCompilationService publicService;


    @GetMapping("/compilations")
    public List<CompilationFullDto> getAllCompilations(@RequestParam(required = false) Boolean pinned,
                                                       @RequestParam(defaultValue = "0") @PositiveOrZero Integer from,
                                                       @RequestParam(defaultValue = "10") @Positive Integer size,
                                                       HttpServletRequest request) {
        log.info("Getting all compialtions by public request: " + request.getServletPath());
        return publicService.getAllCompilations(pinned, from, size);
    }

    @GetMapping("/compilations/{compId}")
    public CompilationFullDto getCompilation(@PathVariable @Positive long compId, HttpServletRequest request) throws EntityNotFoundException {
        log.info("Getting compilation by public request: " + request.getServletPath());
        return publicService.getCompilationById(compId);
    }
}
