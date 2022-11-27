package ru.practicum.explorewithme.publicAPI;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.explorewithme.category.dto.CategoryFullDto;
import ru.practicum.explorewithme.compilation.dto.CompilationFullDto;
import ru.practicum.explorewithme.event.dto.EventFullDto;
import ru.practicum.explorewithme.exception.EntityNotFoundException;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.net.URISyntaxException;
import java.util.List;

@RestController
@Validated
@Slf4j
@RequiredArgsConstructor
@RequestMapping
public class PublicController {

    private final PublicService publicService;

    @GetMapping("/events")
    public List<EventFullDto> getAllEvents(@RequestParam(defaultValue = "") String text,
                                           @RequestParam(required = false) Long[] categories,
                                           @RequestParam(required = false) Boolean paid,
                                           @RequestParam(required = false) String rangeStart,
                                           @RequestParam(required = false) String rangeEnd,
                                           @RequestParam(required = false) Boolean onlyAvailable,
                                           @RequestParam(required = false, defaultValue = "EVENT_DATE") String sort,
                                           @RequestParam(defaultValue = "0") @PositiveOrZero Integer from,
                                           @RequestParam(defaultValue = "10") @Positive Integer size,
                                           HttpServletRequest request) {
        log.info("Getting all events by public request");
        return publicService.getAllEvents(text, categories, paid, rangeStart, rangeEnd, onlyAvailable, sort, from, size, request);
    }

    @GetMapping("/events/{id}")
    public EventFullDto getEvent(@PathVariable @Positive long id, HttpServletRequest request) throws EntityNotFoundException, URISyntaxException {
        log.info("Getting event by public request");
        return publicService.getEventById(id, request);
    }

    @GetMapping("/compilations")
    public List<CompilationFullDto> getAllCompilations(@RequestParam(required = false) Boolean pinned,
                                                       @RequestParam(defaultValue = "0") @PositiveOrZero Integer from,
                                                       @RequestParam(defaultValue = "10") @Positive Integer size) {
        log.info("Getting all compialtions by public request");
        return publicService.getAllCompilations(pinned, from, size);
    }

    @GetMapping("/compilations/{compId}")
    public CompilationFullDto getCompilation(@PathVariable @Positive long compId) throws EntityNotFoundException {
        log.info("Getting compilation by public request");
        return publicService.getCompilationById(compId);
    }

    @GetMapping("/categories")
    public List<CategoryFullDto> getAllCategories(@RequestParam(defaultValue = "0") @PositiveOrZero Integer from,
                                                  @RequestParam(defaultValue = "10") @Positive Integer size) {
        log.info("Getting all categories by public request");
        return publicService.getAllCategories(from, size);
    }

    @GetMapping("/categories/{catId}")
    public CategoryFullDto getCategory(@PathVariable @Positive long catId) throws EntityNotFoundException {
        log.info("Getting category by public request");
        return publicService.getCategoryById(catId);
    }


}
