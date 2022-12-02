package ru.practicum.explorewithme.adminAPI.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.explorewithme.adminAPI.service.AdminCategoryService;
import ru.practicum.explorewithme.category.dto.CategoryDto;
import ru.practicum.explorewithme.category.dto.CategoryFullDto;
import ru.practicum.explorewithme.exception.ConflictException;
import ru.practicum.explorewithme.exception.EntityNotFoundException;
import ru.practicum.explorewithme.exception.ValidationException;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.Positive;

@RestController
@Validated
@Slf4j
@RequiredArgsConstructor
@RequestMapping(path = "/admin")
public class AdminCategoryController {

    private final AdminCategoryService administratorService;

    @PatchMapping("/categories")
    public CategoryFullDto updateCategory(@RequestBody @Valid CategoryFullDto categoryFullDto, HttpServletRequest request) throws EntityNotFoundException, ValidationException, ConflictException {
        log.info("Updating category by administrator: " + request.getServletPath());
        return administratorService.updateCategory(categoryFullDto);
    }

    @PostMapping("/categories")
    public CategoryFullDto addCategory(@RequestBody @Valid CategoryDto categoryDto, HttpServletRequest request) throws ValidationException, ConflictException {
        log.info("Adding category by administrator: " + request.getServletPath());
        return administratorService.addCategory(categoryDto);
    }

    @DeleteMapping("/categories/{catId}")
    public void removeCategory(@PathVariable @Positive long catId, HttpServletRequest request) throws EntityNotFoundException {
        log.info("Removing category by administrator: " + request.getServletPath());
        administratorService.removeCategoryById(catId);
    }
}
