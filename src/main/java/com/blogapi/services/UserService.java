package com.blogapi.services;

import java.util.List;

import com.blogapi.exception.ResourceNotFoundException;
import com.blogapi.payloads.UserDto;

public interface UserService {

	UserDto registerNewUser(UserDto userDto);

	UserDto createUser(UserDto userDto);

	UserDto updateUser(UserDto userDto, Integer userId) throws ResourceNotFoundException;

	UserDto getUserById(Integer userId) throws ResourceNotFoundException;

	List<UserDto> getAllUser();

	void deleteUser(Integer userId) throws ResourceNotFoundException;

}
