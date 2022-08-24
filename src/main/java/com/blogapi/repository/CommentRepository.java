package com.blogapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.blogapi.entities.Comment;

public interface CommentRepository extends JpaRepository<Comment, Integer> {

}
