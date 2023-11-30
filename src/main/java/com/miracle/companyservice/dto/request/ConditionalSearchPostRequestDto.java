package com.miracle.companyservice.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.Set;

@Getter
@ToString
@EqualsAndHashCode
public class ConditionalSearchPostRequestDto {

    private final Set<Long> stackIdSet;

    @Schema(
            description = "경력값은 0이상의 양수입니다.",
            required = true,
            example = "3"
    )
    @PositiveOrZero(message = "400_21:경력값은 0이상의 양수입니다.")
    @NotBlank(message = "400_21:경력 값이 없습니다.")
    private final Integer career;
    private final Set<Long> jobIdSet;
    private final Set<String> addressSet;

    @Schema(
            description = "마감된 공고 검색 여부는 null 일 수 없습니다.",
            required = true,
            example = "false"
    )
    @NotNull(message = "400_25:마감된 공고 검색여부 값이 없습니다.")
    private final Boolean includeEnded;

    public ConditionalSearchPostRequestDto(Set<Long> stackIdSet, Integer career, Set<Long> jobIdSet, Set<String> addressSet, boolean includeEnded) {
        this.stackIdSet = stackIdSet;
        this.career = career;
        this.jobIdSet = jobIdSet;
        this.addressSet = addressSet;
        this.includeEnded = includeEnded;
    }

    public ConditionalSearchPostRequestDto() {
        this.stackIdSet = null;
        this.career = null;
        this.jobIdSet = null;
        this.addressSet = null;
        this.includeEnded = null;
    }


}
