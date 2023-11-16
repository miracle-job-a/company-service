package com.miracle.companyservice.entity;

import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
public class Bno {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 50)
    private String bno;

    @Column(nullable = false)
    private boolean status;
}
