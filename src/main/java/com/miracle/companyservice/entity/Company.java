package com.miracle.companyservice.entity;

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

    @OneToMany
    @JoinColumn(name = "company_id")
    private List<CompanyFaq> faqList = new ArrayList<>();

    private int employeeNum;
    private boolean approveStatus;

    public Company(Long id, String email, String bno, int password, String name, String photo,
                   String ceoName, String sector, String address, String addressDetail, String introduction,
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
}
