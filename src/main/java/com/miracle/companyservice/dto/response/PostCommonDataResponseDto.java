package com.miracle.companyservice.dto.response;

import lombok.Getter;
import lombok.ToString;

import java.util.List;


@Getter
@ToString
public class PostCommonDataResponseDto{

    public static PostCommonDataResponseDto fromObjectArray;
    private final String name;
    private final String ceoName;
    private final String photo;
    private final int employeeNum;
    private final String address;
    private final String introduction;
    private final List<CompanyFaqDto> faqList;

    /**
     * Instantiates a new Post common data response dto.
     * @param name         the name
     * @param ceoName      the ceo name
     * @param photo        the photo
     * @param employeeNum  the employee num
     * @param address      the address
     * @param introduction the introduction
     * @param faqList      the faq list
     * @author wjdals3936
     */
    public PostCommonDataResponseDto(String name, String ceoName, String photo, int employeeNum, String address, String introduction, List<CompanyFaqDto> faqList) {
        this.name = name;
        this.ceoName = ceoName;
        this.photo = photo;
        this.employeeNum = employeeNum;
        this.address = address;
        this.introduction = introduction;
        this.faqList = faqList;
    }
}