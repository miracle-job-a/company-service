package com.miracle.companyservice.dto.request;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.util.Set;

@Getter
@ToString
@EqualsAndHashCode
public class ConditionalSearchPostRequestDto {

    private final Set<Long> stackIdSet;
    private final Integer career;
    private final Set<Long> jobIdSet;
    private final Set<String> addressSet;
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
