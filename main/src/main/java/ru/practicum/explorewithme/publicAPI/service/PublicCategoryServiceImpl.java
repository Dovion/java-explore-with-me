package ru.practicum.explorewithme.publicAPI.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.explorewithme.category.dto.CategoryFullDto;
import ru.practicum.explorewithme.category.mapper.CategoryMapper;
import ru.practicum.explorewithme.category.model.Category;
import ru.practicum.explorewithme.category.repository.CategoryRepository;
import ru.practicum.explorewithme.exception.EntityNotFoundException;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class PublicCategoryServiceImpl implements PublicCategoryService {

    private final CategoryRepository categoryRepository;

    @Override
    public List<CategoryFullDto> getAllCategories(Integer from, Integer size) {
        Pageable pageable = PageRequest.of(from / size, size);
        List<Category> categoryList = categoryRepository.findAll(pageable).toList();
        List<CategoryFullDto> categoryFullDtoList = new ArrayList<>();
        for (var category : categoryList) {
            categoryFullDtoList.add(CategoryMapper.categoryToCategoryFullDto(category));
        }
        log.info("Getting success");
        return categoryFullDtoList;
    }

    @Override
    public CategoryFullDto getCategoryById(Long id) throws EntityNotFoundException {
        Category category = categoryRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Can`t get category: category not found"));
        log.info("Getting success");
        return CategoryMapper.categoryToCategoryFullDto(category);
    }
}
