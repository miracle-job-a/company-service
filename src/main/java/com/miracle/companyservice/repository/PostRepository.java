package com.miracle.companyservice.repository;

import com.miracle.companyservice.entity.Company;
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

    /**
     * @author kade
     * @param companyId
     * @param postId
     * @return boolean
     * companyID와 postId가 일치하는 공고 유무 확인
     */
    boolean existsByCompanyIdAndId(Long companyId, Long postId);


    Long countByCompanyIdAndDeletedFalse(Long companyId); //전체 공고
    Long countByCompanyIdAndClosedTrueDeletedFalse(Long companyId); //마감공고수
    Long countByCompanyIdAndClosedFalseDeletedFalse(Long companyId); //진행중공고
}
