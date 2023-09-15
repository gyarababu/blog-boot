package com.blog.boot.service.impl;

import com.blog.boot.entity.Category;
import com.blog.boot.exception.ResourceNotFoundException;
import com.blog.boot.payload.CategoryDto;
import com.blog.boot.repository.CategoryRepository;
import com.blog.boot.service.CategoryService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {

    Logger logger = LoggerFactory.getLogger(CategoryServiceImpl.class);

    private CategoryRepository categoryRepository;
    private ModelMapper modelMapper;

    public CategoryServiceImpl(CategoryRepository categoryRepository, ModelMapper modelMapper) {
        this.categoryRepository = categoryRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public CategoryDto addCategory(CategoryDto categoryDto) {
        logger.info("started add category service class for user info log level ");

        // covert dto to entity
        Category category = modelMapper.map(categoryDto, Category.class);
        Category newCategory = categoryRepository.save(category);

        logger.info("ended add category service class for user info log level ");

        // covert entity to dto
        return modelMapper.map(newCategory, CategoryDto.class);
    }

    @Override
    public CategoryDto getCategory(long categoryId) {
        logger.info("started get category service class for user info log level ");

        // find category else exception
        Category category = categoryRepository.findById(categoryId).orElseThrow(() ->
                new ResourceNotFoundException("Category", "id", categoryId));
        logger.info("ended get category service class for user info log level ");

        // covert entity to dto
        return modelMapper.map(category, CategoryDto.class);
    }

    @Override
    public List<CategoryDto> getAllCategories() {
        logger.info("started get all categories service class for user info log level ");

        // find all categories
        List<Category> categories = categoryRepository.findAll();
        logger.info("ended get all categories service class for user info log level ");

        // convert entity to dto
        return categories.stream().map((category) ->
                modelMapper.map(category, CategoryDto.class)).collect(Collectors.toList());
    }

    @Override
    public CategoryDto updateCategory(CategoryDto categoryDto, long categoryId) {
        logger.info("started update category service class for user info log level ");

        // find category else exception
        Category category = categoryRepository.findById(categoryId).orElseThrow(() ->
                new ResourceNotFoundException("Category", "id", categoryId));

        category.setId(categoryId);
        category.setName(categoryDto.getName());
        category.setDescription(categoryDto.getDescription());

        // save details
        Category updatedCategory = categoryRepository.save(category);
        logger.info("ended update category service class for user info log level ");

        // convert entity to dto
        return modelMapper.map(updatedCategory, CategoryDto.class);
    }

    @Override
    public void deleteCategory(long categoryId) {
        logger.info("started delete category service class for user info log level ");

        // find category else exception
        Category category = categoryRepository.findById(categoryId).orElseThrow(() ->
                new ResourceNotFoundException("Category", "id", categoryId));

        categoryRepository.delete(category);
        logger.info("ended delete category service class for user info log level ");

    }
}
