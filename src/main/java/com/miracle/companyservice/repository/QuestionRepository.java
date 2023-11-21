package com.miracle.companyservice.repository;

import com.miracle.companyservice.entity.Post;
import com.miracle.companyservice.entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {
}
