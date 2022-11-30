package ru.practicum.explorewithme.adminAPI.service;

import ru.practicum.explorewithme.category.dto.CategoryDto;
import ru.practicum.explorewithme.category.dto.CategoryFullDto;
import ru.practicum.explorewithme.exception.ConflictException;
import ru.practicum.explorewithme.exception.EntityNotFoundException;
import ru.practicum.explorewithme.exception.ValidationException;

public interface AdminCategoryService {

    CategoryFullDto updateCategory(CategoryFullDto categoryFullDto) throws EntityNotFoundException, ValidationException, ConflictException;

    CategoryFullDto addCategory(CategoryDto categoryDto) throws ValidationException, ConflictException;

    void removeCategoryById(Long id) throws EntityNotFoundException;
}
