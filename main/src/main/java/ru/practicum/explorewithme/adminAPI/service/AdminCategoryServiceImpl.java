package ru.practicum.explorewithme.adminAPI.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.explorewithme.category.dto.CategoryDto;
import ru.practicum.explorewithme.category.dto.CategoryFullDto;
import ru.practicum.explorewithme.category.mapper.CategoryMapper;
import ru.practicum.explorewithme.category.model.Category;
import ru.practicum.explorewithme.category.repository.CategoryRepository;
import ru.practicum.explorewithme.exception.ConflictException;
import ru.practicum.explorewithme.exception.EntityNotFoundException;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class AdminCategoryServiceImpl implements AdminCategoryService {

    private final CategoryRepository categoryRepository;

    @Override
    @Transactional(rollbackFor = ConflictException.class)
    public CategoryFullDto updateCategory(CategoryFullDto categoryFullDto) throws EntityNotFoundException, ConflictException {
        Category category = categoryRepository.findById(categoryFullDto.getId()).orElseThrow(() -> new EntityNotFoundException("Can`t update category: category not found"));
        Category newCategory = CategoryMapper.categoryFullDtoToCategory(categoryFullDto);
        try {
            categoryRepository.saveAndFlush(newCategory);
        } catch (DataIntegrityViolationException e) {
            throw new ConflictException("Can`t update category: category`s name already exist");
        }
        log.info("Update success");
        return CategoryMapper.categoryToCategoryFullDto(newCategory);
    }

    @Override
    @Transactional(rollbackFor = ConflictException.class)
    public CategoryFullDto addCategory(CategoryDto categoryDto) throws ConflictException {
        Category category = CategoryMapper.categoryDtoToCategory(categoryDto);
        try {
            categoryRepository.saveAndFlush(category);
        } catch (DataIntegrityViolationException e) {
            throw new ConflictException("Can`t add category: category`s name already exist");
        }
        log.info("Adding success");
        return CategoryMapper.categoryToCategoryFullDto(category);
    }

    @Override
    public void removeCategoryById(Long id) throws EntityNotFoundException {
        Category category = categoryRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Can`t remove category: category not found"));
        categoryRepository.deleteById(id);
        log.info("Remove success");
    }
}
