package com.blogapi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.blogapi.entities.Category;
import com.blogapi.entities.Post;
import com.blogapi.entities.User;

public interface PostRepository extends JpaRepository<Post, Integer> {

	List<Post> findByCategory(Category category);

	List<Post> findByUser(User user);

	@Query("select p from Post p where p.title like :keyword")
	List<Post> findByTitleContaining(@Param("keyword") String title);

}
