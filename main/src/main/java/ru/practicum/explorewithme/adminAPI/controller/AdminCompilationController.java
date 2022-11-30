package ru.practicum.explorewithme.adminAPI.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.explorewithme.adminAPI.service.AdminCompilationService;
import ru.practicum.explorewithme.compilation.dto.CompilationDto;
import ru.practicum.explorewithme.compilation.dto.CompilationFullDto;
import ru.practicum.explorewithme.exception.EntityNotFoundException;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.Positive;

@RestController
@Validated
@Slf4j
@RequiredArgsConstructor
@RequestMapping(path = "/admin")
public class AdminCompilationController {

    private final AdminCompilationService administratorService;

    @PostMapping("/compilations")
    public CompilationFullDto addCompilation(@RequestBody @Valid CompilationDto compilationDto, HttpServletRequest request) {
        log.info("Adding compilation by administrator: " + request.getServletPath());
        return administratorService.addCompilation(compilationDto);
    }

    @DeleteMapping("/compilations/{compId}")
    public void removeCompilation(@PathVariable @Positive long compId, HttpServletRequest request) throws EntityNotFoundException {
        log.info("Removing compilation by administrator: " + request.getServletPath());
        administratorService.removeCompilation(compId);
    }

    @DeleteMapping("/compilations/{compId}/events/{eventId}")
    public void removeEventFromCompilation(@PathVariable @Positive long compId, @PathVariable long eventId, HttpServletRequest request) throws
            EntityNotFoundException {
        log.info("Removing event from compilation by administrator: " + request.getServletPath());
        administratorService.removeEventFromCompilation(compId, eventId);
    }

    @PatchMapping("/compilations/{compId}/events/{eventId}")
    public void addEventInCompilation(@PathVariable @Positive long compId, @PathVariable @Positive long eventId, HttpServletRequest request) throws
            EntityNotFoundException {
        log.info("Adding event into compilation by administrator: " + request.getServletPath());
        administratorService.addEventInCompilation(compId, eventId);
    }

    @DeleteMapping("/compilations/{compId}/pin")
    public void unpinCompilation(@PathVariable @Positive long compId, HttpServletRequest request) throws EntityNotFoundException {
        log.info("Unpinning compilation by administrator: " + request.getServletPath());
        administratorService.unpinCompilation(compId);
    }

    @PatchMapping("/compilations/{compId}/pin")
    public void pinCompilation(@PathVariable @Positive long compId, HttpServletRequest request) throws EntityNotFoundException {
        log.info("Pinning compilation by administrator: " + request.getServletPath());
        administratorService.pinCompilation(compId);
    }
}
