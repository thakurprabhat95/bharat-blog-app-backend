package com.blogapi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blogapi.exception.ApiException;
import com.blogapi.payloads.JwtAuthRequest;
import com.blogapi.payloads.JwtAuthResponse;
import com.blogapi.payloads.UserDto;
import com.blogapi.security.JwtTokenHelper;
import com.blogapi.services.UserService;

@RestController
@RequestMapping("/api/v1/auth")
@CrossOrigin
public class AuthController {
	
	@Autowired
	private JwtTokenHelper jwtTokenHelper;
	
	@Autowired
	private UserDetailsService userDetailsService;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private UserService userService;
	
	@PostMapping("/login")
	ResponseEntity<JwtAuthResponse> createToken(@RequestBody JwtAuthRequest jwtAuthRequest) throws ApiException
	{
		//first authenticate
		this.authenticate(jwtAuthRequest.getUsername(),jwtAuthRequest.getPassword());
		UserDetails userDetails = this.userDetailsService.loadUserByUsername(jwtAuthRequest.getUsername());
		
		//genearte token
		String genertaedToken = this.jwtTokenHelper.generateToken(userDetails);
		JwtAuthResponse jwtAuthResponse=new JwtAuthResponse();
		jwtAuthResponse.setToken(genertaedToken);
		return new ResponseEntity<JwtAuthResponse>(jwtAuthResponse,HttpStatus.OK);
		
	}

	private void authenticate(String username, String password) throws ApiException {
		UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken=new UsernamePasswordAuthenticationToken(username,password);
		
		try {
		this.authenticationManager.authenticate(usernamePasswordAuthenticationToken);
		}
		catch (BadCredentialsException e) {
			throw new ApiException("Invalid username and password!!!");
		}
		
	}
	
	@PostMapping("/register")
	public ResponseEntity<UserDto>  registerNewUser(@RequestBody UserDto userDto)
	{
		UserDto registeredUser = this.userService.registerNewUser(userDto);
		return new ResponseEntity<UserDto>(registeredUser,HttpStatus.CREATED);
		
	}
	

}

