package com.blogapi.services;

import java.util.List;

import com.blogapi.exception.ResourceNotFoundException;
import com.blogapi.payloads.CategoryDto;

public interface CategoryService {

	// create category

	CategoryDto createCategory(CategoryDto categoryDto);

	// update category
	CategoryDto updateCategory(CategoryDto categoryDto, Integer categoryId) throws ResourceNotFoundException;

	// delete
	void deleteCategory(Integer categoryId) throws ResourceNotFoundException;

	// get single category
	CategoryDto getCategory(Integer categoryId) throws ResourceNotFoundException;

	// get all category
	List<CategoryDto> getAllCategory();
}
