package com.blogapi.security;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.blogapi.entities.User;
import com.blogapi.exception.ResourceNotFoundException;
import com.blogapi.repository.UserRepository;

@Service
public class CustomUserDetailService implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		// loading username from database

		User user = null;
		try {
			user = this.userRepository.findByEmail(username)
					.orElseThrow(() -> new ResourceNotFoundException("user", "Email:" + username, 0));
		} catch (ResourceNotFoundException e) {
			e.printStackTrace();
		}
		return user;

	}

}
