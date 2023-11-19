package com.miracle.companyservice.service;

import com.miracle.companyservice.dto.request.CompanySignUpRequestDto;
import com.miracle.companyservice.dto.response.ApiResponse;
import com.miracle.companyservice.dto.response.CompanyFaqDto;
import com.miracle.companyservice.dto.response.PostCommonDataResponseDto;
import com.miracle.companyservice.dto.response.SuccessApiResponse;
import com.miracle.companyservice.entity.Company;

import com.miracle.companyservice.entity.CompanyFaq;
import com.miracle.companyservice.repository.CompanyFaqRepository;
import com.miracle.companyservice.repository.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CompanyServiceImpl implements CompanyService{

    private final CompanyRepository companyRepository;
    private final CompanyFaqRepository companyFaqRepository;


    @Autowired
    public CompanyServiceImpl(CompanyRepository companyRepository, CompanyFaqRepository companyFaqRepository) {
        this.companyRepository = companyRepository;
        this.companyFaqRepository = companyFaqRepository;
    }

    public ApiResponse signUpCompany(CompanySignUpRequestDto companySignUpRequestDto) {

        if (companyRepository.existsByEmail(companySignUpRequestDto.getEmail())) {
            throw new DuplicateKeyException("중복된 이메일이 있습니다.");
        }

        Company save = companyRepository.save(new Company(companySignUpRequestDto));
            return SuccessApiResponse.builder()
                    .httpStatus(HttpStatus.OK.value())
                    .message("회원가입 성공")
                    .build();
    }

    @Override
    public PostCommonDataResponseDto getCompanyFaqsByCompanyId(Long companyId){
        Company company = companyRepository.findById(companyId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid company ID: " + companyId));
        System.out.println("======company==> "+company);

        List<CompanyFaq> faqs = companyFaqRepository.findByCompanyId(companyId);
        System.out.println("======fags ====> "+faqs);

        List<CompanyFaqDto> faqList = faqs.stream()
                .map(CompanyFaqDto::new)
                .collect(Collectors.toList());
        System.out.println("======faqList======>" + faqList);

        return new PostCommonDataResponseDto(
                company.getName(),
                company.getCeoName(),
                company.getPhoto(),
                company.getEmployeeNum(),
                company.getAddress(),
                company.getIntroduction(),
                faqList);
    }
}
