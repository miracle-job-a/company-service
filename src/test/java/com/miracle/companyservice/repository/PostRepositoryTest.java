package com.miracle.companyservice.repository;

import com.miracle.companyservice.dto.response.ManagePostsResponseDto;
import com.miracle.companyservice.entity.Company;
import com.miracle.companyservice.entity.Post;
import com.miracle.companyservice.entity.PostType;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;

import javax.persistence.EntityManager;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
class PostRepositoryTest {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private EntityManager entityManager;

    @BeforeEach
    void setUp() {
        postRepository.deleteAll();
        entityManager.createNativeQuery("ALTER TABLE Post ALTER COLUMN id RESTART WITH 1").executeUpdate();

        entityManager.flush();
        entityManager.clear();

        Post post1 = Post.builder()
                .companyId(1L)
                .postType(PostType.NORMAL)
                .title("공고1 - 진행중")
                .endDate(LocalDateTime.of(2023, 12, 4, 00, 00))
                .closed(false)
                .deleted(false)
                .tool("개발툴")
                .workAddress("회사주소")
                .mainTask("메인업무")
                .workCondition("근무조건")
                .qualification("자격조건")
                .benefit("복지혜택")
                .specialSkill("우대기술")
                .process("채용절차")
                .notice("유의사항")
                .career(0)
                .jobIdSet(new HashSet<>())
                .stackIdSet(new HashSet<>())
                .build();
        post1.setCreatedAt(LocalDateTime.of(2023,11,01,00,00));
        postRepository.save(post1);

        Post post2 = Post.builder()
                .companyId(1L)
                .postType(PostType.NORMAL)
                .title("공고2 - 진행중")
                .endDate(LocalDateTime.of(2023, 12, 26, 00, 00))
                .closed(false)
                .deleted(false)
                .tool("개발툴")
                .workAddress("회사주소")
                .mainTask("메인업무")
                .workCondition("근무조건")
                .qualification("자격조건")
                .benefit("복지혜택")
                .specialSkill("우대기술")
                .process("채용절차")
                .notice("유의사항")
                .career(0)
                .jobIdSet(new HashSet<>())
                .stackIdSet(new HashSet<>())
                .build();
        post2.setCreatedAt(LocalDateTime.of(2023,11,02,00,00));
        postRepository.save(post2);

        Post post3 = Post.builder()
                .companyId(1L)
                .postType(PostType.NORMAL)
                .title("공고3 - 진행중")
                .endDate(LocalDateTime.of(2023, 12, 27, 00, 00))
                .closed(false)
                .deleted(false)
                .tool("개발툴")
                .workAddress("회사주소")
                .mainTask("메인업무")
                .workCondition("근무조건")
                .qualification("자격조건")
                .benefit("복지혜택")
                .specialSkill("우대기술")
                .process("채용절차")
                .notice("유의사항")
                .career(0)
                .jobIdSet(new HashSet<>())
                .stackIdSet(new HashSet<>())
                .build();
        post3.setCreatedAt(LocalDateTime.of(2023,11,03,00,00));
        postRepository.save(post3);

        Post post4 = Post.builder()
                .companyId(1L)
                .postType(PostType.NORMAL)
                .title("공고4 - 마감")
                .endDate(LocalDateTime.of(2023, 12, 2, 00, 00))
                .closed(true)
                .deleted(false)
                .tool("개발툴")
                .workAddress("회사주소")
                .mainTask("메인업무")
                .workCondition("근무조건")
                .qualification("자격조건")
                .benefit("복지혜택")
                .specialSkill("우대기술")
                .process("채용절차")
                .notice("유의사항")
                .career(0)
                .jobIdSet(new HashSet<>())
                .stackIdSet(new HashSet<>())
                .build();
        post4.setCreatedAt(LocalDateTime.of(2023,10,3,00,00));
        postRepository.save(post4);

        Post post5 = Post.builder()
                .companyId(1L)
                .postType(PostType.NORMAL)
                .title("공고5 - 마감")
                .endDate(LocalDateTime.of(2023, 12, 1, 00, 00))
                .closed(true)
                .deleted(false)
                .tool("개발툴")
                .workAddress("회사주소")
                .mainTask("메인업무")
                .workCondition("근무조건")
                .qualification("자격조건")
                .benefit("복지혜택")
                .specialSkill("우대기술")
                .process("채용절차")
                .notice("유의사항")
                .career(0)
                .jobIdSet(new HashSet<>())
                .stackIdSet(new HashSet<>())
                .build();
        post5.setCreatedAt(LocalDateTime.of(2023,10,13,00,00));
        postRepository.save(post5);

        Post post6 = Post.builder()
                .companyId(1L)
                .postType(PostType.NORMAL)
                .title("공고6 - 마감")
                .endDate(LocalDateTime.of(2023, 11, 30, 00, 00))
                .closed(true)
                .deleted(false)
                .tool("개발툴")
                .workAddress("회사주소")
                .mainTask("메인업무")
                .workCondition("근무조건")
                .qualification("자격조건")
                .benefit("복지혜택")
                .specialSkill("우대기술")
                .process("채용절차")
                .notice("유의사항")
                .career(0)
                .jobIdSet(new HashSet<>())
                .stackIdSet(new HashSet<>())
                .build();
        post6.setCreatedAt(LocalDateTime.of(2023,10,15,00,00));
        postRepository.save(post6);

        Post post7 = Post.builder()
                .companyId(1L)
                .postType(PostType.NORMAL)
                .title("공고7 - 삭제")
                .endDate(LocalDateTime.of(2023, 12, 25, 00, 00))
                .closed(false)
                .deleted(true)
                .tool("개발툴")
                .workAddress("회사주소")
                .mainTask("메인업무")
                .workCondition("근무조건")
                .qualification("자격조건")
                .benefit("복지혜택")
                .specialSkill("우대기술")
                .process("채용절차")
                .notice("유의사항")
                .career(0)
                .jobIdSet(new HashSet<>())
                .stackIdSet(new HashSet<>())
                .build();
        postRepository.save(post7);

        Post post8 = Post.builder()
                .companyId(1L)
                .postType(PostType.NORMAL)
                .title("공고8 - 마감임박")
                .endDate(LocalDateTime.of(2023, 11, 30, 00, 00))
                .closed(false)
                .deleted(false)
                .tool("개발툴")
                .workAddress("회사주소")
                .mainTask("메인업무")
                .workCondition("근무조건")
                .qualification("자격조건")
                .benefit("복지혜택")
                .specialSkill("우대기술")
                .process("채용절차")
                .notice("유의사항")
                .career(0)
                .jobIdSet(new HashSet<>())
                .stackIdSet(new HashSet<>())
                .build();
        post8.setCreatedAt(LocalDateTime.of(2023,11,8,00,00));
        postRepository.save(post8);

        Post post9 = Post.builder()
                .companyId(1L)
                .postType(PostType.NORMAL)
                .title("공고9 - 마감임박")
                .endDate(LocalDateTime.of(2023, 11, 29, 00, 00))
                .closed(false)
                .deleted(false)
                .tool("개발툴")
                .workAddress("회사주소")
                .mainTask("메인업무")
                .workCondition("근무조건")
                .qualification("자격조건")
                .benefit("복지혜택")
                .specialSkill("우대기술")
                .process("채용절차")
                .notice("유의사항")
                .career(0)
                .jobIdSet(new HashSet<>())
                .stackIdSet(new HashSet<>())
                .build();
        post9.setCreatedAt(LocalDateTime.of(2023,11,12,00,00));
        postRepository.save(post9);

        Post post10 = Post.builder()
                .companyId(1L)
                .postType(PostType.NORMAL)
                .title("공고10 - 마감임박")
                .endDate(LocalDateTime.of(2023, 11, 28, 00, 00))
                .closed(false)
                .deleted(false)
                .tool("개발툴")
                .workAddress("회사주소")
                .mainTask("메인업무")
                .workCondition("근무조건")
                .qualification("자격조건")
                .benefit("복지혜택")
                .specialSkill("우대기술")
                .process("채용절차")
                .notice("유의사항")
                .career(0)
                .jobIdSet(new HashSet<>())
                .stackIdSet(new HashSet<>())
                .build();
        post10.setCreatedAt(LocalDateTime.of(2023,11,15,00,00));
        postRepository.save(post10);
    }

    @Test
    @DisplayName("임박 공고 3건 반환 / 7일 내")
    void findTop3ByEndDateOrderByEndDateAsc() {
        List<Post> deadline = postRepository.findTop3ByEndDateOrderByEndDateAsc(PageRequest.of(0, 3));

        Assertions.assertThat(deadline.size()).isEqualTo(3);
        Assertions.assertThat(deadline.get(0).getTitle()).isEqualTo("공고10 - 마감임박");
        Assertions.assertThat(deadline.get(1).getTitle()).isEqualTo("공고9 - 마감임박");
        Assertions.assertThat(deadline.get(2).getTitle()).isEqualTo("공고8 - 마감임박");
    }

    @Test
    @DisplayName("최신 공고 3건 반환")
    void findAllByClosedFalseAndDeletedFalseOrderByCreatedAtDesc() {
        List<Post> newest3 = postRepository.findAllByClosedFalseAndDeletedFalseOrderByCreatedAtDesc(PageRequest.of(0, 3));

        Assertions.assertThat(newest3.size()).isEqualTo(3);
        Assertions.assertThat(newest3.get(0).getTitle()).isEqualTo("공고10 - 마감임박");
        Assertions.assertThat(newest3.get(1).getTitle()).isEqualTo("공고9 - 마감임박");
        Assertions.assertThat(newest3.get(2).getTitle()).isEqualTo("공고8 - 마감임박");
    }

    @Test
    @DisplayName("companyId와 postId 일치 확인")
    void existsByCompanyIdAndId() {
        Long companyId = 1L;
        Long postId = 10L;

        boolean result = postRepository.existsByCompanyIdAndId(companyId, postId);

        Assertions.assertThat(result).isTrue();

    }

    @Test
    @DisplayName("해당 기업의 전체 공고 수 반환")
    void countByCompanyIdAndDeletedFalse() {
        long compnayId = 1L;

        Long allPosts = postRepository.countByCompanyIdAndDeletedFalse(compnayId);

        Assertions.assertThat(allPosts).isEqualTo(9);
    }

    @Test
    @DisplayName("해당 기업의 마감된 공고 수 반환")
    void countByCompanyIdAndClosedTrueAndDeletedFalse() {
        long companyId = 1L;

        Long endedPosts = postRepository.countByCompanyIdAndClosedTrueAndDeletedFalse(companyId);

        Assertions.assertThat(endedPosts).isEqualTo(3);
    }

    @Test
    @DisplayName("해당 기업의 진행 중 공고 수 반환")
    void countByCompanyIdAndClosedFalseAndDeletedFalse() {
        long companyId = 1L;

        Long openPosts = postRepository.countByCompanyIdAndClosedFalseAndDeletedFalse(companyId);

        Assertions.assertThat(openPosts).isEqualTo(6);
    }

    @Test
    @DisplayName("최신순 정렬")
    void findAllByCompanyIdOrderByLatest() {
        long companyId = 1L;

        List<ManagePostsResponseDto> allByCompanyIdOrderByLatest = postRepository.findAllByCompanyIdOrderByLatest(companyId);

        Assertions.assertThat(allByCompanyIdOrderByLatest.size()).isEqualTo(9);
        Assertions.assertThat(allByCompanyIdOrderByLatest.get(0).getTitle()).isEqualTo("공고10 - 마감임박");
        Assertions.assertThat(allByCompanyIdOrderByLatest.get(5).getTitle()).isEqualTo("공고1 - 진행중");
        Assertions.assertThat(allByCompanyIdOrderByLatest.get(6).getTitle()).isEqualTo("공고6 - 마감");
        Assertions.assertThat(allByCompanyIdOrderByLatest.get(8).getTitle()).isEqualTo("공고4 - 마감");

    }

    @Test
    @DisplayName("마감임박 순 정렬")
    void findAllByCompanyIdOrderByDeadline() {
        long companyId = 1L;

        List<ManagePostsResponseDto> deadline = postRepository.findAllByCompanyIdOrderByDeadline(companyId);

        Assertions.assertThat(deadline.size()).isEqualTo(9);
        Assertions.assertThat(deadline.get(0).getTitle()).isEqualTo("공고10 - 마감임박");
        Assertions.assertThat(deadline.get(3).getTitle()).isEqualTo("공고1 - 진행중");
        Assertions.assertThat(deadline.get(5).getTitle()).isEqualTo("공고3 - 진행중");
        Assertions.assertThat(deadline.get(6).getTitle()).isEqualTo("공고6 - 마감");
        Assertions.assertThat(deadline.get(8).getTitle()).isEqualTo("공고4 - 마감");
    }

    @Test
    @DisplayName("마감된 공고만 보기")
    void findAllByCompanyIdOrderByEnd() {
        long companyId = 1L;
        List<ManagePostsResponseDto> end = postRepository.findAllByCompanyIdOrderByEnd(companyId);

        Assertions.assertThat(end.size()).isEqualTo(3);
        Assertions.assertThat(end.get(0).getTitle()).isEqualTo("공고6 - 마감");
        Assertions.assertThat(end.get(1).getTitle()).isEqualTo("공고5 - 마감");
        Assertions.assertThat(end.get(2).getTitle()).isEqualTo("공고4 - 마감");
    }

    @Test
    @DisplayName("진행 중 공고만 보기")
    void findAllByCompanyIdOrderByOpen() {
        long companyId = 1L;

        List<ManagePostsResponseDto> open = postRepository.findAllByCompanyIdOrderByOpen(companyId);

        Assertions.assertThat(open.size()).isEqualTo(6);
        Assertions.assertThat(open.get(0).getTitle()).isEqualTo("공고10 - 마감임박");
        Assertions.assertThat(open.get(1).getTitle()).isEqualTo("공고9 - 마감임박");
        Assertions.assertThat(open.get(2).getTitle()).isEqualTo("공고8 - 마감임박");
        Assertions.assertThat(open.get(3).getTitle()).isEqualTo("공고3 - 진행중");
        Assertions.assertThat(open.get(4).getTitle()).isEqualTo("공고2 - 진행중");
        Assertions.assertThat(open.get(5).getTitle()).isEqualTo("공고1 - 진행중");
    }
}