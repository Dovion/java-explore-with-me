package ru.practicum.explorewithme.publicAPI;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.explorewithme.category.dto.CategoryFullDto;
import ru.practicum.explorewithme.compilation.dto.CompilationFullDto;
import ru.practicum.explorewithme.event.dto.EventFullDto;

import java.util.List;

@RestController
@Validated
@Slf4j
@RequiredArgsConstructor
@RequestMapping
public class PublicController {

    private final PublicService publicService;
    @GetMapping("/events")
    public List<EventFullDto> getAllEvents(@RequestParam String text,
                                           @RequestParam Long[] categories,
                                           @RequestParam boolean paid,
                                           @RequestParam String rangeStart,
                                           @RequestParam String rangeEnd,
                                           @RequestParam boolean onlyAvailable,
                                           @RequestParam String sort,
                                           @RequestParam Integer size){
        return publicService.getAllEvents(text, categories, paid, rangeStart, rangeEnd, onlyAvailable, sort, size);
    }

    @GetMapping("/events/{id}")
    public EventFullDto getEvent(@PathVariable long id){
        return publicService.getEventById(id);
    }

    @GetMapping("/compilations")
    public List<CompilationFullDto> getAllCompilations(@RequestParam boolean pinned,
                                                       @RequestParam Integer from,
                                                       @RequestParam Integer size){
        return publicService.getAllCompilations(pinned, from, size);
    }

    @GetMapping("/compilations/{compId}")
    public CompilationFullDto getCompilation(@PathVariable long compId){
        return publicService.getCompilationById(compId);
    }

    @GetMapping("/categories")
    public List<CategoryFullDto> getAllCategories(@RequestParam(defaultValue = "0") Integer from,
                                                  @RequestParam(defaultValue = "10") Integer size){
        return publicService.getAllCategories(from,size);
    }

    @GetMapping("/categories/{catId}")
    public CategoryFullDto getCategory(@PathVariable long catId){
        return publicService.getCategoryById(catId);
    }



}
