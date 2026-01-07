package uz.salikhdev.google_shop.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.salikhdev.google_shop.entity.order.Order;
import uz.salikhdev.google_shop.entity.order.Status;
import uz.salikhdev.google_shop.repositroy.OrderRepository;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class ClickService {

    private final OrderRepository orderRepository;

    public String generatePaymentUrl(Long orderId, Long userId) {

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        if (!order.getUser().getId().equals(userId)) {
            throw new RuntimeException("Order does not belong to the user");
        }

        return "http://localhost:8081/api/v1/click/pay?order_id=" + order.getId() + "&amount=" + order.getTotalPrice().longValue();
    }

    public void preparePayment(Long orderId, Long amount, String secretKey) {

        if (secretKey == null || !secretKey.equals("12345")) {
            throw new RuntimeException("Invalid secret key");
        }

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        if (order.getTotalPrice().longValue() != amount) {
            throw new RuntimeException("Amount does not match the order total price");
        }
    }

    @Transactional
    public void confirmPayment(Long orderId, String secretKey) {
        if (secretKey == null || !secretKey.equals("12345")) {
            throw new RuntimeException("Invalid secret key");
        }
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        if(order.getStatus() == Status.COMPLETED){
            throw new RuntimeException("Order is already completed");
        }

        BigDecimal balance = order.getUser().getBalance();
        BigDecimal cashback = order.getTotalPrice().multiply(BigDecimal.valueOf(0.02));

        order.getUser().setBalance(balance.add(cashback));
        order.setStatus(Status.COMPLETED);

        orderRepository.save(order);
    }


}
