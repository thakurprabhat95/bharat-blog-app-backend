package com.blogapi.serviceImpl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.blogapi.entities.Category;
import com.blogapi.exception.ResourceNotFoundException;
import com.blogapi.payloads.CategoryDto;
import com.blogapi.repository.CategoryRepository;
import com.blogapi.services.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService {

	@Autowired
	private CategoryRepository categoryRepository;

	@Autowired
	private ModelMapper modelMapper;

	//business logic for create category
	@Override
	public CategoryDto createCategory(CategoryDto categoryDto) {
		Category category=this.dtoToCategory(categoryDto);
		Category createdCategory = this.categoryRepository.save(category);
		return this.categoryToDto(createdCategory);
	}

	
	//business logic for update category
	@Override
	public CategoryDto updateCategory(CategoryDto categoryDto, Integer categoryId) throws ResourceNotFoundException {
		
		Category category=this.categoryRepository.findById(categoryId).orElseThrow(()->new ResourceNotFoundException("Category", "CategoryId", categoryId));
		
		category.setCategoryTitle(categoryDto.getCategoryTitle());
		category.setCategoryDescription(categoryDto.getCategoryDescription());
		
		Category updatedCategory = this.categoryRepository.save(category);
		return this.categoryToDto(updatedCategory);
	}

	
	//business logic for delete category
	@Override
	public void deleteCategory(Integer categoryId) throws ResourceNotFoundException {
		Category category=this.categoryRepository.findById(categoryId).orElseThrow(()->new ResourceNotFoundException("Category", "CategoryId", categoryId));
		this.categoryRepository.delete(category);

	}

	@Override
	public CategoryDto getCategory(Integer categoryId) throws ResourceNotFoundException {
		Category category=this.categoryRepository.findById(categoryId).orElseThrow(()->new ResourceNotFoundException("Category", "CategoryId", categoryId));
		
		return this.categoryToDto(category);
	}

	@Override
	public List<CategoryDto> getAllCategory() {
		List<Category> findAllCategoryList = this.categoryRepository.findAll();
		List<CategoryDto> findCategoryListDto= findAllCategoryList.stream().map(findAllCategory->this.modelMapper.map(findAllCategory, CategoryDto.class)).collect(Collectors.toList());
		return findCategoryListDto;
	}

	public Category dtoToCategory(CategoryDto categoryDto) {
		return this.modelMapper.map(categoryDto, Category.class);

	}

	public CategoryDto categoryToDto(Category category) {
		return this.modelMapper.map(category, CategoryDto.class);
	}

}
