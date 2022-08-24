package com.blogapi.serviceImpl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.blogapi.config.AppConstant;
import com.blogapi.entities.Role;
import com.blogapi.entities.User;
import com.blogapi.exception.ResourceNotFoundException;
import com.blogapi.payloads.UserDto;
import com.blogapi.repository.RoleRepository;
import com.blogapi.repository.UserRepository;
import com.blogapi.services.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private RoleRepository roleRepository;

	@Override
	public UserDto createUser(UserDto userDto) {

		User user = this.dtoToUser(userDto);
		User savedUser = this.userRepository.save(user);
		return this.userToDto(savedUser);
	}

	@Override
	public UserDto updateUser(UserDto userDto, Integer userId) throws ResourceNotFoundException {
		User user = this.userRepository.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("user", "Id", userId));

		user.setName(userDto.getName());
		user.setEmail(userDto.getEmail());
		user.setPassword(userDto.getPassword());
		user.setAbout(userDto.getAbout());

		User updatedUser = this.userRepository.save(user);
		return this.userToDto(updatedUser);
	}

	@Override
	public UserDto getUserById(Integer userId) throws ResourceNotFoundException {
		User user = this.userRepository.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("user", "Id", userId));

		return this.userToDto(user);
	}

	@Override
	public List<UserDto> getAllUser() {
		List<User> findAllUsers = this.userRepository.findAll();

		List<UserDto> findAllUserDto = findAllUsers.stream().map(findAllUser -> this.userToDto(findAllUser))
				.collect(Collectors.toList());
		return findAllUserDto;
	}

	@Override
	public void deleteUser(Integer userId) throws ResourceNotFoundException {
		User user = this.userRepository.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("user", "Id", userId));

		this.userRepository.delete(user);

	}

	public User dtoToUser(UserDto userDto) {
//		User user = new User();
//
//		user.setId(userDto.getId());
//		user.setName(userDto.getName());
//		user.setEmail(userDto.getEmail());
//		user.setPassword(userDto.getPassword());
//		user.setAbout(userDto.getAbout());

		// here we use modelMapper for changing dto To user

		return this.modelMapper.map(userDto, User.class);
		// return user;
	}

	public UserDto userToDto(User user) {
//		UserDto userDto = new UserDto();
//
//		userDto.setId(user.getId());
//		userDto.setName(user.getName());
//		userDto.setEmail(user.getEmail());
//		userDto.setPassword(user.getPassword());
//		userDto.setAbout(user.getAbout());

		// here we use modelMapper for changing User To Dto
		return this.modelMapper.map(user, UserDto.class);

	}

	@Override
	public UserDto registerNewUser(UserDto userDto) {
		User user = this.modelMapper.map(userDto, User.class);
		// encode the password
		user.setPassword(this.passwordEncoder.encode(userDto.getPassword()));

		// set role
		Role role = this.roleRepository.findById(AppConstant.NORMAL_USER).get();

		user.getRole().add(role);

		// save user info
		this.userRepository.save(user);

		return this.modelMapper.map(user, UserDto.class);
	}
}
