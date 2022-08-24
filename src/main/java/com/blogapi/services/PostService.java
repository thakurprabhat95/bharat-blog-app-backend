package com.blogapi.services;

import java.util.List;

import com.blogapi.exception.ResourceNotFoundException;
import com.blogapi.payloads.PostDto;
import com.blogapi.payloads.PostResponse;

public interface PostService {

	// create
	PostDto createPost(PostDto postDto, Integer id, Integer categoryId) throws ResourceNotFoundException;

	// update
	PostDto updatePost(PostDto postDto, Integer postId) throws ResourceNotFoundException;

	// delete
	void deletePost(Integer postId) throws ResourceNotFoundException;

	// getAll post
	// List<PostDto> getAllPost(Integer pageNumber,Integer pageSize);
	// get all information about pagenumber,pageSize etc, we have to pass
	// PostResponse as parameter
	PostResponse getAllPost(Integer pageNumber, Integer pageSize, String sortBy, String sortDir);

	// get single post

	PostDto getPostById(Integer postId) throws ResourceNotFoundException;

	// get all posts by category
	List<PostDto> getPostsByCategory(Integer categoryId) throws ResourceNotFoundException;

	// get all posts by user

	List<PostDto> getPostByUser(Integer id) throws ResourceNotFoundException;

	// get all search posts
	List<PostDto> searchPosts(String keyword);
	
	

}
