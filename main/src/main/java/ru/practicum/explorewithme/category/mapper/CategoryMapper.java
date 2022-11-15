package ru.practicum.explorewithme.category.mapper;

import ru.practicum.explorewithme.category.dto.CategoryDto;
import ru.practicum.explorewithme.category.dto.CategoryFullDto;
import ru.practicum.explorewithme.category.model.Category;

public class CategoryMapper {

    public static Category categoryDtoToCategory(CategoryDto categoryDto){
        return new Category(null,
                categoryDto.getName());
    }

    public static CategoryFullDto categoryToCategoryFullDto(Category category){
        return new CategoryFullDto(category.getId(),
                category.getName());
    }

    public static Category categoryFullDtoToCategory(CategoryFullDto categoryFullDto){
        return new Category(categoryFullDto.getId(),
                categoryFullDto.getName());
    }
}
