package com.miracle.companyservice.repository;

import com.miracle.companyservice.entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {
    List<Question> findByPostId(Long postId);
    Optional<Question> findByIdAndPostId(Long id, Long postId);
}
