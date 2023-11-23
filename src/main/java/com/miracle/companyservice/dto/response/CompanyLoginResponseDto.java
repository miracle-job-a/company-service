package com.miracle.companyservice.dto.response;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode
public class CompanyLoginResponseDto {

    private final Long id;
    private final String email;
    private final String bno;

    public CompanyLoginResponseDto(Long id, String email, String bno) {
        this.id = id;
        this.email = email;
        this.bno = bno;
    }
}
