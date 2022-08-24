package com.blogapi.serviceImpl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.blogapi.entities.Comment;
import com.blogapi.entities.Post;
import com.blogapi.exception.ResourceNotFoundException;
import com.blogapi.payloads.CommentDto;
import com.blogapi.repository.CommentRepository;
import com.blogapi.repository.PostRepository;
import com.blogapi.services.CommentService;

@Service
public class CommentServiceImpl implements CommentService{
	
	@Autowired
	private PostRepository postRepository;
	
	@Autowired
	private CommentRepository commentRepository;
	
	@Autowired
	private ModelMapper modelMapper;

	@Override
	public CommentDto createComments(CommentDto commentDto, Integer postId) throws ResourceNotFoundException {
		
		Post post = this.postRepository.findById(postId).orElseThrow(()->new ResourceNotFoundException("Post", "post id", postId));
		Comment comment = this.modelMapper.map(commentDto, Comment.class);
		comment.setPost(post);
		Comment savedComment = this.commentRepository.save(comment);
	return this.modelMapper.map(savedComment, CommentDto.class);
	}

	@Override
	public void deleteComments(Integer commentId) throws ResourceNotFoundException {
		
		Comment comment = this.commentRepository.findById(commentId).orElseThrow(()->new ResourceNotFoundException("Comment", "comment id", commentId));
		this.commentRepository.delete(comment);
		
		
	}

}
