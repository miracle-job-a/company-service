package com.miracle.companyservice.service;

import com.miracle.companyservice.dto.request.CompanyLoginRequestDto;
import com.miracle.companyservice.dto.request.CompanySignUpRequestDto;
import com.miracle.companyservice.dto.request.PostRequestDto;
import com.miracle.companyservice.dto.request.QuestionDto;
import com.miracle.companyservice.dto.response.CommonApiResponse;
import com.miracle.companyservice.dto.response.CompanyFaqDto;
import com.miracle.companyservice.dto.response.PostCommonDataResponseDto;
import com.miracle.companyservice.dto.response.SuccessApiResponse;
import com.miracle.companyservice.entity.Company;
import com.miracle.companyservice.entity.CompanyFaq;
import com.miracle.companyservice.entity.Post;
import com.miracle.companyservice.entity.Question;
import com.miracle.companyservice.repository.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
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

    public CommonApiResponse checkEmailDuplicated (String email) {
        log.info("email : {}", email);
        if (companyRepository.existsByEmail(email)) {
            return SuccessApiResponse.builder()
                    .httpStatus(HttpStatus.OK.value())
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
        companyRepository.save(new Company(companySignUpRequestDto));
        return SuccessApiResponse.builder()
                .httpStatus(HttpStatus.OK.value())
                .message("회원가입 성공")
                .build();
    }

    public CommonApiResponse loginCompany(CompanyLoginRequestDto companyLoginRequestDto) {
        log.info("email : {}, password : {}", companyLoginRequestDto.getEmail(), companyLoginRequestDto.getPassword().hashCode());
        if (!companyRepository.existsByEmail(companyLoginRequestDto.getEmail())) {
            return SuccessApiResponse.builder()
                    .httpStatus(HttpStatus.OK.value())
                    .message("이메일 또는 비밀번호가 일치하지 않습니다.")
                    .data(Boolean.FALSE)
                    .build();
        }
        Optional<Company> company = companyRepository.findByEmailAndPassword(companyLoginRequestDto.getEmail(), companyLoginRequestDto.getPassword().hashCode());
        if (company.isEmpty()) {
            return SuccessApiResponse.builder()
                    .httpStatus(HttpStatus.OK.value())
                    .message("이메일 또는 비밀번호가 일치하지 않습니다.")
                    .data(Boolean.FALSE)
                    .build();
        }
        if (!bnoRepository.findStatusByBnoIsTrue(company.get().getBno())) {
            return SuccessApiResponse.builder()
                    .httpStatus(HttpStatus.OK.value())
                    .message("만료된 사업자 번호입니다.")
                    .data(Boolean.FALSE)
                    .build();
        }
        return SuccessApiResponse.builder()
                .httpStatus(HttpStatus.OK.value())
                .message("로그인 성공")
                .data(company.get().getId())
                .build();
    }


    public CommonApiResponse checkBnoStatus(String bno) {
        log.info("bno : {}", bno);
        if (!bnoRepository.existsByBno(bno)) {
            return SuccessApiResponse.builder()
                    .httpStatus(HttpStatus.OK.value())
                    .message("존재하지 않는 사업자 번호입니다.")
                    .data(Boolean.FALSE)
                    .build();
        }
        if (!bnoRepository.findStatusByBnoIsTrue(bno)) {
            return SuccessApiResponse.builder()
                    .httpStatus(HttpStatus.OK.value())
                    .message("만료된 사업자 번호입니다.")
                    .data(Boolean.FALSE)
                    .build();
        }
        if (companyRepository.existsByBno(bno)) {
            return SuccessApiResponse.builder()
                    .httpStatus(HttpStatus.OK.value())
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
    public CommonApiResponse getCompanyFaqsByCompanyId(Long companyId){
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
        log.info("faqs : {}",faqs);

        List<CompanyFaqDto> faqList = faqs.stream()
                .map(CompanyFaqDto::new)
                .collect(Collectors.toList());
        log.info("faqList : {}",faqList);

        PostCommonDataResponseDto responseDto = new PostCommonDataResponseDto(
                company.get(),
                faqList);

        return SuccessApiResponse.builder()
                .httpStatus(HttpStatus.OK.value())
                .message("SUCCESS")
                .data(responseDto)
                .build();
    }

/*  일대다 단방향
    @Override
    public CommonApiResponse savePost(PostRequestDto postRequestDto){

        List<String> questions = postRequestDto.getQuestionList();
        log.info("questions : {}",questions);

        List<Question> questionList = questions.stream()
                .map(question -> Question.builder()
                        .question(question)
                        .build())
                .collect(Collectors.toList());
        log.info("questionList : {}",questionList);

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
                .build();
        log.info("post : {}",post);

        post.getQuestionList().addAll(questionList);

        Post savedPost = postRepository.save(post);
        log.info("savedPost : {}",savedPost);
        log.info("savedPost.getId() : {}",savedPost.getId());

        if (savedPost == null) {
            return SuccessApiResponse.builder()
                    .httpStatus(HttpStatus.BAD_REQUEST.value())
                    .message("공고 등록에 실패하였습니다.")
                    .build();
        }


        *//*List<Question> savedQuestions = questionRepository.saveAll(questionList);
        log.info("savedQuestions : {}",savedQuestions);

        if (savedQuestions.isEmpty()) {
            return SuccessApiResponse.builder()
                    .httpStatus(HttpStatus.BAD_REQUEST.value())
                    .message("질문 등록에 실패하였습니다.")
                    .build();
        }*//*

        return SuccessApiResponse.builder()
                .httpStatus(HttpStatus.OK.value())
                .message("공고가 성공적으로 등록되었습니다.")
                .build();
    }*/

    @Override
    public CommonApiResponse savePost(PostRequestDto postRequestDto){

        List<QuestionDto> questions = postRequestDto.getQuestionList();
        log.info("questions : {}",questions);

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
                .build();
        log.info("post : {}",post);

        Post savedPost = postRepository.save(post);
        log.info("savedPost : {}",savedPost);
        log.info("savedPost.getId() : {}",savedPost.getId());

        if (savedPost == null) {
            return SuccessApiResponse.builder()
                    .httpStatus(HttpStatus.BAD_REQUEST.value())
                    .message("공고 등록에 실패하였습니다.")
                    .build();
        }

        Long postId = post.getId();

        List<Question> questionList = questions.stream()
                .map(questionDto -> Question.builder()
                        .post(savedPost)
                        .question(questionDto.getQuestion())
                        .build())
                .collect(Collectors.toList());
        log.info("questionList : {}",questionList);

        List<Question> savedQuestions = questionRepository.saveAll(questionList);
        log.info("savedQuestions : {}",savedQuestions);

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
}

