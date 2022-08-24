package com.blogapi.payloads;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CategoryDto {
	
	
	private Integer categotyId;
	
	@NotBlank
	@Size(min = 4,message = "should be minimum 4 characters!!!")
	private String categoryTitle;
	
	@NotBlank
	@Size(min = 4, message = "should be minimum 4 characters!!!")
	private String categoryDescription;

}
