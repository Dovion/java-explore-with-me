package ru.practicum.explorewithme.publicAPI.service;

import ru.practicum.explorewithme.category.dto.CategoryFullDto;
import ru.practicum.explorewithme.exception.EntityNotFoundException;

import java.util.List;

public interface PublicCategoryService {

    List<CategoryFullDto> getAllCategories(Integer from, Integer size);

    CategoryFullDto getCategoryById(Long id) throws EntityNotFoundException;
}
