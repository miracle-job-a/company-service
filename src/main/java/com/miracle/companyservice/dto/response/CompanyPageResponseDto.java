package com.miracle.companyservice.dto.response;

import com.miracle.companyservice.entity.Bno;
import com.miracle.companyservice.entity.Company;
import com.miracle.companyservice.entity.Post;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class CompanyPageResponseDto {
    private final Long companyId;
    private final Boolean approveStatus;
    private final String name;
    private final String ceoName;
    private final String photo;
    private final int employeeNum;
    private final String address;
    private final String introduction;
    private final String sector;
    private final Boolean bnoStatus;
    private final Long countOpen;


    public CompanyPageResponseDto(Company company, Boolean bnoStatus, Long countOpen) {
        this.companyId = company.getId();
        this.approveStatus = company.isApproveStatus();
        this.name = company.getName();
        this.ceoName = company.getName();
        this.photo = company.getPhoto();
        this.employeeNum = company.getEmployeeNum();
        this.address = company.getAddress();
        this.introduction = company.getIntroduction();
        this.sector = company.getSector();
        this.bnoStatus = bnoStatus;
        this.countOpen = countOpen;
    }
}
