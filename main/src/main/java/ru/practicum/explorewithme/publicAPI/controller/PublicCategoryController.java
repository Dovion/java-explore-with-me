package ru.practicum.explorewithme.publicAPI.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.explorewithme.category.dto.CategoryFullDto;
import ru.practicum.explorewithme.exception.EntityNotFoundException;
import ru.practicum.explorewithme.publicAPI.service.PublicCategoryService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

@RestController
@Validated
@Slf4j
@RequiredArgsConstructor
@RequestMapping
public class PublicCategoryController {

    private final PublicCategoryService publicService;

    @GetMapping("/categories")
    public List<CategoryFullDto> getAllCategories(@RequestParam(defaultValue = "0") @PositiveOrZero Integer from,
                                                  @RequestParam(defaultValue = "10") @Positive Integer size,
                                                  HttpServletRequest request) {
        log.info("Getting all categories by public request: " + request.getServletPath());
        return publicService.getAllCategories(from, size);
    }

    @GetMapping("/categories/{catId}")
    public CategoryFullDto getCategory(@PathVariable @Positive long catId, HttpServletRequest request) throws EntityNotFoundException {
        log.info("Getting category by public request: " + request.getServletPath());
        return publicService.getCategoryById(catId);
    }
}
