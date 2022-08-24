package com.blogapi.serviceImpl;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.blogapi.config.AppConstant;
import com.blogapi.entities.Category;
import com.blogapi.entities.Post;
import com.blogapi.entities.User;
import com.blogapi.exception.ResourceNotFoundException;
import com.blogapi.payloads.PostDto;
import com.blogapi.payloads.PostResponse;
import com.blogapi.repository.CategoryRepository;
import com.blogapi.repository.PostRepository;
import com.blogapi.repository.UserRepository;
import com.blogapi.services.PostService;

@Service
public class PostServiceImpl implements PostService {

	@Autowired
	private PostRepository postRepository;

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private CategoryRepository categoryRepository;

	@Override
	public PostDto createPost(PostDto postDto, Integer id, Integer categoryId) throws ResourceNotFoundException {
		Post post = this.dtoToPost(postDto);
		post.setImageName("default.png");
		post.setAddedDate(new Date());

		User userId = this.userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(AppConstant.USER_ID, AppConstant.USER_ID, id));
		Category categoryById = this.categoryRepository.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException(AppConstant.CATEGORY_ID, AppConstant.CATEGORY_ID, categoryId));
		post.setUser(userId);
		post.setCategory(categoryById);

		Post newPost = this.postRepository.save(post);

		return this.modelMapper.map(newPost, PostDto.class);

	}

	// updtae business logic
	@Override
	public PostDto updatePost(PostDto postDto, Integer postId) throws ResourceNotFoundException {

		Post getpost = this.postRepository.findById(postId)
				.orElseThrow(() -> new ResourceNotFoundException(AppConstant.POST_ID, AppConstant.POST_ID, postId));
		getpost.setTitle(postDto.getTitle());
		getpost.setContent(postDto.getContent());
		getpost.setImageName(postDto.getImageName());

		Post savedPost = this.postRepository.save(getpost);
		return this.modelMapper.map(savedPost, PostDto.class);
	}

	@Override
	public void deletePost(Integer postId) throws ResourceNotFoundException {
		Post getpost = this.postRepository.findById(postId)
				.orElseThrow(() -> new ResourceNotFoundException(AppConstant.POST_ID, AppConstant.POST_ID, postId));
		this.postRepository.delete(getpost);

	}

	// business logic for get post by postId

	@Override
	public PostDto getPostById(Integer postId) throws ResourceNotFoundException {

		Post PostById = this.postRepository.findById(postId)
				.orElseThrow(() -> new ResourceNotFoundException(AppConstant.POST_ID, AppConstant.POST_ID, postId));
		return this.modelMapper.map(PostById, PostDto.class);
	}

	@Override
	public List<PostDto> getPostsByCategory(Integer categoryId) throws ResourceNotFoundException {
		Category category = this.categoryRepository.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException(AppConstant.CATEGORY_ID, AppConstant.CATEGORY_ID, categoryId));

		List<Post> posts = this.postRepository.findByCategory(category);
		List<PostDto> postDtos = posts.stream().map((post) -> this.modelMapper.map(post, PostDto.class))
				.collect(Collectors.toList());
		return postDtos;
	}

	@Override
	public List<PostDto> getPostByUser(Integer id) throws ResourceNotFoundException {
		User user = this.userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(AppConstant.USER_ID,AppConstant.USER_ID, id));
		List<Post> posts = this.postRepository.findByUser(user);
		List<PostDto> ListOfPostInDto = posts.stream().map((post) -> this.modelMapper.map(post, PostDto.class))
				.collect(Collectors.toList());

		return ListOfPostInDto;
	}

	@Override
	public List<PostDto> searchPosts(String keyword) {
		List<Post> searchPostByTitle = this.postRepository.findByTitleContaining(AppConstant.LIKE_OPERATOR + keyword + AppConstant.LIKE_OPERATOR);
		List<PostDto> searchPostByTitleDtos = searchPostByTitle.stream()
				.map((searchByTitle) -> this.modelMapper.map(searchByTitle, PostDto.class))
				.collect(Collectors.toList());
		return searchPostByTitleDtos;
	}

	public Post dtoToPost(PostDto postDto) {
		return this.modelMapper.map(postDto, Post.class);
	}

	public PostDto PostToDto(Post post) {
		return this.modelMapper.map(post, PostDto.class);
	}

	@Override
	public PostResponse getAllPost(Integer pageNumber, Integer pageSize, String sortBy, String sortDir) {

		// ternary operator to check sorting direction

		Sort sort = (sortDir.equalsIgnoreCase("asc")) ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();

//		if(sortDir.equalsIgnoreCase("asc"))
//		{
//			sort=Sort.by(sortBy).ascending();
//		}
//		else
//		{
//			sort=Sort.by(sortBy).descending();
//		}

		// for paging and sorting
		Pageable paging = PageRequest.of(pageNumber, pageSize, sort);
		Page<Post> pagePost = this.postRepository.findAll(paging);

		// change Page into List
		List<Post> findAllpost = pagePost.getContent();

		// reterive all value from post into postDto

		List<PostDto> postDtos = findAllpost.stream().map((getPost) -> this.modelMapper.map(getPost, PostDto.class))
				.collect(Collectors.toList());

		// set all value in postResponse
		PostResponse postResponse = new PostResponse();
		postResponse.setContent(postDtos);
		postResponse.setPageNumber(pagePost.getNumber());
		postResponse.setPageSize(pagePost.getSize());
		postResponse.setTotalElements(pagePost.getTotalElements());
		postResponse.setTotalPages(pagePost.getTotalPages());
		postResponse.setLastPage(pagePost.isLast());

		return postResponse;

	}

}
