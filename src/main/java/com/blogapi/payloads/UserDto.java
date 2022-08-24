package com.blogapi.payloads;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import com.blogapi.entities.Role;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class UserDto {
	
	private Integer id;
	
	@NotEmpty
	@Size(min = 4, message = "name should be minimum of 4 characters!!")
	private String name;
	
	@NotEmpty
	@Email(message = "Email is not valid!!!!")
	private String email;
	
	@NotEmpty
	@Size(min = 4, max = 8, message = "password length should be between 4 and 8!!!")
	
	private String password;
	
	@NotEmpty
	private String about;
	
	private Set<Role> role = new HashSet<>();

	
}
