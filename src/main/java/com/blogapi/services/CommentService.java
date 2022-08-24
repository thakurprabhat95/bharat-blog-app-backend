package com.blogapi.services;

import com.blogapi.exception.ResourceNotFoundException;
import com.blogapi.payloads.CommentDto;

public interface CommentService {
	
	CommentDto createComments(CommentDto commentDto, Integer postId) throws ResourceNotFoundException;
	void deleteComments(Integer commentId) throws ResourceNotFoundException;

}
