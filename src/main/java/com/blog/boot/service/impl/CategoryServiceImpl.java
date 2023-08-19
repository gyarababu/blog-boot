package com.blog.boot.service.impl;

import com.blog.boot.entity.Category;
import com.blog.boot.exception.ResourceNotFoundException;
import com.blog.boot.payload.CategoryDto;
import com.blog.boot.repository.CategoryRepository;
import com.blog.boot.service.CategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class CategoryServiceImpl implements CategoryService {

    private CategoryRepository categoryRepository;
    private ModelMapper modelMapper;

    public CategoryServiceImpl(CategoryRepository categoryRepository, ModelMapper modelMapper) {
        this.categoryRepository = categoryRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public CategoryDto addCategory(CategoryDto categoryDto) {

        // covert dto to entity
        Category category = modelMapper.map(categoryDto, Category.class);
        Category newCategory = categoryRepository.save(category);

        // covert entity to dto
        return modelMapper.map(newCategory, CategoryDto.class);
    }

    @Override
    public CategoryDto getCategory(long categoryId) {

        // find category else exception
        Category category = categoryRepository.findById(categoryId).orElseThrow(() ->
                new ResourceNotFoundException("Category", "id", categoryId));

        // covert entity to dto
        return modelMapper.map(category, CategoryDto.class);
    }
}
