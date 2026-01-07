package uz.salikhdev.google_shop.repositroy;

import lombok.NonNull;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import uz.salikhdev.google_shop.entity.order.Order;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long>, JpaSpecificationExecutor<Order> {

    @EntityGraph(attributePaths = {"product"})
    Optional<Order> findByIdAndUser_Id(Long id, Long userId);

    @Override
    @NonNull
    @EntityGraph(attributePaths = {"product", "user"})
    List<Order> findAll(@NonNull Specification<Order> spec);


    @NonNull
    @Override
    @EntityGraph(attributePaths = {"user"})
    Optional<Order> findById(Long aLong);
}
