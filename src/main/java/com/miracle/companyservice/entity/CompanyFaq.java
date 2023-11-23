package com.miracle.companyservice.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;

@Getter
@NoArgsConstructor
@Entity
public class CompanyFaq extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String question;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String answer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id")
    private Company company;

    public CompanyFaq(String question, String answer) {
        this.question = question;
        this.answer = answer;
    }

    @Builder
    public CompanyFaq(String question, String answer, Company company) {
        this.question = question;
        this.answer = answer;
        this.company = company;
    }
}
