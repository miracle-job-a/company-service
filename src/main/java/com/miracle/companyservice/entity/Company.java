package com.miracle.companyservice.entity;

import com.miracle.companyservice.dto.request.CompanySignUpRequestDto;
import com.miracle.companyservice.util.encryptor.Encryptors;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
@ToString
public class Company extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 512)
    private String email;

    @Column(nullable = false, unique = true, length = 50)
    private String bno;

    @Column(nullable = false, length = 128)
    private String password;

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

    @Column(nullable = false, columnDefinition = "TEXT")
    private String introduction;

    @OneToMany
    @JoinColumn(name = "company_id")
    private List<CompanyFaq> faqList = new ArrayList<>();

    @Column(nullable = false)
    private int employeeNum;

    @Column(nullable = false)
    private boolean approveStatus;

    @Builder
    public Company(Long id, String email, String bno, String password, String name, String photo,
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


}
