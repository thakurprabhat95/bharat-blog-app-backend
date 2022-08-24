package com.blogapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.blogapi.entities.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {

}
