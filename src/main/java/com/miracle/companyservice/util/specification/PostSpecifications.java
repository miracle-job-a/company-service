package com.miracle.companyservice.util.specification;

import com.miracle.companyservice.entity.Post;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;


public class PostSpecifications {

    public static Specification<Post> stackIdIn(Set<Long> stackIdSet) {
        return (root, query, criteriaBuilder) -> {
            if (!stackIdSet.isEmpty()) {
                Join<Post, Long> stackJoin = root.join("stackIdSet"); // "stackIdSet"은 Post 엔티티의 필드명
                return stackJoin.in(stackIdSet);
            }
            return criteriaBuilder.conjunction();
        };
    }

    public static Specification<Post> jobIdIn(Set<Long> jobIdSet) {
        return (root, query, criteriaBuilder) -> {
            if (!jobIdSet.isEmpty()) {
                Join<Post, Long> jobJoin = root.join("jobIdSet"); // "jobIdSet"은 Post 엔티티의 필드명
                return jobJoin.in(jobIdSet);
            }
            return criteriaBuilder.conjunction();
        };
    }

    public static Specification<Post> notDeleted() {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("deleted"), false);
    }

    public static Specification<Post> notClosed() {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("closed"), false);
    }

    public static Specification<Post> distinctById() {
        return (root, query, criteriaBuilder) -> {
            query.distinct(true);
            return criteriaBuilder.isTrue(criteriaBuilder.literal(true));
        };
    }

    public static Specification<Post> Closed() {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("closed"), true);
    }

    public static Specification<Post> orderByCreatedAtDesc() {
        return (root, query, criteriaBuilder) -> {
            query.orderBy(criteriaBuilder.desc(root.get("createdAt")));
            return criteriaBuilder.conjunction();
        };
    }

    public static Specification<Post> workAddressLike(Set<String> workAddresses) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            for (String address : workAddresses) {
                if (address != null && !address.isEmpty()) {
                    predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("workAddress")), "%" + address.toLowerCase() + "%"));
                }
            }

            return criteriaBuilder.or(predicates.toArray(new Predicate[0]));
        };
    }

    public static Specification<Post> careerGreaterThanOrEqual(int career) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.greaterThanOrEqualTo(root.get("career"), career);
    }
}