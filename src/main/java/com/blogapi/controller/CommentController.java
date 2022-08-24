package com.blogapi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blogapi.exception.ResourceNotFoundException;
import com.blogapi.payloads.ApiResponse;
import com.blogapi.payloads.CommentDto;
import com.blogapi.services.CommentService;

@RestController
@RequestMapping("/api/v1/comment")
public class CommentController {
	
	@Autowired
	private CommentService commentService;
	
	@PostMapping("/posts/{postId}/comments")
	public ResponseEntity<CommentDto> createComment(@RequestBody CommentDto commentDto,@PathVariable("postId") Integer postId) throws ResourceNotFoundException
	{
		CommentDto createComments = this.commentService.createComments(commentDto, postId);
		return new ResponseEntity<CommentDto>(createComments,HttpStatus.CREATED);
	}
	
	@PostMapping("/comments/{commentId}")
	public ResponseEntity<ApiResponse> deleteComments(@PathVariable("commentId") Integer commentId) throws ResourceNotFoundException
	{
		this.commentService.deleteComments(commentId);
		return new ResponseEntity<ApiResponse>(new ApiResponse("comments are deleted successfully",true),HttpStatus.OK);
	}
	
	
	

}
