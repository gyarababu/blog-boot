package com.blog.boot.service;

import com.blog.boot.payload.CategoryDto;

public interface CategoryService {

    CategoryDto addCategory(CategoryDto categoryDto);
    CategoryDto getCategory(long categoryId);
}
