package com.miracle.companyservice.service;

import com.miracle.companyservice.dto.request.CompanyLoginRequestDto;
import com.miracle.companyservice.dto.request.CompanySignUpRequestDto;
import com.miracle.companyservice.dto.response.ApiResponse;
import com.miracle.companyservice.dto.response.CompanyFaqDto;
import com.miracle.companyservice.dto.response.PostCommonDataResponseDto;
import com.miracle.companyservice.dto.response.CommonApiResponse;
import com.miracle.companyservice.dto.response.SuccessApiResponse;
import com.miracle.companyservice.entity.Company;

import com.miracle.companyservice.entity.CompanyFaq;
import com.miracle.companyservice.repository.CompanyFaqRepository;
import com.miracle.companyservice.repository.BnoRepository;
import com.miracle.companyservice.repository.CompanyRepository;
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

    @Autowired
    public CompanyServiceImpl(CompanyRepository companyRepository, CompanyFaqRepository companyFaqRepository, BnoRepository bnoRepository) {
        this.companyRepository = companyRepository;
        this.companyFaqRepository = companyFaqRepository;
        this.bnoRepository = bnoRepository;
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

