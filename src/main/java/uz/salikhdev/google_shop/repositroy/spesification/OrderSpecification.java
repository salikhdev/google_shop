package uz.salikhdev.google_shop.repositroy.spesification;

import org.springframework.data.jpa.domain.Specification;
import uz.salikhdev.google_shop.entity.order.Order;

import java.time.LocalDateTime;

public class OrderSpecification {


    public static Specification<Order> filterOrders(
            String search,
            String status,
            LocalDateTime fromDate,
            LocalDateTime toDate,
            Long userId
    ) {
        return (root, query, criteriaBuilder) -> {
            var predicates = criteriaBuilder.conjunction();

            if (search != null && !search.trim().isEmpty()) {
                String searchPattern = "%" + search.trim().toLowerCase() + "%";
                var searchPredicate = criteriaBuilder.or(
                        criteriaBuilder.like(criteriaBuilder.lower(root.get("product").get("name")), searchPattern),
                        criteriaBuilder.like(criteriaBuilder.lower(root.get("user").get("phoneNumber")), searchPattern),
                        criteriaBuilder.like(criteriaBuilder.lower(root.get("user").get("firstName")), searchPattern),
                        criteriaBuilder.like(criteriaBuilder.lower(root.get("user").get("lastName")), searchPattern)
                );
                predicates = criteriaBuilder.and(predicates, searchPredicate);
            }

            if (status != null) {
                predicates = criteriaBuilder.and(predicates,
                        criteriaBuilder.equal(root.get("status"), status));
            }

            if (fromDate != null) {
                predicates = criteriaBuilder.and(predicates,
                        criteriaBuilder.greaterThanOrEqualTo(root.get("createdAt"), fromDate));
            }

            if (toDate != null) {
                predicates = criteriaBuilder.and(predicates,
                        criteriaBuilder.lessThanOrEqualTo(root.get("createdAt"), toDate));
            }
            if (userId != null) {
                predicates = criteriaBuilder.and(predicates,
                        criteriaBuilder.equal(root.get("user").get("id"), userId));
            }

            return predicates;
        };
    }


}
