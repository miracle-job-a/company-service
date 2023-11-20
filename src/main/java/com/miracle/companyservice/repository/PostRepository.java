package com.miracle.companyservice.repository;

import com.miracle.companyservice.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {


    List<Post> findTop3ByOrderByModifiedAtDesc();

    List<Post> findTop3ByOrderByEndDateAsc();
}
