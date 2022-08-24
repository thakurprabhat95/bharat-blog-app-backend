package com.blogapi.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blogapi.exception.ResourceNotFoundException;
import com.blogapi.payloads.ApiResponse;
import com.blogapi.payloads.CategoryDto;
import com.blogapi.services.CategoryService;

/**
 * @author rahul, prabhat,Jatin
 *
 */
@RestController
@RequestMapping("/api/v1/category")
public class CategoryController {

	@Autowired
	private CategoryService categoryService;

	// create api
	@PostMapping("/createcategory")
	public ResponseEntity<CategoryDto> createCategory(@Valid @RequestBody CategoryDto categoryDto) {
		CategoryDto createCategory = this.categoryService.createCategory(categoryDto);
		return new ResponseEntity<CategoryDto>(createCategory, HttpStatus.CREATED);

	}

	// update api
	@PutMapping("/updatecategory/{categoryId}")
	public ResponseEntity<CategoryDto> updateCategory(@Valid @RequestBody CategoryDto categoryDto,
			@PathVariable Integer categoryId) throws ResourceNotFoundException {
		CategoryDto updatedCategory = this.categoryService.updateCategory(categoryDto, categoryId);
		return new ResponseEntity<CategoryDto>(updatedCategory, HttpStatus.OK);

	}

	// delete api
	@DeleteMapping("/deletecategory/{categoryId}")
	public ResponseEntity<ApiResponse> deleteCategory(@PathVariable Integer categoryId)
			throws ResourceNotFoundException {
		this.categoryService.deleteCategory(categoryId);
		return new ResponseEntity<ApiResponse>(new ApiResponse("Category delete successfully", false), HttpStatus.OK);
	}

	// get specific id

	@GetMapping("/getcategoryById/{categoryId}")
	public ResponseEntity<CategoryDto> getCategoryById(@PathVariable Integer categoryId)
			throws ResourceNotFoundException {
		CategoryDto getCategoryById = this.categoryService.getCategory(categoryId);
		return new ResponseEntity<CategoryDto>(getCategoryById, HttpStatus.OK);
	}

	// get all category

	@GetMapping("/getAllCategory")
	public ResponseEntity<List<CategoryDto>> getAllCategory() {
		List<CategoryDto> getAllCategory = this.categoryService.getAllCategory();
		// return new ResponseEntity<List<CategoryDto>>(getAllCategory,HttpStatus.OK);
		return ResponseEntity.ok(getAllCategory);
	}

}
