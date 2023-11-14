package com.miracle.companyservice.dto.request;

import com.miracle.companyservice.validation.Bno;
import com.sun.istack.NotNull;
import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
public class CompanySignUpRequestDto {
    
    @NotBlank
    @Email
    @Size(min = 1, max = 50)
    private final String email;

    @NotBlank
    @Size(min = 1, max = 50)
    private final String name;

    @NotBlank
    @Size(min = 1, max = 50)
    private final String photo;

    @NotBlank
    private final int password;

    @NotBlank
    @Size(min = 1, max = 50)
    @Bno
    private final String bno;

    @NotBlank
    @Size(min = 1, max = 50)
    private final String ceoName;

    @NotBlank
    @Size(min = 1, max = 50)
    private final String sector;

    @NotBlank
    @Size(min = 1, max = 255)
    private final String address;

    @NotBlank
    private final String introduction;

    @NotBlank
    @Min(value = 1)
    private final int employeeNum;

    @Builder
    public CompanySignUpRequestDto(String email, String name, String photo,
                                   int password, String bno,
                                   String ceoName, String sector,
                                   String address, String introduction,
                                   int employeeNum) {
        this.email = email;
        this.name = name;
        this.photo = photo;
        this.password = password;
        this.bno = bno;
        this.ceoName = ceoName;
        this.sector = sector;
        this.address = address;
        this.introduction = introduction;
        this.employeeNum = employeeNum;
    }
}
