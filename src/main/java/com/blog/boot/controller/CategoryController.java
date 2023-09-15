package com.blog.boot.controller;

import com.blog.boot.payload.CategoryDto;
import com.blog.boot.service.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@Tag(
        name = "Category Resource CRUD REST APIs"
)
public class CategoryController {

    Logger logger = LoggerFactory.getLogger(CategoryController.class);

    private CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @Operation(
            summary = "Create Category REST API",
            description = "Saving category data into database"
    )
    @ApiResponse(
            responseCode = "201",
            description = "Http Status 201 CREATED"
    )
    // create add category REST API
    // gave authorization
    // creating authorization header to the REST API in swagger
    @SecurityRequirement(
            name = "Authentication Header"
    )
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/categories")
    public ResponseEntity<CategoryDto> addCategory(@RequestBody CategoryDto categoryDto){
        logger.info("started category post controller for user info log level ");

        CategoryDto newCategory = categoryService.addCategory(categoryDto);
        return new ResponseEntity<CategoryDto>(newCategory, HttpStatus.CREATED);
    }

    @Operation(
            summary = "Get Category By Id REST API",
            description = "Get single category data from database"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Http Status 200 SUCCESS"
    )
    // create get category REST API
    @GetMapping("/categories/{id}")
    public ResponseEntity<CategoryDto> getCategory(@PathVariable("id") long categoryId){
        logger.info("started get category by id controller for user info log level ");

        CategoryDto categoryDto = categoryService.getCategory(categoryId);
        return ResponseEntity.ok(categoryDto);
    }

    @Operation(
            summary = "Get All Categories REST API",
            description = "Get all categories data from database"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Http Status 200 SUCCESS"
    )
    // create get all categories REST API
    @GetMapping("/categories")
    public ResponseEntity<List<CategoryDto>> getAllCategories(){
        logger.info("started get all categories controller for user info log level ");

        return  ResponseEntity.ok(categoryService.getAllCategories());
    }

    @Operation(
            summary = "Update Category By Id REST API",
            description = "Update single category data from database"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Http Status 200 SUCCESS"
    )
    // create update category REST API
    // gave authorization
    // creating authorization header to the REST API in swagger
    @SecurityRequirement(
            name = "Authentication Header"
    )
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/categories/{id}")
    public ResponseEntity<CategoryDto> updateCategory(@RequestBody CategoryDto categoryDto,
                                                      @PathVariable("id") long categoryId){
        logger.info("started put category by id controller for user info log level ");

        return ResponseEntity.ok(categoryService.updateCategory(categoryDto, categoryId));
    }

    @Operation(
            summary = "Delete Category By Id REST API",
            description = "Delete single category data from database"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Http Status 200 SUCCESS"
    )
    // create delete REST API
    // gave authorization
    // creating authorization header to the REST API in swagger
    @SecurityRequirement(
            name = "Authentication Header"
    )
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/categories/{id}")
    public  ResponseEntity<String> deleteCategory(@PathVariable("id") long categoryId){
        logger.info("started delete category by id controller for user info log level ");

        categoryService.deleteCategory(categoryId);
        return ResponseEntity.ok("Category deleted successfully");
    }
}
