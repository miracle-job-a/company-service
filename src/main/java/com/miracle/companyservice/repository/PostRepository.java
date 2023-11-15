package com.miracle.companyservice.repository;

import com.miracle.companyservice.entity.Company;
import com.miracle.companyservice.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
}