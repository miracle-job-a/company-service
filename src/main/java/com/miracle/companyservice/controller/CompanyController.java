package com.miracle.companyservice.controller;

import com.miracle.companyservice.controller.swagger.*;
import com.miracle.companyservice.dto.request.*;
import com.miracle.companyservice.dto.response.CommonApiResponse;
import com.miracle.companyservice.service.CompanyService;
import com.miracle.companyservice.service.CompanyServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping("/v1/company")
public class CompanyController {

    private final CompanyService companyService;

    @Autowired
    public CompanyController(CompanyServiceImpl companyServiceImpl) {
        this.companyService = companyServiceImpl;
    }

    @ApiCheckEmail
    @ApiDefault
    @PostMapping("/email")
    public CommonApiResponse checkEmail(@Valid @RequestBody CompanyCheckEmailRequestDto companyCheckEmailRequestDto, HttpServletResponse response) {
        CommonApiResponse commonApiResponse = companyService.checkEmailDuplicated(companyCheckEmailRequestDto.getEmail());
        response.setStatus(commonApiResponse.getHttpStatus());
        return commonApiResponse;
    }

    @ApiCheckBno
    @ApiDefault
    @PostMapping("/bno")
    public CommonApiResponse checkBno(@Valid @RequestBody CompanyCheckBnoRequestDto companyCheckBnoRequestDto, HttpServletResponse response) {
        CommonApiResponse commonApiResponse = companyService.checkBnoStatus(companyCheckBnoRequestDto.getBno());
        response.setStatus(commonApiResponse.getHttpStatus());
        return commonApiResponse;
    }

    @ApiSignUp
    @ApiDefault
    @PostMapping("/signup")
    public CommonApiResponse signUpCompany(@Valid @RequestBody CompanySignUpRequestDto companySignUpRequestDto, HttpServletResponse response) {
        CommonApiResponse commonApiResponse = companyService.signUpCompany(companySignUpRequestDto);
        response.setStatus(commonApiResponse.getHttpStatus());
        return commonApiResponse;
    }

    @ApiLogin
    @ApiDefault
    @PostMapping("/login")
    public CommonApiResponse loginCompany(@Valid @RequestBody CompanyLoginRequestDto companyLoginRequestDto, HttpServletResponse response) {
        CommonApiResponse commonApiResponse = companyService.loginCompany(companyLoginRequestDto);
        response.setStatus(commonApiResponse.getHttpStatus());
        return commonApiResponse;
    }

    @ApiPostMain
    @ApiDefault
    @GetMapping("/main")
    public CommonApiResponse postForMainPage(HttpServletResponse response) {
        response.setStatus(HttpStatus.OK.value());
        return companyService.postForMainPage();
    }

    @ApiAddFaq
    @ApiInterceptor
    @PostMapping("/{companyId}/faq")
    public CommonApiResponse addFaq(@PathVariable Long companyId, @Valid @RequestBody CompanyFaqRequestDto companyFaqRequestDto, HttpServletResponse response) {
        CommonApiResponse commonApiResponse = companyService.addFaq(companyId, companyFaqRequestDto);
        response.setStatus(commonApiResponse.getHttpStatus());
        return commonApiResponse;
    }

    @ApiDeleteFaq
    @ApiInterceptor
    @DeleteMapping("/{companyId}/faqs/{faqId}")
    public CommonApiResponse deleteFaq(@PathVariable Long companyId, @PathVariable Long faqId, HttpServletRequest request, HttpServletResponse response) {
        CommonApiResponse commonApiResponse = companyService.deleteFaq(companyId, faqId);
        response.setStatus(commonApiResponse.getHttpStatus());
        return commonApiResponse;
    }

    @ApiGetFaq
    @ApiDefault
    @GetMapping("/{companyId}/faqs")
    public CommonApiResponse getFaq(@PathVariable Long companyId, HttpServletResponse response) {
        CommonApiResponse commonApiResponse = companyService.getFaq(companyId);
        response.setStatus(commonApiResponse.getHttpStatus());
        return commonApiResponse;
    }

    @ApiReturnQuestions
    @ApiDefault
    @GetMapping("/{companyId}/posts/{postId}/questions")
    public CommonApiResponse returnQuestions(@PathVariable Long companyId, @PathVariable Long postId, HttpServletResponse response) {
        CommonApiResponse commonApiResponse = companyService.returnQuestions(companyId, postId);
        response.setStatus(commonApiResponse.getHttpStatus());
        return commonApiResponse;
    }

    @ApiCountPosts
    @ApiDefault
    @GetMapping("/{companyId}/posts")
    public CommonApiResponse countPosts(@PathVariable Long companyId, HttpServletResponse response) {
        CommonApiResponse commonApiResponse = companyService.getCountPosts(companyId);
        response.setStatus(commonApiResponse.getHttpStatus());
        return commonApiResponse;
    }

    @ApiChangeToClose
    @ApiInterceptor
    @GetMapping("/{companyId}/posts/{postId}/close")
    public CommonApiResponse changeToClose(@PathVariable Long companyId, @PathVariable Long postId, HttpServletResponse response) {
        CommonApiResponse commonApiResponse = companyService.changeToClose(companyId, postId);
        response.setStatus(commonApiResponse.getHttpStatus());
        return commonApiResponse;
    }
    @ApiGetLatestPosts
    @ApiDefault
    @GetMapping("/{companyId}/posts/latest")
    public CommonApiResponse getLatestPosts(@PathVariable Long companyId, HttpServletResponse response) {
        CommonApiResponse commonApiResponse = companyService.getLatestPosts(companyId);
        response.setStatus(commonApiResponse.getHttpStatus());
        return commonApiResponse;
    }

    @ApiGetDeadlinePosts
    @ApiDefault
    @GetMapping("/{companyId}/posts/deadline")
    public CommonApiResponse getDeadlinePosts(@PathVariable Long companyId, HttpServletResponse response) {
        CommonApiResponse commonApiResponse = companyService.getDeadlinePosts(companyId);
        response.setStatus(commonApiResponse.getHttpStatus());
        return commonApiResponse;
    }

    @ApiGetEndPosts
    @ApiDefault
    @GetMapping("/{companyId}/posts/end")
    public CommonApiResponse getEndPosts(@PathVariable Long companyId, HttpServletResponse response) {
        CommonApiResponse commonApiResponse = companyService.getEndPosts(companyId);
        response.setStatus(commonApiResponse.getHttpStatus());
        return commonApiResponse;
    }

    @ApiGetOpenPosts
    @ApiDefault
    @GetMapping("/{companyId}/posts/open")
    public CommonApiResponse getOpenPosts(@PathVariable Long companyId, HttpServletResponse response) {
        CommonApiResponse commonApiResponse = companyService.getOpenPosts(companyId);
        response.setStatus(commonApiResponse.getHttpStatus());
        return commonApiResponse;
    }

    @ApiFindCompanyInfo
    @ApiDefault
    @GetMapping("/{companyId}/info")
    public CommonApiResponse findCompanyInfo(@PathVariable Long companyId, HttpServletResponse response) {
        CommonApiResponse commonApiResponse = companyService.getCompanyInfoAndFaqsByCompanyId(companyId);
        response.setStatus(commonApiResponse.getHttpStatus());
        return commonApiResponse;
    }

    @ApiRegisterPost
    @ApiInterceptor
    @PostMapping("{companyId}/post")
    public CommonApiResponse registerPost(@RequestBody PostRequestDto postRequestDto, HttpServletResponse response) {
        CommonApiResponse commonApiResponse = companyService.savePost(postRequestDto);
        response.setStatus(commonApiResponse.getHttpStatus());
        return commonApiResponse;
    }

    @ApiGetPost
    @ApiDefault
    @GetMapping("{companyId}/posts/{postId}")
    public CommonApiResponse getPost(@PathVariable Long postId, HttpServletResponse response){
        CommonApiResponse commonApiResponse = companyService.findPostById(postId);
        response.setStatus(commonApiResponse.getHttpStatus());
        return commonApiResponse;
    }

    @ApiModifyPost
    @ApiInterceptor
    @PutMapping("{companyId}/posts/{postId}")
    public CommonApiResponse modifyPost(@RequestBody PostRequestDto postRequestDto, HttpServletResponse response){
        CommonApiResponse commonApiResponse = companyService.modifyPostById(postRequestDto);
        response.setStatus(commonApiResponse.getHttpStatus());
        return commonApiResponse;
    }

    @ApiDeletePost
    @ApiInterceptor
    @DeleteMapping("{companyId}/posts/{postId}")
    public CommonApiResponse deletePost(@PathVariable Long postId, HttpServletResponse response){
        CommonApiResponse commonApiResponse = companyService.deletePostById(postId);
        response.setStatus(commonApiResponse.getHttpStatus());
        return commonApiResponse;
    }

    @ApiGetCompany
    @ApiInterceptor
    @GetMapping("/{companyId}")
    public CommonApiResponse getCompany(@PathVariable Long companyId, HttpServletResponse response){
        CommonApiResponse commonApiResponse = companyService.findCompanyById(companyId);
        response.setStatus(commonApiResponse.getHttpStatus());
        return commonApiResponse;
    }
}