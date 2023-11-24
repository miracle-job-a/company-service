package com.miracle.companyservice.repository;

import com.miracle.companyservice.dto.response.ManagePostsResponseDto;
import com.miracle.companyservice.entity.Post;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    /**
     * @author kade
     * @param now
     * @param pageable
     * @return List<Post>
     * 현재 날짜와 비교하여 마감임박 공고를 반환합니다.
     * closed = false (마감여부)
     * deleted = false (삭제여부)
     */
    @Query("SELECT p FROM Post p " +
            "WHERE p.closed = false " +
            "AND p.deleted = false " +
            "AND p.endDate >= :now " +
            "ORDER BY p.endDate ASC")
    List<Post> findTop3ByEndDateOrderByEndDateAsc(LocalDateTime now, Pageable pageable);

    /**
     * @author kade
     * @param pageable
     * @return List<Post>
     * closed = 0 && deleted = 최신 공고 반환
     */
    List<Post> findAllByClosedFalseAndDeletedFalseOrderByCreatedAtDesc(Pageable pageable);

    /**
     * @author kade
     * @param companyId
     * @param postId
     * @return boolean
     * companyID와 postId가 일치하는 공고 유무 확인
     */
    boolean existsByCompanyIdAndId(Long companyId, Long postId);

    /**
     * @author kade
     * @param companyId
     * @return Long
     * 해당 기업의 전체 공고 수 반환하는 메서드
     */
    Long countByCompanyIdAndDeletedFalse(Long companyId); //전체 공고

    /**
     * @author kade
     * @param companyId
     * @return Long
     * 해당 기업의 마감된 공고 수 반환하는 메서드
     */
    Long countByCompanyIdAndClosedTrueAndDeletedFalse(Long companyId); //마감공고수

    /**
     * @author kade
     * @param companyId
     * @return Long
     * 해당 기업의 진행 중인 공고를 반환하는 메서드
     */
    Long countByCompanyIdAndClosedFalseAndDeletedFalse(Long companyId); //진행중공고

    /**
     * @author kade
     * @param companyId
     * @return List<CompanyManagePostsResponseDto>
     * 기업이 공고관리를 들어갔을 때, 최신순으로 정렬하여 반환하는 메서드 (디폴트)
     */
    @Query("SELECT new com.miracle.companyservice.dto.response.ManagePostsResponseDto(p.id, p.postType, p.title, p.createdAt, p.endDate, p.closed) " +
            "FROM Post p " +
            "WHERE p.companyId = :companyId AND p.deleted = false " +
            "ORDER BY p.closed ASC, p.createdAt DESC")
    List<ManagePostsResponseDto> findAllByCompanyIdOrderByLatest(Long companyId);

    /**
     * @author kade
     * @param companyId
     * @param now
     * @return List<CompanyManagePostsResponseDto>
     * 기업이 공고 관리를 들어갔을 때, 마감임박 순으로 정렬하는 메서드
     * 마감된 공고는 보여주지 않습니다.
     */
    @Query("SELECT new com.miracle.companyservice.dto.response.ManagePostsResponseDto(p.id, p.postType, p.title, p.createdAt, p.endDate, p.closed) " +
            "FROM Post p " +
            "WHERE p.companyId = :companyId AND p.deleted = false AND p.closed = false AND p.endDate >= :now " +
            "ORDER BY p.endDate ASC")
    List<ManagePostsResponseDto> findAllByCompanyIdOrderByDeadline(Long companyId, LocalDateTime now);

    /**
     * @author kade
     * @param companyId
     * @return List<CompanyManagePostsResponseDto>
     * 기업이 공고 관리를 들어갔을 때, 종료된 공고만 정렬하여 보여주는 메서드
     * 진행중 공고는 보이지 않습니다.
     */
    @Query("SELECT new com.miracle.companyservice.dto.response.ManagePostsResponseDto(p.id, p.postType, p.title, p.createdAt, p.endDate, p.closed) " +
            "FROM Post p " +
            "WHERE p.companyId = :companyId AND p.deleted = false AND p.closed = true " +
            "ORDER BY p.createdAt DESC")
    List<ManagePostsResponseDto> findAllByCompanyIdOrderByEnd(Long companyId);

    /**
     * @author kade
     * @param companyId
     * @return List<CompanyManagePostsResponseDto>
     * 기업이 공고 관리를 들어갔을 떄, 진행 중 공고만 정렬하여 보여주는 메서드
     * 마감된 공고는 보이지 않습니다.
     */
    @Query("SELECT new com.miracle.companyservice.dto.response.ManagePostsResponseDto(p.id, p.postType, p.title, p.createdAt, p.endDate, p.closed) " +
            "FROM Post p " +
            "WHERE p.companyId = :companyId AND p.deleted = false AND p.closed = false " +
            "ORDER BY p.createdAt DESC")
    List<ManagePostsResponseDto> findAllByCompanyIdOrderByOpen(Long companyId);
}
