package com.miracle.companyservice.service;

import com.miracle.companyservice.dto.request.*;
import com.miracle.companyservice.dto.response.*;
import com.miracle.companyservice.entity.Company;
import com.miracle.companyservice.entity.CompanyFaq;
import com.miracle.companyservice.entity.Post;
import com.miracle.companyservice.entity.Question;
import com.miracle.companyservice.repository.*;
import com.miracle.companyservice.util.encryptor.Encryptors;
import com.miracle.companyservice.util.specification.PostSpecifications;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
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
    private final Encryptors encryptors;

    @Autowired
    public CompanyServiceImpl(CompanyRepository companyRepository, CompanyFaqRepository companyFaqRepository, BnoRepository bnoRepository, PostRepository postRepository, QuestionRepository questionRepository, Encryptors encryptors) {
        this.companyRepository = companyRepository;
        this.companyFaqRepository = companyFaqRepository;
        this.bnoRepository = bnoRepository;
        this.postRepository = postRepository;
        this.questionRepository = questionRepository;
        this.encryptors = encryptors;
    }

    @Override
    public Boolean companyValidation(Long id, String email, String bno) {
        return companyRepository.existsByIdAndEmailAndBno(id, encryptors.encryptAES(email, encryptors.getSecretKey()), bno);
    }

    @Override
    public CommonApiResponse checkEmailDuplicated(String email) {
        log.info("email : {}", email);
        if (companyRepository.existsByEmail(encryptors.encryptAES(email, encryptors.getSecretKey()))) {
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

    @Override
    public CommonApiResponse signUpCompany(CompanySignUpRequestDto companySignUpRequestDto) {
        log.info("companySignUpRequestDto : {}", companySignUpRequestDto);
        if (companyRepository.existsByBno(companySignUpRequestDto.getBno())) {
            return SuccessApiResponse.builder()
                    .httpStatus(HttpStatus.BAD_REQUEST.value())
                    .message("이미 가입된 사업자번호 입니다.")
                    .data(Boolean.FALSE)
                    .build();
        }

        if (companyRepository.existsByEmail(encryptors.encryptAES(companySignUpRequestDto.getEmail(), encryptors.getSecretKey()))) {
            return SuccessApiResponse.builder()
                    .httpStatus(HttpStatus.BAD_REQUEST.value())
                    .message("중복된 이메일입니다.")
                    .data(Boolean.FALSE)
                    .build();
        }
        Company company = new Company(companySignUpRequestDto);
        company.setEmail(encryptors.encryptAES(companySignUpRequestDto.getEmail(), encryptors.getSecretKey()));
        company.setPassword(encryptors.SHA3Algorithm(companySignUpRequestDto.getPassword()));
        companyRepository.save(company);
        return SuccessApiResponse.builder()
                .httpStatus(HttpStatus.OK.value())
                .message("회원가입 성공")
                .data(Boolean.TRUE)
                .build();
    }

    @Override
    public CommonApiResponse loginCompany(CompanyLoginRequestDto companyLoginRequestDto) {
        log.info("email : {}, password : {}", companyLoginRequestDto.getEmail(), companyLoginRequestDto.getPassword());
        if (!companyRepository.existsByEmail(encryptors.encryptAES(companyLoginRequestDto.getEmail(), encryptors.getSecretKey()))) {
            return SuccessApiResponse.builder()
                    .httpStatus(HttpStatus.BAD_REQUEST.value())
                    .message("이메일 또는 비밀번호가 일치하지 않습니다.")
                    .data(Boolean.FALSE)
                    .build();
        }
        Optional<Company> company = companyRepository.findByEmailAndPassword(encryptors.encryptAES(companyLoginRequestDto.getEmail(), encryptors.getSecretKey()), encryptors.SHA3Algorithm(companyLoginRequestDto.getPassword()));
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
        CompanyLoginResponseDto responseDto = new CompanyLoginResponseDto(company.get().getId(), encryptors.decryptAES(company.get().getEmail(), encryptors.getSecretKey()), company.get().getBno());
        return SuccessApiResponse.builder()
                .httpStatus(HttpStatus.OK.value())
                .message("로그인 성공")
                .data(responseDto)
                .build();
    }

    @Override
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

    @Override
    public CommonApiResponse postForMainPage() {
        List<Post> newestResult = postRepository.findAllByClosedFalseAndDeletedFalseOrderByCreatedAtDesc(PageRequest.of(0, 3));
        List<MainPagePostsResponseDto> newest = new ArrayList<>();
        newestResult.iterator().forEachRemaining((Post p) -> {
            String photo = companyRepository.findPhotoById(p.getCompanyId());
            newest.add(new MainPagePostsResponseDto(p, photo));
        });

        List<Post> deadlineResult = postRepository.findTop3ByEndDateOrderByEndDateAsc(PageRequest.of(0, 3));
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

    public CommonApiResponse addFaq(long companyId, CompanyFaqRequestDto companyFaqRequestDto) {
        Optional<Company> company = companyRepository.findById(companyId);
        if (company.isEmpty()) {
            return SuccessApiResponse.builder()
                    .httpStatus(HttpStatus.BAD_REQUEST.value())
                    .message("존재하지 않는 companyId 입니다.")
                    .data(Boolean.FALSE)
                    .build();
        }

        Long count = companyFaqRepository.countByCompanyId(companyId);
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

    @Override
    public CommonApiResponse deleteFaq(Long companyId, Long faqId) {
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

    @Override
    public CommonApiResponse getFaq(Long companyId) {
        if (!companyRepository.existsById(companyId)) {
            return SuccessApiResponse.builder()
                    .httpStatus(HttpStatus.BAD_REQUEST.value())
                    .message("존재하지 않는 companyId 입니다.")
                    .data(Boolean.FALSE)
                    .build();
        }
        List<CompanyFaq> faqList = companyFaqRepository.findByCompanyId(companyId);
        List<CompanyFaqResponseDto> faqDtoList = new ArrayList<>();

        faqList.iterator().forEachRemaining((CompanyFaq c) ->
                faqDtoList.add(new CompanyFaqResponseDto(c.getId(), c.getQuestion(), c.getAnswer())));
        return SuccessApiResponse.builder()
                .httpStatus(HttpStatus.OK.value())
                .message("FAQ 조회 성공")
                .data(faqDtoList)
                .build();
    }

    @Override
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
        questionList.iterator().forEachRemaining((Question q) ->
                questionResponseDtoList.add(new QuestionResponseDto(q)));

        return SuccessApiResponse.builder()
                .httpStatus(HttpStatus.OK.value())
                .message("자기소개서 질문 조회 성공")
                .data(questionResponseDtoList)
                .build();
    }

    @Override
    public CommonApiResponse getCountPosts(Long companyId) {
        Long countAllPosts = postRepository.countByCompanyIdAndDeletedFalse(companyId);
        Long countEndedPosts = postRepository.countByCompanyIdAndClosedTrueAndDeletedFalse(companyId);
        Long countOpen = postRepository.countByCompanyIdAndClosedFalseAndDeletedFalse(companyId);

        Map<String, Long> map = new HashMap<>();
        map.put("countAllPosts", countAllPosts);
        map.put("countEndedPosts", countEndedPosts);
        map.put("countOpen", countOpen);

        return SuccessApiResponse.builder()
                .httpStatus(HttpStatus.OK.value())
                .message("공고 수 조회 완료")
                .data(map)
                .build();
    }

    @Override
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

    public CommonApiResponse managePost(Long companyId, int strNum, int endNum, String sort) {
        List<Page<ManagePostsResponseDto>> result = new ArrayList<>();
        if (sort.equals("deadline")) {
            for (int i = strNum - 1; i < endNum; i++) {
                Page<ManagePostsResponseDto> getDeadline = postRepository.findAllByCompanyIdOrderByDeadline(companyId, PageRequest.of(i, 9))
                        .map(ManagePostsResponseDto::new);
                result.add(getDeadline);
            }
            return SuccessApiResponse.builder()
                    .httpStatus(HttpStatus.OK.value())
                    .message("마감 임박 공고 정렬")
                    .data(result)
                    .build();
        }

        if (sort.equals("close")) {
            for (int i = strNum - 1; i < endNum; i++) {
                Page<ManagePostsResponseDto> getClose = postRepository.findAllByCompanyIdOrderByClose(companyId, PageRequest.of(i, 9))
                        .map(ManagePostsResponseDto::new);
                result.add(getClose);
            }
            return SuccessApiResponse.builder()
                    .httpStatus(HttpStatus.OK.value())
                    .message("마감 공고만 보기")
                    .data(result)
                    .build();
        }
        if (sort.equals("open")) {
            for (int i = strNum - 1; i < endNum; i++) {
                Page<ManagePostsResponseDto> getOpen = postRepository.findAllByCompanyIdOrderByClose(companyId, PageRequest.of(i, 9))
                        .map(ManagePostsResponseDto::new);
                result.add(getOpen);
            }
            return SuccessApiResponse.builder()
                    .httpStatus(HttpStatus.OK.value())
                    .message("진행 중 공고만 보기")
                    .data(result)
                    .build();
        }
        // sort = lastest 디폴트 최신순 정렬
        for (int i = strNum - 1; i < endNum; i++) {
            Page<ManagePostsResponseDto> getLatest = postRepository.findAllByCompanyIdOrderByDeadline(companyId, PageRequest.of(i, 9))
                    .map(ManagePostsResponseDto::new);
            result.add(getLatest);
        }
        return SuccessApiResponse.builder()
                .httpStatus(HttpStatus.OK.value())
                .message("최신 공고 정렬")
                .data(result)
                .build();
    }

    public CommonApiResponse conditionalSearch(int strNum, int endNum, ConditionalSearchPostRequestDto conditionalSearchRequestDto) {
        List<Page<ConditionalSearchPostResponseDto>> searchList = new ArrayList<>();
        // 주소 값이 빈 경우 검색이 될 수 있도록 빈 값을(" ") 넣어줍니다.
        if (conditionalSearchRequestDto.getAddressSet().isEmpty()) {
            conditionalSearchRequestDto.getAddressSet().add(" ");
        }

        // 마감된 공고 미포함
        if (!conditionalSearchRequestDto.getIncludeEnded()) {
            Specification<Post> finalSpec = Specification
                    .where(PostSpecifications.notClosed())
                    .and(PostSpecifications.notDeleted())
                    .and(PostSpecifications.distinctById())
                    .and(PostSpecifications.workAddressLike(conditionalSearchRequestDto.getAddressSet()))
                    .and(PostSpecifications.careerGreaterThanOrEqual(conditionalSearchRequestDto.getCareer()))
                    .and(PostSpecifications.jobIdIn(conditionalSearchRequestDto.getJobIdSet()))
                    .and(PostSpecifications.stackIdIn(conditionalSearchRequestDto.getStackIdSet()))
                    .and(PostSpecifications.orderByCreatedAtDesc());
            for (int i = strNum - 1; i < endNum; i++) {
                Page<ConditionalSearchPostResponseDto> postPage = postRepository.findAll(finalSpec, PageRequest.of(i, 9))
                        .map(ConditionalSearchPostResponseDto::new);
                searchList.add(postPage);
            }
            return SuccessApiResponse.builder()
                    .httpStatus(HttpStatus.OK.value())
                    .message("공고 상세 검색 완료")
                    .data(searchList)
                    .build();
        }
        // 마감된 공고 포함
        Specification<Post> finalSpec = Specification
                .where(PostSpecifications.notDeleted())
                .and(PostSpecifications.distinctById())
                .and(PostSpecifications.workAddressLike(conditionalSearchRequestDto.getAddressSet()))
                .and(PostSpecifications.careerGreaterThanOrEqual(conditionalSearchRequestDto.getCareer()))
                .and(PostSpecifications.jobIdIn(conditionalSearchRequestDto.getJobIdSet()))
                .and(PostSpecifications.stackIdIn(conditionalSearchRequestDto.getStackIdSet()))
                .and(PostSpecifications.orderByCreatedAtDesc());

        for (int i = strNum - 1; i < endNum; i++) {
            Page<ConditionalSearchPostResponseDto> postPage = postRepository.findAll(finalSpec, PageRequest.of(i, 9))
                    .map(ConditionalSearchPostResponseDto::new);
            searchList.add(postPage);
        }
        return SuccessApiResponse.builder()
                .httpStatus(HttpStatus.OK.value())
                .message("공고 상세 검색 완료")
                .data(searchList)
                .build();
    }

    @Override
    public CommonApiResponse getCompanyList(int strNum, int endNum, boolean today) {
        List<Page<CompanyListForAdminResponseDto>> companyList = new ArrayList<>();
        if (today) {
            for (int i = strNum - 1; i < endNum; i++) {
                LocalDateTime todayTrue = LocalDateTime.of(LocalDate.now(), LocalTime.MIDNIGHT);
                Page<CompanyListForAdminResponseDto> createdAt = companyRepository.findAllByCreatedAt(todayTrue, PageRequest.of(i, 9, Sort.by("createdAt").descending()))
                        .map((Company c) -> new CompanyListForAdminResponseDto(c, encryptors.decryptAES(c.getEmail(), encryptors.getSecretKey()), bnoRepository.findStatusByBnoIsTrue(c.getBno())));
                companyList.add(createdAt);
            }
            return SuccessApiResponse.builder()
                    .httpStatus(HttpStatus.OK.value())
                    .message("기업 목록 조회 성공")
                    .data(companyList)
                    .build();
        }
        for (int i = strNum - 1; i < endNum; i++) {
            LocalDateTime todayFalse = LocalDateTime.of(1945, 8, 15, 0, 0);
            Page<CompanyListForAdminResponseDto> createdAt = companyRepository.findAllByCreatedAt(todayFalse, PageRequest.of(i, 9, Sort.by("createdAt").descending()))
                    .map((Company c) -> new CompanyListForAdminResponseDto(c, encryptors.decryptAES(c.getEmail(), encryptors.getSecretKey()), bnoRepository.findStatusByBnoIsTrue(c.getBno())));
            companyList.add(createdAt);
        }
        return SuccessApiResponse.builder()
                .httpStatus(HttpStatus.OK.value())
                .message("기업 목록 조회 성공")
                .data(companyList)
                .build();
    }

    @Override
    public CommonApiResponse quitCompany(Long companyId) {
        companyRepository.deleteById(companyId);
        return SuccessApiResponse.builder()
                .httpStatus(HttpStatus.OK.value())
                .message("탈퇴 성공")
                .data(Boolean.TRUE)
                .build();
    }

    @Override
    public CommonApiResponse approveCompany(Long companyId) {
        Optional<Company> company = companyRepository.findById(companyId);
        if (company.isEmpty()) {
            return SuccessApiResponse.builder()
                    .httpStatus(HttpStatus.BAD_REQUEST.value())
                    .message("존재하지 않는 companyId 입니다.")
                    .data(Boolean.FALSE)
                    .build();
        }
        Company company1 = company.get();
        if (!bnoRepository.findStatusByBnoIsTrue(company1.getBno())) {
            return SuccessApiResponse.builder()
                    .httpStatus(HttpStatus.BAD_REQUEST.value())
                    .message("만료된 사업자 번호 입니다.")
                    .data(Boolean.FALSE)
                    .build();
        }
        company1.setApproveStatus(true);
        return SuccessApiResponse.builder()
                .httpStatus(HttpStatus.OK.value())
                .message("기업 회원 가입이 승인되었습니다.")
                .data(Boolean.TRUE)
                .build();
    }

    @Override
    public CommonApiResponse getCompanyInfoAndFaqs(Long companyId) {
        Optional<Company> company = companyRepository.findById(companyId);
        if (company.isEmpty()) {
            return SuccessApiResponse.builder()
                    .httpStatus(HttpStatus.BAD_REQUEST.value())
                    .message("해당 기업에 대한 정보가 없습니다.")
                    .data(Boolean.FALSE)
                    .build();
        }

        List<CompanyFaq> faqs = companyFaqRepository.findByCompanyId(companyId);
        if (faqs == null) {
            return SuccessApiResponse.builder()
                    .httpStatus(HttpStatus.BAD_REQUEST.value())
                    .message("해당 기업의 자주 묻는 질문 정보가 없습니다.")
                    .data(Boolean.FALSE)
                    .build();
        }

        List<CompanyFaqResponseDto> faqList = faqs.stream()
                .map(companyFaq -> new CompanyFaqResponseDto(companyFaq))
                .collect(Collectors.toList());

        PostCommonDataResponseDto responseDto = new PostCommonDataResponseDto(
                company.get(),
                faqList);

        return SuccessApiResponse.builder()
                .httpStatus(HttpStatus.OK.value())
                .message("기업 정보 및 자주 묻는 질문 조회 성공")
                .data(responseDto)
                .build();
    }

    @Override
    public CommonApiResponse savePost(Long companyId, PostRequestDto postRequestDto) {

        List<QuestionRequestDto> questions = postRequestDto.getQuestionList();

        Post post = Post.builder()
                .companyId(companyId)
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

        Post savedPost = postRepository.save(post);
        if (savedPost == null) {
            return SuccessApiResponse.builder()
                    .httpStatus(HttpStatus.BAD_REQUEST.value())
                    .message("공고 등록에 실패하였습니다.")
                    .data(Boolean.FALSE)
                    .build();
        }

        List<Question> questionList = questions.stream()
                .map(questionRequestDto -> Question.builder()
                        .post(savedPost)
                        .question(questionRequestDto.getQuestion())
                        .build())
                .collect(Collectors.toList());

        List<Question> savedQuestions = questionRepository.saveAll(questionList);
        if (savedQuestions.isEmpty()) {
            return SuccessApiResponse.builder()
                    .httpStatus(HttpStatus.BAD_REQUEST.value())
                    .message("자기소개서 문항 등록에 실패하였습니다.")
                    .data(Boolean.FALSE)
                    .build();
        }

        return SuccessApiResponse.builder()
                .httpStatus(HttpStatus.OK.value())
                .message("공고가 성공적으로 등록되었습니다.")
                .data(Boolean.TRUE)
                .build();
    }

    @Override
    public CommonApiResponse findPost(Long postId) {
        Optional<Post> post = postRepository.findById(postId);
        if (post.isEmpty()) {
            return SuccessApiResponse.builder()
                    .httpStatus(HttpStatus.BAD_REQUEST.value())
                    .message("해당 공고 정보가 없습니다.")
                    .data(Boolean.FALSE)
                    .build();
        }

        List<Question> questions = questionRepository.findByPostId(postId);
        if (questions == null) {
            return SuccessApiResponse.builder()
                    .httpStatus(HttpStatus.BAD_REQUEST.value())
                    .message("해당 공고에 대한 자기소개서 문항이 존재하지 않습니다.")
                    .data(Boolean.FALSE)
                    .build();
        }
        List<QuestionResponseDto> questionList = questions.stream()
                .map(QuestionResponseDto::new)
                .collect(Collectors.toList());

        PostResponseDto responseDto = new PostResponseDto(
                post.get(),
                questionList);

        return SuccessApiResponse.builder()
                .httpStatus(HttpStatus.OK.value())
                .message("해당 공고 데이터 조회 성공")
                .data(responseDto)
                .build();
    }

    @Override
    public CommonApiResponse modifyPost(Long companyId, Long postId, PostRequestDto postRequestDto) {

        Post post = Post.builder()
                .id(postId)
                .companyId(companyId)
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

        Post modifiedPost = postRepository.save(post);
        if (modifiedPost == null) {
            return SuccessApiResponse.builder()
                    .httpStatus(HttpStatus.BAD_REQUEST.value())
                    .message("공고 수정에 실패하였습니다.")
                    .data(Boolean.FALSE)
                    .build();
        }

        List<Question> modifiedQuestions = new ArrayList<>();

        List<QuestionRequestDto> questionRequestDtos = postRequestDto.getQuestionList();
        if (questionRequestDtos != null) {
            for (QuestionRequestDto questionRequestDto : questionRequestDtos) {
                Optional<Question> optionalQuestion = questionRepository.findByIdAndPostId(questionRequestDto.getId(), modifiedPost.getId());
                if (optionalQuestion.isPresent()) {
                    Question question = optionalQuestion.get();
                    Question updatedQuestion = question.toBuilder()
                            .question(questionRequestDto.getQuestion())
                            .build();
                    Question savedQuestion = questionRepository.save(updatedQuestion);
                    modifiedQuestions.add(savedQuestion);
                } else {
                    Question question = Question.builder()
                            .question(questionRequestDto.getQuestion())
                            .post(modifiedPost)
                            .build();
                    Question savedQuestion = questionRepository.save(question);
                    modifiedQuestions.add(savedQuestion);
                }
            }

            if (modifiedQuestions.contains(null)) {
                return SuccessApiResponse.builder()
                        .httpStatus(HttpStatus.BAD_REQUEST.value())
                        .message("자소서 문항 수정에 실패하였습니다.")
                        .data(Boolean.FALSE)
                        .build();
            }
        }

        return SuccessApiResponse.builder()
                .httpStatus(HttpStatus.OK.value())
                .message("공고가 성공적으로 수정되었습니다.")
                .data(Boolean.TRUE)
                .build();
    }

    @Override
    public CommonApiResponse deletePost(Long postId) {
        Optional<Post> post = postRepository.findById(postId);
        if (post.isPresent()) {
            post.get().setDeleted(true);
            postRepository.save(post.get());
            return SuccessApiResponse.builder()
                    .httpStatus(HttpStatus.OK.value())
                    .message("공고가 성공적으로 삭제되었습니다.")
                    .data(Boolean.TRUE)
                    .build();
        } else {
            return SuccessApiResponse.builder()
                    .httpStatus(HttpStatus.BAD_REQUEST.value())
                    .message("공고 삭제 실패")
                    .data(Boolean.FALSE)
                    .build();
        }
    }

    @Override
    public CommonApiResponse findCompany(Long companyId) {
        Optional<Company> company = companyRepository.findById(companyId);
        if (company.isEmpty()) {
            return SuccessApiResponse.builder()
                    .httpStatus(HttpStatus.BAD_REQUEST.value())
                    .message("해당 기업에 대한 정보가 없습니다.")
                    .data(Boolean.FALSE)
                    .build();
        }

        Boolean bnoStatus = bnoRepository.findStatusByBnoIsTrue(company.get().getBno());
        if (bnoStatus == false) {
            return SuccessApiResponse.builder()
                    .httpStatus(HttpStatus.BAD_REQUEST.value())
                    .message("bno가 만료되었습니다.")
                    .data(Boolean.FALSE)
                    .build();
        }

        Long countOpen = postRepository.countByCompanyIdAndClosedFalseAndDeletedFalse(companyId);
        if (countOpen == null) {
            return SuccessApiResponse.builder()
                    .httpStatus(HttpStatus.BAD_REQUEST.value())
                    .message("진행 중인 공고 수 조회에 실패하였습니다.")
                    .data(Boolean.FALSE)
                    .build();
        }

        CompanyPageResponseDto responseDto = new CompanyPageResponseDto(
                company.get(), bnoStatus, countOpen);

        return SuccessApiResponse.builder()
                .httpStatus(HttpStatus.OK.value())
                .message("기업 상세페이지 정보 조회 성공")
                .data(responseDto)
                .build();
    }

    @Override
    public CommonApiResponse findPostAndCompany(String keyword, int strNum, int endNum) {
        String likeKeyword = "%" + keyword + "%";
        int page = strNum;
        List<Page<PostListResponseDto>> postList = new ArrayList<>();
        List<Page<CompanyListResponseDto>> companyList = new ArrayList<>();


        while (page < endNum) {
            Page<PostListResponseDto> postPageResult = postRepository.findByKeyword(likeKeyword, PageRequest.of(page, 9));
            if (postPageResult.isEmpty()) {
                break;
            }
            postList.add(postPageResult);

            Page<CompanyListResponseDto> companyPageResult = companyRepository.findByKeyword(likeKeyword, PageRequest.of(page, 9));
            if (companyPageResult.isEmpty()) {
                break;
            }
            companyList.add(companyPageResult);

            page++;
        }


        Map<String, Object> data = new HashMap<>();

        data.put("postList", postList);
        data.put("companyList", companyList);

        if (data == null) {
            return SuccessApiResponse.builder()
                    .httpStatus(HttpStatus.BAD_REQUEST.value())
                    .message("공고/기업 검색 조회에 실패하였습니다.")
                    .data(Boolean.FALSE)
                    .build();
        }

        return SuccessApiResponse.builder()
                .httpStatus(HttpStatus.OK.value())
                .message("검색 키워드를 기반으로 한 데이터 조회 성공")
                .data(data)
                .build();
    }

    @Override
    public CommonApiResponse modifyCompanyInfo(Long companyId, CompanyInfoRequestDto requestDto) {
        String encryptedPwd = encryptors.SHA3Algorithm(requestDto.getPwd());

        Optional<Company> company = companyRepository.findById(companyId);
        if(company.isEmpty()){
            return SuccessApiResponse.builder()
                    .httpStatus(HttpStatus.BAD_REQUEST.value())
                    .message("해당 회사를 찾을 수 없습니다.")
                    .data(Boolean.FALSE)
                    .build();
        }

        Company existingCompany = company.get();

        existingCompany.setName(requestDto.getName());
        existingCompany.setCeoName(requestDto.getCeoName());
        existingCompany.setEmployeeNum(requestDto.getEmployeeNum());
        existingCompany.setSector(requestDto.getSector());
        existingCompany.setPhoto(requestDto.getPhoto());
        existingCompany.setIntroduction(requestDto.getIntroduction());
        existingCompany.setAddress(requestDto.getAddress());

        if (encryptedPwd.equals(existingCompany.getPassword())) {
            return SuccessApiResponse.builder()
                        .httpStatus(HttpStatus.BAD_REQUEST.value())
                        .message("현재 비밀번호와 동일하게 변경할 수 없습니다.")
                        .data(Boolean.FALSE)
                        .build();
        } else{
            existingCompany.setPassword(encryptedPwd);
        }

        Company modifiedCompanyInfo = companyRepository.save(existingCompany);
        if(!requestDto.getName().equals(modifiedCompanyInfo.getName())){
            return SuccessApiResponse.builder()
                        .httpStatus(HttpStatus.BAD_REQUEST.value())
                        .message("기업 회원 정보 수정에 실패하였습니다.")
                        .data(Boolean.FALSE)
                        .build();

        }else if(!requestDto.getCeoName().equals(modifiedCompanyInfo.getCeoName())){
            return SuccessApiResponse.builder()
                    .httpStatus(HttpStatus.BAD_REQUEST.value())
                    .message("기업 회원 정보 수정에 실패하였습니다.")
                    .data(Boolean.FALSE)
                    .build();

        }else if(requestDto.getEmployeeNum() != modifiedCompanyInfo.getEmployeeNum()){
            return SuccessApiResponse.builder()
                    .httpStatus(HttpStatus.BAD_REQUEST.value())
                    .message("기업 회원 정보 수정에 실패하였습니다.")
                    .data(Boolean.FALSE)
                    .build();

        }else if(!requestDto.getSector().equals(modifiedCompanyInfo.getSector())){
            return SuccessApiResponse.builder()
                 .httpStatus(HttpStatus.BAD_REQUEST.value())
                 .message("업종 수정에 실패하였습니다.")
                 .data(Boolean.FALSE)
                 .build();

        }else if(!requestDto.getPhoto().equals(modifiedCompanyInfo.getPhoto())){
            return SuccessApiResponse.builder()
                .httpStatus(HttpStatus.BAD_REQUEST.value())
                .message("사진 변경에 실패하였습니다.")
                .data(Boolean.FALSE)
                .build();

        }else if(!requestDto.getIntroduction().equals(modifiedCompanyInfo.getIntroduction())){
            return SuccessApiResponse.builder()
                .httpStatus(HttpStatus.BAD_REQUEST.value())
                .message("기업 소개 수정에 실패하였습니다.")
                .data(Boolean.FALSE)
                .build();

        }else if(!requestDto.getAddress().equals(modifiedCompanyInfo.getAddress())){
            return SuccessApiResponse.builder()
                .httpStatus(HttpStatus.BAD_REQUEST.value())
                .message("주소 변경에 실패하였습니다.")
                .data(Boolean.FALSE)
                .build();

        }else if(!encryptedPwd.equals(modifiedCompanyInfo.getPassword())){
            return SuccessApiResponse.builder()
                .httpStatus(HttpStatus.BAD_REQUEST.value())
                .message("비밀번호 변경에 실패하였습니다.")
                .data(Boolean.FALSE)
                .build();
               }

        return SuccessApiResponse.builder()
                .httpStatus(HttpStatus.OK.value())
                .message("기업 회원 정보가 성공적으로 수정되었습니다.")
                .data(Boolean.TRUE)
                .build();
    }

    @Override
    public CommonApiResponse userCheck(Long companyId, CompanyLoginRequestDto companyLoginRequestDto) {
        String email = encryptors.encryptAES(companyLoginRequestDto.getEmail(), encryptors.getSecretKey());
        String pwd = encryptors.SHA3Algorithm(companyLoginRequestDto.getPassword());

        Optional<Company> company = companyRepository.findEmailAndPasswordById(companyId);

        if (company.isEmpty() || !email.equals(company.get().getEmail()) || !pwd.equals(company.get().getPassword())) {
            return SuccessApiResponse.builder()
                    .httpStatus(HttpStatus.BAD_REQUEST.value())
                    .message("이메일 또는 비밀번호가 일치하지 않습니다.")
                    .data(Boolean.FALSE)
                    .build();
        }

        return SuccessApiResponse.builder()
                .httpStatus(HttpStatus.OK.value())
                .message("계정 확인 성공")
                .data(Boolean.TRUE)
                .build();
    }
}