package com.miracle.companyservice.repository;

import com.miracle.companyservice.dto.response.PostListResponseDto;
import com.miracle.companyservice.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * The interface Post repository.
 */
@Repository
public interface PostRepository extends JpaRepository<Post, Long>, JpaSpecificationExecutor<Post> {
    /**
     * Find top 3 by end date order by end date asc list.
     *
     * @param pageable the pageable
     * @return List<Post>  현재 날짜와 비교하여 마감임박 공고를 반환합니다. closed = false (마감여부) deleted = false (삭제여부)
     * @author kade
     */
    @Query("SELECT p FROM Post p " +
            "WHERE p.closed = false " +
            "AND p.deleted = false " +
            "ORDER BY p.endDate ASC")
    List<Post> findTop3ByEndDateOrderByEndDateAsc(Pageable pageable);

    /**
     * Find all by closed false and deleted false order by created at desc list.
     *
     * @param pageable the pageable
     * @return List<Post>  closed = 0 && deleted = 최신 공고 반환
     * @author kade
     */
    List<Post> findAllByClosedFalseAndDeletedFalseOrderByCreatedAtDesc(Pageable pageable);

    /**
     * Exists by company id and id boolean.
     *
     * @param companyId the company id
     * @param postId    the post id
     * @return boolean  companyID와 postId가 일치하는 공고 유무 확인
     * @author kade
     */
    boolean existsByCompanyIdAndId(Long companyId, Long postId);

    /**
     * Count by company id and deleted false long.
     *
     * @param companyId the company id
     * @return Long  해당 기업의 전체 공고 수 반환하는 메서드
     * @author kade
     */
    Long countByCompanyIdAndDeletedFalse(Long companyId); //전체 공고

    /**
     * Count by company id and closed true and deleted false long.
     *
     * @param companyId the company id
     * @return Long  해당 기업의 마감된 공고 수 반환하는 메서드
     * @author kade
     */
    Long countByCompanyIdAndClosedTrueAndDeletedFalse(Long companyId); //마감공고수

    /**
     * Count by company id and closed false and deleted false long.
     *
     * @param companyId the company id
     * @return Long  해당 기업의 진행 중인 공고를 반환하는 메서드
     * @author kade
     */
    Long countByCompanyIdAndClosedFalseAndDeletedFalse(Long companyId); //진행중공고

    /**
     * Find all by company id order by latest page.
     *
     * @param companyId the company id
     * @param pageable  the pageable
     * @return List<CompanyManagePostsResponseDto>  기업이 공고관리를 들어갔을 때, 최신순으로 정렬하여 반환하는 메서드 (디폴트)
     * @author kade
     */
    @Query("SELECT p " +
            "FROM Post p " +
            "WHERE p.companyId = :companyId AND p.deleted = false " +
            "ORDER BY p.closed ASC, p.createdAt DESC")
    Page<Post> findAllByCompanyIdOrderByLatest(Long companyId, Pageable pageable);

    /**
     * Find all by company id order by deadline page.
     *
     * @param companyId the company id
     * @param pageable  the pageable
     * @return List<CompanyManagePostsResponseDto>  기업이 공고 관리를 들어갔을 때, 마감임박 순으로 정렬하는 메서드
     * @author kade
     */
    @Query("SELECT p " +
            "FROM Post p " +
            "WHERE p.companyId = :companyId AND p.deleted = false " +
            "ORDER BY p.closed ASC, p.endDate ASC")
    Page<Post> findAllByCompanyIdOrderByDeadline(Long companyId, Pageable pageable);

    /**
     * Find all by company id order by close page.
     *
     * @param companyId the company id
     * @param pageable  the pageable
     * @return List<CompanyManagePostsResponseDto>  기업이 공고 관리를 들어갔을 때, 종료된 공고만 정렬하여 보여주는 메서드 진행중 공고는 보이지 않습니다.
     * @author kade
     */
    @Query("SELECT p " +
            "FROM Post p " +
            "WHERE p.companyId = :companyId AND p.deleted = false AND p.closed = true " +
            "ORDER BY p.createdAt DESC")
    Page<Post> findAllByCompanyIdOrderByClose(Long companyId, Pageable pageable);

    /**
     * Find all by company id order by open page.
     *
     * @param companyId the company id
     * @param pageable  the pageable
     * @return List<CompanyManagePostsResponseDto>  기업이 공고 관리를 들어갔을 때, 진행 중 공고만 정렬하여 보여주는 메서드 마감된 공고는 보이지 않습니다.
     * @author kade
     */
    @Query("SELECT p " +
            "FROM Post p " +
            "WHERE p.companyId = :companyId AND p.deleted = false AND p.closed = false " +
            "ORDER BY p.createdAt DESC")
    Page<Post> findAllByCompanyIdOrderByOpen(Long companyId, Pageable pageable);

    /**
     * Find by keyword page.
     *
     * @param keyword  the keyword
     * @param pageable the pageable
     * @return Page<PostListResponseDto>  사용자가 키워드를 검색하면 해당 키워드가 포함된 공고 관련 데이터를 조회 후, 최신순으로 반환하는 메서드
     * @author wjdals3936
     */
    @Query("SELECT p FROM Post p WHERE " +
            "p.title LIKE :keyword OR " +
            "p.mainTask LIKE :keyword OR " +
            "p.workCondition LIKE :keyword OR " +
            "p.qualification LIKE :keyword OR " +
            "p.benefit LIKE :keyword OR " +
            "p.specialSkill LIKE :keyword OR " +
            "p.tool LIKE :keyword " +
            "ORDER BY p.createdAt DESC")
    Page<PostListResponseDto> findByKeyword(@Param("keyword") String keyword, Pageable pageable);

    /**
     * @author kade
     * @param spec can be {@literal null}.
     * @param pageable must not be {@literal nsull}.
     * @return List<ConditionalSearchPostReponseDto>
     * 공고의 조건 검색에 따른 결과를 리턴합니다.
     */
    @Override
    Page<Post> findAll(Specification<Post> spec, Pageable pageable);

    /**
     * @author wjdals3936
     * @param postId
     * @return Optional<Post>
     * postId를 통한 companyId 조회
     */
//    List<Post> findCompanyIdByIn(Set<Long> postIdSet);
    Optional<Post> findPostById(Long postId);
}
