package com.miracle.companyservice.entity;

import com.miracle.companyservice.dto.request.CompanySignUpRequestDto;
import com.miracle.companyservice.dto.response.PostCommonDataResponseDto;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@Entity
public class Company extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 50)
    private String email;

    @Column(nullable = false, unique = true, length = 50)
    private String bno;

    @Column(nullable = false)
    private int password;

    @Column(nullable = false, length = 50)
    private String name;

    @Column(nullable = false, length = 50)
    private String photo;

    @Column(nullable = false, length = 30)
    private String ceoName;

    @Column(nullable = false, length = 50)
    private String sector;

    @Column(nullable = false)
    private String address;

    @Column(columnDefinition = "TEXT")
    private String introduction;

    @OneToMany(mappedBy = "company")
    private List<CompanyFaq> faqList = new ArrayList<>();

    private int employeeNum;
    private boolean approveStatus;

    /**
     * Instantiates a new Company.
     *
     * @param id            the id
     * @param email         the email
     * @param bno           the bno
     * @param password      the password
     * @param name          the name
     * @param photo         the photo
     * @param ceoName       the ceo name
     * @param sector        the sector
     * @param address       the address
     * @param introduction  the introduction
     * @param faqList       the faq list
     * @param employeeNum   the employee num
     * @param approveStatus the approve status
     */
    @Builder
    public Company(Long id, String email, String bno, int password, String name, String photo,
                   String ceoName, String sector, String address, String introduction,
                   List<CompanyFaq> faqList, int employeeNum, boolean approveStatus) {
        this.id = id;
        this.email = email;
        this.bno = bno;
        this.password = password;
        this.name = name;
        this.photo = photo;
        this.ceoName = ceoName;
        this.sector = sector;
        this.address = address;
        this.introduction = introduction;
        this.faqList = faqList;
        this.employeeNum = employeeNum;
        this.approveStatus = approveStatus;
    }

    /**
     * Instantiates a new Company.
     * @param companySignUpRequestDto the company sign up request dto
     * @author Kade-jeon
     */
    public Company(CompanySignUpRequestDto companySignUpRequestDto) {
        this.email = companySignUpRequestDto.getEmail();
        this.bno = companySignUpRequestDto.getBno();
        this.password = companySignUpRequestDto.getPassword();
        this.name = companySignUpRequestDto.getName();
        this.photo = companySignUpRequestDto.getPhoto();
        this.ceoName = companySignUpRequestDto.getCeoName();
        this.sector = companySignUpRequestDto.getSector();
        this.address = companySignUpRequestDto.getAddress();
        this.introduction = companySignUpRequestDto.getIntroduction();
        this.employeeNum = companySignUpRequestDto.getEmployeeNum();
    }

    /**
     * Instantiates a new Company.
     *
     * @param name         the name
     * @param photo        the photo
     * @param ceoName      the ceo name
     * @param sector       the sector
     * @param address      the address
     * @param introduction the introduction
     * @param employeeNum  the employee num
     * @author wjdals3936
     */
    public Company(String name, String photo, String ceoName, String sector, String address, String introduction, int employeeNum) {
        this.name = name;
        this.photo = photo;
        this.ceoName = ceoName;
        this.sector = sector;
        this.address = address;
        this.introduction = introduction;
        this.employeeNum = employeeNum;
    }
}
