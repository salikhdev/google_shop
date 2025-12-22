package uz.salikhdev.google_shop.repositroy;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.salikhdev.google_shop.entity.order.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
}
