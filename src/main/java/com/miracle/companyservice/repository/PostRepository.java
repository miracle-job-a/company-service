package com.miracle.companyservice.repository;

import com.miracle.companyservice.entity.Post;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    /**
     * @author kade
     * @param pageable
     * @return List<Post>
     * closed = 0 && deleted = 마감 임박 공정 반환
     */
    List<Post> findAllByClosedFalseAndDeletedFalseOrderByEndDateAsc(Pageable pageable);

    /**
     * @author kade
     * @param pageable
     * @return List<Post>
     * closed = 0 && deleted = 최신 공고 반환
     */
    List<Post> findAllByClosedFalseAndDeletedFalseOrderByModifiedAtDesc(Pageable pageable);
}
