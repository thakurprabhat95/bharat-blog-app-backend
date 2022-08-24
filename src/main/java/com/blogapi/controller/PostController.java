package com.blogapi.controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.blogapi.config.AppConstant;
import com.blogapi.exception.ResourceNotFoundException;
import com.blogapi.payloads.ApiResponse;
import com.blogapi.payloads.PostDto;
import com.blogapi.payloads.PostResponse;
import com.blogapi.services.FileService;
import com.blogapi.services.PostService;

/**
 * @author prabhat.thakur,rahul,jatin Mangla
 *
 */
@RestController
@RequestMapping("/api/v1/posts")
public class PostController {

	@Autowired
	private PostService postService;

	@Autowired
	private FileService fileService;

	@Value("${project.image}")
	private String path;

	// create
	@PostMapping("/user/{id}/category/{categoryId}")
	public ResponseEntity<PostDto> createPost(@RequestBody PostDto postDto, @PathVariable Integer id,
			@PathVariable Integer categoryId) throws ResourceNotFoundException {
		PostDto createPost = this.postService.createPost(postDto, id, categoryId);
		return new ResponseEntity<PostDto>(createPost, HttpStatus.CREATED);
	}

	// get post by userId
	@GetMapping("/getpostByUserId/{userId}")
	public ResponseEntity<List<PostDto>> getPostByUserId(@PathVariable("userId") Integer id)
			throws ResourceNotFoundException {
		List<PostDto> postByUser = this.postService.getPostByUser(id);
		return ResponseEntity.ok(postByUser);
	}

	// get post by categoryId
	@GetMapping("/getcategory/{categoryId}")
	public ResponseEntity<List<PostDto>> getPostByCategoryId(@PathVariable Integer categoryId)
			throws ResourceNotFoundException {
		List<PostDto> postsByCategory = this.postService.getPostsByCategory(categoryId);
		return ResponseEntity.ok(postsByCategory);
	}

	// get all posts
	@GetMapping("/getAllPosts")
	public ResponseEntity<PostResponse> getAllPost(
			@RequestParam(value = "pageNumber", defaultValue = AppConstant.PAGE_NUMBER, required = false) Integer pageNumber,
			@RequestParam(value = "pageSize", defaultValue = AppConstant.PAGE_SIZE, required = false) Integer pageSize,
			@RequestParam(value = "sortBy", defaultValue = AppConstant.SORT_BY, required = false) String sortBy,
			@RequestParam(value = "sortDir", defaultValue = AppConstant.SORT_DIR, required = false) String sortDir) {
		PostResponse allPost = this.postService.getAllPost(pageNumber, pageSize, sortBy, sortDir);
		return ResponseEntity.ok(allPost);
	}

	// get post by postId
	@GetMapping("/getpostbypostId/{postId}")
	public ResponseEntity<PostDto> getPostByPostId(@PathVariable Integer postId) throws ResourceNotFoundException {
		PostDto postById = this.postService.getPostById(postId);
		return new ResponseEntity<PostDto>(postById, HttpStatus.OK);
	}

	// delete post by id
	//ADMIN
	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("/deletepost/{postId}")
	public ResponseEntity<ApiResponse> deletePostById(@PathVariable Integer postId) throws ResourceNotFoundException {
		this.postService.deletePost(postId);
		return new ResponseEntity<ApiResponse>(new ApiResponse("post deleted successfully", true), HttpStatus.OK);
	}

	// update post by id
	@PutMapping("/updatepost/{postId}")
	public ResponseEntity<PostDto> updatePost(@RequestBody PostDto postDto, @PathVariable Integer postId)
			throws ResourceNotFoundException {
		PostDto updatedPost = this.postService.updatePost(postDto, postId);
		return new ResponseEntity<PostDto>(updatedPost, HttpStatus.ACCEPTED);
	}

	// search with any keyword
	@GetMapping("/searchpost")
	public ResponseEntity<List<PostDto>> searchPostByAnyKeyword(
			@RequestParam(value = "title", required = true) String title) {
		List<PostDto> searchPosts = this.postService.searchPosts(title);
		return new ResponseEntity<List<PostDto>>(searchPosts, HttpStatus.OK);
	}

	// upload image into post
	@PostMapping("/post/image/uploadImage/{postId}")
	public ResponseEntity<PostDto> uploadImage(@RequestParam("image") MultipartFile image,
			@PathVariable("postId") Integer postId) throws IOException, ResourceNotFoundException {

		// get specific postid
		PostDto postDto = this.postService.getPostById(postId);
		// upload image
		String filename = this.fileService.uploadImage(path, image);

		// set imageName into postDto
		postDto.setImageName(filename);

		// update all data in database
		PostDto updatedPost = this.postService.updatePost(postDto, postId);
		return new ResponseEntity<PostDto>(updatedPost, HttpStatus.OK);

	}

	// api for serve images
	@GetMapping(value = "post/profiles/{imageName}", produces = MediaType.IMAGE_JPEG_VALUE)
	public void downloadImage(@PathVariable("imageName") String imageName, HttpServletResponse response)
			throws IOException {
		InputStream downloadImage = this.fileService.downloadImage(path, imageName);
		response.setContentType(MediaType.IMAGE_JPEG_VALUE);
		StreamUtils.copy(downloadImage, response.getOutputStream());

	}

}
