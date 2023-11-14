package com.miracle.companyservice.service;

import com.miracle.companyservice.dto.api.BaseApi;
import com.miracle.companyservice.dto.request.CompanySignUpRequestDto;
import com.miracle.companyservice.entity.Company;
import com.miracle.companyservice.exception.InternalServerException;
import com.miracle.companyservice.repository.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CompanyServiceImpl implements CompanyService{

    private final CompanyRepository companyRepository;

    @Autowired
    public CompanyServiceImpl(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }

    public BaseApi signUpCompany(CompanySignUpRequestDto companySignUpRequestDto) {

        if (companyRepository.existsByEmail(companySignUpRequestDto.getEmail())) {
            throw new DuplicateKeyException("중복된 이메일이 있습니다.");
        }

        Company save = companyRepository.save(new Company(companySignUpRequestDto));
            return BaseApi.builder()
                    .httpStatus(HttpStatus.OK)
                    .code("200_1")
                    .message("회원가입 성공")
                    .build();
    }

}
