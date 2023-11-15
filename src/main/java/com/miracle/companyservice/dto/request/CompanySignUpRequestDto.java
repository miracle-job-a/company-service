package com.miracle.companyservice.dto.request;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import javax.validation.constraints.*;

@Getter
@EqualsAndHashCode
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


    private final int password;

    @NotBlank
    @Size(min = 1, max = 50)
    @Pattern(regexp = "\\d{3}[-]\\d{2}[-]\\d{5}")
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
