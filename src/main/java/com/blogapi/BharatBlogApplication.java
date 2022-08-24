package com.blogapi;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.blogapi.config.AppConstant;
import com.blogapi.entities.Role;
import com.blogapi.entities.User;
import com.blogapi.payloads.UserDto;
import com.blogapi.repository.RoleRepository;
import com.blogapi.repository.UserRepository;

@SpringBootApplication
public class BharatBlogApplication implements CommandLineRunner {

	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private RoleRepository roleRepository;
	
	@Autowired
	private UserRepository userRepository;
	

	public static void main(String[] args) {
		SpringApplication.run(BharatBlogApplication.class, args);
	}

	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}

	@Override
	public void run(String... args) throws Exception {
		System.out.println(this.passwordEncoder.encode("abcd1234"));
		
		
		
		try {
		Role admin_role=new Role();
		admin_role.setId(AppConstant.ADMIN_USER);
		admin_role.setName("ROLE_ADMIN");
		
		Role normal_user_role=new Role();
		normal_user_role.setId(AppConstant.NORMAL_USER);
		normal_user_role.setName("ROLE_NORMAL");
		
		List<Role> allRoles=List.of(admin_role,normal_user_role);
		
		//save into database
		List<Role> saveAllRoles = this.roleRepository.saveAll(allRoles);
		saveAllRoles.forEach(r->{
			System.out.println(r.getName());
		});

	}
		catch (Exception e) {
			e.printStackTrace();
		}
		}
		

}
