package com.miracle.companyservice.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.*;

@Getter
@NoArgsConstructor
@Entity
public class Post extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long companyId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 10)
    private PostType postType;

    @Column(nullable = false, length = 100)
    private String title;

    @Column(nullable = false)
    private LocalDateTime endDate;

    @Column(nullable = false, length = 100)
    private String tool;

    @Column(nullable = false)
    private String workAddress;

    @Column(columnDefinition = "TEXT")
    private String mainTask;

    @Column(columnDefinition = "TEXT")
    private String workCondition;

    @Column(columnDefinition = "TEXT")
    private String qualification;

    @Column(columnDefinition = "TEXT")
    private String benefit;

    @Column(columnDefinition = "TEXT")
    private String specialSkill;

    @Column(columnDefinition = "TEXT")
    private String process;

    @Column(columnDefinition = "TEXT")
    private String notice;

    private int career;
    private boolean status;
    private LocalDateTime testStartDate;
    private LocalDateTime testEndDate;

    @OneToMany
    @JoinColumn(name = "post_id")
    private List<Question> questionList = new ArrayList<>();

    @ElementCollection
    @CollectionTable(
            name = "post_job",
            joinColumns = @JoinColumn(name = "post_id")
    )
    @Column(name = "job_id")
    private Set<Long> jobIdSet = new HashSet<>();

    @ElementCollection
    @CollectionTable(
            name = "post_stack",
            joinColumns = @JoinColumn(name = "post_id")
    )
    @Column(name = "stack_id")
    private Set<Long> stackIdSet = new HashSet<>();

    public Post(Long id, Long companyId,
                PostType postType, String title,
                LocalDateTime endDate, String tool,
                String workAddress, String mainTask, String workCondition,
                String qualification, String benefit,
                String specialSkill, String process,
                String notice,int career,
                boolean status,
                LocalDateTime testStartDate, LocalDateTime testEndDate,
                List<Question> questionList, Set<Long> jobIdSet, Set<Long> stackIdSet) {
        this.id = id;
        this.companyId = companyId;
        this.postType = postType;
        this.title = title;
        this.endDate = endDate;
        this.tool = tool;
        this.workAddress = workAddress;
        this.mainTask = mainTask;
        this.workCondition = workCondition;
        this.qualification = qualification;
        this.benefit = benefit;
        this.specialSkill = specialSkill;
        this.process = process;
        this.notice = notice;
        this.career = career;
        this.status = status;
        this.testStartDate = testStartDate;
        this.testEndDate = testEndDate;
        this.questionList = questionList;
        this.jobIdSet = jobIdSet;
        this.stackIdSet = stackIdSet;
    }
}