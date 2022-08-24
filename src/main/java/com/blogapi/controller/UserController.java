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
import com.blogapi.payloads.UserDto;
import com.blogapi.services.UserService;

/**
 * @author prabhat.thakur,@author rahul
 *
 */
@RestController
@RequestMapping("/api/v1/users")
public class UserController {

	@Autowired
	private UserService userService;

	/**
	 * POST-Create user
	 *
	 */
	@PostMapping("/createuser")
	public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto userDto) {
		UserDto createdUser = this.userService.createUser(userDto);
		return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
	}

	/**
	 * PUT-Update user
	 * 
	 * @throws ResourceNotFoundException
	 *
	 */

	@PutMapping("/updateuser/{id}")
	public ResponseEntity<UserDto> updateUser(@Valid @RequestBody UserDto userDto, @PathVariable Integer id)
			throws ResourceNotFoundException {
		UserDto updatedUser = this.userService.updateUser(userDto, id);
		return new ResponseEntity<>(updatedUser, HttpStatus.OK);
	}

	/**
	 * DELETE- Delete user
	 * 
	 * @throws ResourceNotFoundException
	 *
	 */
	@DeleteMapping("/deleteuser/{id}")
	public ResponseEntity<ApiResponse> deleteUser(@PathVariable("id") Integer id) throws ResourceNotFoundException {
		this.userService.deleteUser(id);
		// return new ResponseEntity(Map.of("message","User Deleted
		// Successfully"),HttpStatus.OK);
		return new ResponseEntity(new ApiResponse("User Deleted Successfully", true), HttpStatus.OK);
	}

	/**
	 * GET- Get all users
	 *
	 */
	@GetMapping("/getAllUsers")
	public ResponseEntity<List<UserDto>> getAllUsers() {
		return ResponseEntity.ok(this.userService.getAllUser());
	}

	/**
	 * GET- Get user by specific id
	 * 
	 * @throws ResourceNotFoundException
	 *
	 */
	@GetMapping("/getSingleUser/{id}")
	public ResponseEntity<UserDto> getUserById(@PathVariable Integer id) throws ResourceNotFoundException {
		return ResponseEntity.ok(this.userService.getUserById(id));
	}
}
