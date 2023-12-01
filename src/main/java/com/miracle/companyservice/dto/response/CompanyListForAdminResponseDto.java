package com.miracle.companyservice.dto.response;

import com.miracle.companyservice.entity.Company;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@ToString
@EqualsAndHashCode
public class CompanyListForAdminResponseDto {

    private final Long id;
    private final String name;
    private final String email;
    private final Integer employeeNum;
    private final String bno;
    private final Boolean status;
    private final Boolean approveStatus;
    private final LocalDateTime createdAt;

    public CompanyListForAdminResponseDto() {
        this.id = null;
        this.name = null;
        this.email = null;
        this.employeeNum = null;
        this.bno = null;
        this.status = null;
        this.approveStatus = null;
        this.createdAt = null;
    }

    public CompanyListForAdminResponseDto(Company company, String decryptedEmail, Boolean status) {
        this.id = company.getId();
        this.name = company.getName();
        this.email = decryptedEmail;
        this.employeeNum = company.getEmployeeNum();
        this.bno = company.getBno();
        this.status = status;
        this.approveStatus = company.isApproveStatus();
        this.createdAt = company.getCreatedAt();
    }
}