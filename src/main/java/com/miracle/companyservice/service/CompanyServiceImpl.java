package com.miracle.companyservice.service;

import com.miracle.companyservice.dto.request.CompanyFaqRequestDto;
import com.miracle.companyservice.dto.request.CompanyLoginRequestDto;
import com.miracle.companyservice.dto.request.CompanySignUpRequestDto;
import com.miracle.companyservice.dto.request.PostRequestDto;
import com.miracle.companyservice.dto.request.QuestionRequestDto;
import com.miracle.companyservice.dto.response.CommonApiResponse;
import com.miracle.companyservice.dto.response.CompanyFaqResponseDto;
import com.miracle.companyservice.dto.response.PostCommonDataResponseDto;
import com.miracle.companyservice.dto.response.SuccessApiResponse;
import com.miracle.companyservice.dto.response.*;
import com.miracle.companyservice.entity.Company;

import com.miracle.companyservice.entity.CompanyFaq;
import com.miracle.companyservice.entity.Post;
import com.miracle.companyservice.entity.Question;
import com.miracle.companyservice.repository.CompanyFaqRepository;
import com.miracle.companyservice.repository.BnoRepository;
import com.miracle.companyservice.repository.CompanyRepository;
import com.miracle.companyservice.repository.PostRepository;
import com.miracle.companyservice.repository.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
public class CompanyServiceImpl implements CompanyService {

    private final CompanyRepository companyRepository;
    private final CompanyFaqRepository companyFaqRepository;
    private final BnoRepository bnoRepository;
    private final PostRepository postRepository;
    private final QuestionRepository questionRepository;

    @Autowired
    public CompanyServiceImpl(CompanyRepository companyRepository, CompanyFaqRepository companyFaqRepository, BnoRepository bnoRepository, PostRepository postRepository, QuestionRepository questionRepository) {
        this.companyRepository = companyRepository;
        this.companyFaqRepository = companyFaqRepository;
        this.bnoRepository = bnoRepository;
        this.postRepository = postRepository;
        this.questionRepository = questionRepository;
    }

    public Boolean companyValidation(Long id, String email, String bno) {
        return companyRepository.existsByIdAndEmailAndBno(id, email, bno);
    }

    public CommonApiResponse checkEmailDuplicated(String email) {
        log.info("email : {}", email);
        if (companyRepository.existsByEmail(email)) {
            return SuccessApiResponse.builder()
                    .httpStatus(HttpStatus.BAD_REQUEST.value())
                    .message("중복된 이메일입니다.")
                    .data(Boolean.FALSE)
                    .build();
        }
        return SuccessApiResponse.builder()
                .httpStatus(HttpStatus.OK.value())
                .message("사용가능한 이메일입니다.")
                .data(Boolean.TRUE)
                .build();
    }

    public CommonApiResponse signUpCompany(CompanySignUpRequestDto companySignUpRequestDto) {
        log.info("companySignUpRequestDto : {}", companySignUpRequestDto);
        if (companyRepository.existsByEmail(companySignUpRequestDto.getEmail())) {
            return SuccessApiResponse.builder()
                    .httpStatus(HttpStatus.BAD_REQUEST.value())
                    .message("중복된 이메일입니다.")
                    .data(Boolean.FALSE)
                    .build();

        }
        companyRepository.save(new Company(companySignUpRequestDto));
        return SuccessApiResponse.builder()
                .httpStatus(HttpStatus.OK.value())
                .message("회원가입 성공")
                .data(Boolean.TRUE)
                .build();
    }

    public CommonApiResponse loginCompany(CompanyLoginRequestDto companyLoginRequestDto) {
        log.info("email : {}, password : {}", companyLoginRequestDto.getEmail(), companyLoginRequestDto.getPassword().hashCode());
        if (!companyRepository.existsByEmail(companyLoginRequestDto.getEmail())) {
            return SuccessApiResponse.builder()
                    .httpStatus(HttpStatus.BAD_REQUEST.value())
                    .message("이메일 또는 비밀번호가 일치하지 않습니다.")
                    .data(Boolean.FALSE)
                    .build();
        }
        Optional<Company> company = companyRepository.findByEmailAndPassword(companyLoginRequestDto.getEmail(), companyLoginRequestDto.getPassword().hashCode());
        if (company.isEmpty()) {
            return SuccessApiResponse.builder()
                    .httpStatus(HttpStatus.BAD_REQUEST.value())
                    .message("이메일 또는 비밀번호가 일치하지 않습니다.")
                    .data(Boolean.FALSE)
                    .build();
        }
        if (!bnoRepository.existsByBno(company.get().getBno())) {
            return SuccessApiResponse.builder()
                    .httpStatus(HttpStatus.BAD_REQUEST.value())
                    .message("존재하지 않는 사업자 번호입니다.")
                    .data(Boolean.FALSE)
                    .build();
        }

        if (!bnoRepository.findStatusByBnoIsTrue(company.get().getBno())) {
            return SuccessApiResponse.builder()
                    .httpStatus(HttpStatus.BAD_REQUEST.value())
                    .message("만료된 사업자 번호입니다.")
                    .data(Boolean.FALSE)
                    .build();
        }
        CompanyLoginResponseDto responseDto = new CompanyLoginResponseDto(company.get().getId(), company.get().getEmail(), company.get().getBno());
        return SuccessApiResponse.builder()
                .httpStatus(HttpStatus.OK.value())
                .message("로그인 성공")
                .data(responseDto)
                .build();
    }

    public CommonApiResponse checkBnoStatus(String bno) {
        log.info("bno : {}", bno);
        if (!bnoRepository.existsByBno(bno)) {
            return SuccessApiResponse.builder()
                    .httpStatus(HttpStatus.BAD_REQUEST.value())
                    .message("존재하지 않는 사업자 번호입니다.")
                    .data(Boolean.FALSE)
                    .build();
        }
        if (!bnoRepository.findStatusByBnoIsTrue(bno)) {
            return SuccessApiResponse.builder()
                    .httpStatus(HttpStatus.BAD_REQUEST.value())
                    .message("만료된 사업자 번호입니다.")
                    .data(Boolean.FALSE)
                    .build();
        }
        if (companyRepository.existsByBno(bno)) {
            return SuccessApiResponse.builder()
                    .httpStatus(HttpStatus.BAD_REQUEST.value())
                    .message("이미 가입된 사업자 번호입니다.")
                    .data(Boolean.FALSE)
                    .build();
        }
        return SuccessApiResponse.builder()
                .httpStatus(HttpStatus.OK.value())
                .message("가입 가능한 사업자 번호입니다.")
                .data(Boolean.TRUE)
                .build();
    }

    public CommonApiResponse postForMainPage() {
        List<Post> newestResult = postRepository.findAllByClosedFalseAndDeletedFalseOrderByCreatedAtDesc(PageRequest.of(0, 3));
        List<MainPagePostsResponseDto> newest = new ArrayList<>();
        newestResult.iterator().forEachRemaining((Post p) -> {
            String photo = companyRepository.findPhotoById(p.getCompanyId());
            newest.add(new MainPagePostsResponseDto(p, photo));
        });

        List<Post> deadlineResult = postRepository.findTop3ByEndDateOrderByEndDateAsc(LocalDateTime.now(), PageRequest.of(0, 3));
        List<MainPagePostsResponseDto> deadline = new ArrayList<>();
        deadlineResult.iterator().forEachRemaining((Post p) -> {
            String photo = companyRepository.findPhotoById(p.getCompanyId());
            deadline.add(new MainPagePostsResponseDto(p, photo));
        });

        Map<String, Object> data = new HashMap<>();
        data.put("newest", newest);
        data.put("deadline", deadline);

        return SuccessApiResponse.builder()
                .httpStatus(HttpStatus.OK.value())
                .message("최신 공고, 마감임박 공고 조회 성공")
                .data(data)
                .build();
    }

    public CommonApiResponse addFaq(CompanyFaqRequestDto companyFaqRequestDto) {
        Optional<Company> company = companyRepository.findById(companyFaqRequestDto.getCompanyId());
        if (company.isEmpty()) {
            return SuccessApiResponse.builder()
                    .httpStatus(HttpStatus.BAD_REQUEST.value())
                    .message("존재하지 않는 companyId 입니다.")
                    .data(Boolean.FALSE)
                    .build();
        }

        Long count = companyFaqRepository.countByCompanyId(companyFaqRequestDto.getCompanyId());
        if (count >= 10) {
            return SuccessApiResponse.builder()
                    .httpStatus(HttpStatus.BAD_REQUEST.value())
                    .message("FAQ는 10개를 넘을 수 없습니다.")
                    .data(Boolean.FALSE)
                    .build();
        }
        CompanyFaq save = companyFaqRepository.save(new CompanyFaq(companyFaqRequestDto.getQuestion(), companyFaqRequestDto.getAnswer(), company.get()));
        return SuccessApiResponse.builder()
                .httpStatus(HttpStatus.OK.value())
                .message("FAQ 등록 성공")
                .data(save.getId())
                .build();
    }

    public CommonApiResponse deleteFaq(Long companyId, Long faqId) {
        if (faqId == null || faqId <= 0) {
            return SuccessApiResponse.builder()
                    .httpStatus(HttpStatus.BAD_REQUEST.value())
                    .message("faq 값이 0보다 작습니다.")
                    .data(Boolean.FALSE)
                    .build();
        }
        if (!companyFaqRepository.existsById(faqId)) {
            return SuccessApiResponse.builder()
                    .httpStatus(HttpStatus.BAD_REQUEST.value())
                    .message("존재하지 않는 faqId 입니다.")
                    .data(Boolean.FALSE)
                    .build();
        }
        if (!companyFaqRepository.existsByCompanyIdAndId(companyId, faqId)) {
            return SuccessApiResponse.builder()
                    .httpStatus(HttpStatus.BAD_REQUEST.value())
                    .message("companyId와 삭제하려는 faq의 compnayId가 일치하지 않습니다.")
                    .data(Boolean.FALSE)
                    .build();
        }
        companyFaqRepository.deleteById(faqId);
        return SuccessApiResponse.builder()
                .httpStatus(HttpStatus.OK.value())
                .message("FAQ 삭제 성공")
                .data(Boolean.TRUE)
                .build();
    }

    public CommonApiResponse getFaq(Long companyId) {
        if (companyId == null || companyId <= 0) {
            return SuccessApiResponse.builder()
                    .httpStatus(HttpStatus.BAD_REQUEST.value())
                    .message("companyId 값이 0보다 작습니다.")
                    .data(Boolean.FALSE)
                    .build();
        }
        if (!companyRepository.existsById(companyId)) {
            return SuccessApiResponse.builder()
                    .httpStatus(HttpStatus.BAD_REQUEST.value())
                    .message("존재하지 않는 companyId 입니다.")
                    .data(Boolean.FALSE)
                    .build();
        }
        List<CompanyFaq> faqList = companyFaqRepository.findByCompanyId(companyId);
        List<CompanyFaqResponseDto> faqDtoList = new ArrayList<>();

        faqList.iterator().forEachRemaining((CompanyFaq c) -> faqDtoList.add(new CompanyFaqResponseDto(c.getId(), c.getQuestion(), c.getAnswer())));
        return SuccessApiResponse.builder()
                .httpStatus(HttpStatus.OK.value())
                .message("FAQ 조회 성공")
                .data(faqDtoList)
                .build();
    }

    public CommonApiResponse returnQuestions(Long companyId, Long postId) {
        if (!postRepository.existsByCompanyIdAndId(companyId, postId)) {
            return SuccessApiResponse.builder()
                    .httpStatus(HttpStatus.BAD_REQUEST.value())
                    .message("companyId가 공고의 companyId 값과 다릅니다.")
                    .data(Boolean.FALSE)
                    .build();
        }
        Optional<Post> post = postRepository.findById(postId);
        if (post.isEmpty()) {
            return SuccessApiResponse.builder()
                    .httpStatus(HttpStatus.BAD_REQUEST.value())
                    .message("존재하지 않는 공고입니다.")
                    .data(Boolean.FALSE)
                    .build();
        }
        List<Question> questionList = post.get().getQuestionList();

        List<QuestionResponseDto> questionResponseDtoList = new ArrayList<>();
        questionList.iterator().forEachRemaining((Question q) -> questionResponseDtoList.add(new QuestionResponseDto(q)));
        return SuccessApiResponse.builder()
                .httpStatus(HttpStatus.OK.value())
                .message("자기소개서 질문 조회 성공")
                .data(questionResponseDtoList)
                .build();
    }

    public CommonApiResponse getCountPosts(Long companyId) {
        Long countAllPosts = postRepository.countByCompanyIdAndDeletedFalse(companyId);
        Long countEndedPosts = postRepository.countByCompanyIdAndClosedTrueAndDeletedFalse(companyId);
        Long countOnGoing = postRepository.countByCompanyIdAndClosedFalseAndDeletedFalse(companyId);

        Map<String, Long> map = new HashMap<>();
        map.put("countAllPosts", countAllPosts);
        map.put("countEndedPosts", countEndedPosts);
        map.put("countOnGoing",countOnGoing);

        return SuccessApiResponse.builder()
                .httpStatus(HttpStatus.OK.value())
                .message("공고 수 조회 완료")
                .data(map)
                .build();
    }

    public CommonApiResponse changeToClose(Long companyId, Long postId) {
        if (!postRepository.existsByCompanyIdAndId(companyId, postId)) {
            return SuccessApiResponse.builder()
                    .httpStatus(HttpStatus.BAD_REQUEST.value())
                    .message("companyId가 공고의 companyId 값과 다릅니다.")
                    .data(Boolean.FALSE)
                    .build();
        }
        Optional<Post> postById = postRepository.findById(postId);
        if (postById.isEmpty()) {
            return SuccessApiResponse.builder()
                    .httpStatus(HttpStatus.BAD_REQUEST.value())
                    .message("공고가 존재하지 않습니다.")
                    .data(Boolean.FALSE)
                    .build();
        }
        Post post = postById.get();
        post.setClosed(true);
        postRepository.save(post);
        return SuccessApiResponse.builder()
                .httpStatus(HttpStatus.OK.value())
                .message("공고가 마감처리 되었습니다.")
                .data(Boolean.TRUE)
                .build();
    }



    @Override
    public CommonApiResponse getCompanyFaqsByCompanyId(Long companyId) {
        Optional<Company> company = companyRepository.findById(companyId);
        if (company.isEmpty()) {
            return SuccessApiResponse.builder()
                    .httpStatus(HttpStatus.NOT_FOUND.value())
                    .message("기업 정보가 없습니다.")
                    .data(Boolean.FALSE)
                    .build();
        }

        log.info("company : {}", company);

        List<CompanyFaq> faqs = companyFaqRepository.findByCompanyId(companyId);
        log.info("faqs : {}", faqs);

        List<CompanyFaqResponseDto> faqList = faqs.stream()
                .map(companyFaq -> new CompanyFaqResponseDto(companyFaq))
                .collect(Collectors.toList());
        log.info("faqList : {}", faqList);

        PostCommonDataResponseDto responseDto = new PostCommonDataResponseDto(
                company.get(),
                faqList);

        return SuccessApiResponse.builder()
                .httpStatus(HttpStatus.OK.value())
                .message("SUCCESS")
                .data(responseDto)
                .build();
    }

    @Override
    public CommonApiResponse savePost(PostRequestDto postRequestDto) {

        List<QuestionRequestDto> questions = postRequestDto.getQuestionList();
        log.info("questions : {}", questions);

        Post post = Post.builder()
                .companyId(postRequestDto.getCompanyId())
                .postType(postRequestDto.getPostType())
                .title(postRequestDto.getTitle())
                .endDate(postRequestDto.getEndDate())
                .tool(postRequestDto.getTool())
                .workAddress(postRequestDto.getWorkAddress())
                .mainTask(postRequestDto.getMainTask())
                .workCondition(postRequestDto.getWorkCondition())
                .qualification(postRequestDto.getQualification())
                .benefit(postRequestDto.getBenefit())
                .specialSkill(postRequestDto.getSpecialSkill())
                .process(postRequestDto.getProcess())
                .notice(postRequestDto.getNotice())
                .career(postRequestDto.getCareer())
                .jobIdSet(postRequestDto.getJobIdSet())
                .stackIdSet(postRequestDto.getStackIdSet())
                .testStartDate(postRequestDto.getTestStartDate())
                .testEndDate(postRequestDto.getTestEndDate())
                .build();
        log.info("post : {}", post);

        Post savedPost = postRepository.save(post);
        log.info("savedPost : {}", savedPost);
        log.info("savedPost.getId() : {}", savedPost.getId());

        if (savedPost == null) {
            return SuccessApiResponse.builder()
                    .httpStatus(HttpStatus.BAD_REQUEST.value())
                    .message("공고 등록에 실패하였습니다.")
                    .build();
        }

        List<Question> questionList = questions.stream()
                .map(questionRequestDto -> Question.builder()
                        .post(savedPost)
                        .question(questionRequestDto.getQuestion())
                        .build())
                .collect(Collectors.toList());
        log.info("questionList : {}", questionList);

        List<Question> savedQuestions = questionRepository.saveAll(questionList);
        log.info("savedQuestions : {}", savedQuestions);

        if (savedQuestions.isEmpty()) {
            return SuccessApiResponse.builder()
                    .httpStatus(HttpStatus.BAD_REQUEST.value())
                    .message("질문 등록에 실패하였습니다.")
                    .build();
        }

        return SuccessApiResponse.builder()
                .httpStatus(HttpStatus.OK.value())
                .message("공고가 성공적으로 등록되었습니다.")
                .build();
    }

    @Override
    public CommonApiResponse findPostById(Long postId) {
        Optional<Post> post = postRepository.findById(postId);
        if (post.isEmpty()) {
            return SuccessApiResponse.builder()
                    .httpStatus(HttpStatus.NOT_FOUND.value())
                    .message("공고 정보가 없습니다.")
                    .data(Boolean.FALSE)
                    .build();
        }
        log.info("post : {}", post);

        List<Question> questions = questionRepository.findByPostId(postId);
        log.info("questions : {}", questions);

        List<QuestionResponseDto> questionList = questions.stream()
                .map(QuestionResponseDto::new)
                .collect(Collectors.toList());
        log.info("questionList : {}", questionList);

        PostResponseDto responseDto = new PostResponseDto(
                post.get(),
                questionList);

        return SuccessApiResponse.builder()
                .httpStatus(HttpStatus.OK.value())
                .message("SUCCESS")
                .data(responseDto)
                .build();
    }
}

