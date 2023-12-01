package com.miracle.companyservice.entity;

import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
public class Bno extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 50)
    private String bno;

    @Column(nullable = false)
    private Boolean status;

    public Bno(String bno, Boolean status) {
        this.bno = bno;
        this.status = status;
    }

    public Bno(Long id, String bno, Boolean status) {
        this.id = id;
        this.bno = bno;
        this.status = status;
    }

    public Bno() {
        this.id = null;
        this.bno = null;
        this.status = null;
    }
}
