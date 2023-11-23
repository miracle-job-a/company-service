package com.miracle.companyservice.dto.response;

import com.miracle.companyservice.entity.Company;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.List;


@Getter
@ToString
public class PostCommonDataResponseDto{
    private final String name;
    private final String ceoName;
    private final String photo;
    private final int employeeNum;
    private final String address;
    private final String introduction;
    private final List<CompanyFaqResponseDto> faqList;

    @Builder
    public PostCommonDataResponseDto(Company company, List<CompanyFaqResponseDto> faqList) {
        this.name = company.getName();
        this.ceoName = company.getCeoName();
        this.photo = company.getPhoto();
        this.employeeNum = company.getEmployeeNum();
        this.address = company.getAddress();
        this.introduction = company.getIntroduction();
        this.faqList = faqList;
    }
}