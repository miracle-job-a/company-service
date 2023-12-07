package com.miracle.companyservice.dto.response;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@EqualsAndHashCode
public class CompanyNameResponseDto {
    private final Long postId;
    private final Long companyId;
    private final String companyName;

    public CompanyNameResponseDto(Long postId, Long companyId, String companyName) {
        this.postId = postId;
        this.companyId = companyId;
        this.companyName = companyName;

    }
}
