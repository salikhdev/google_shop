package uz.salikhdev.google_shop.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.salikhdev.google_shop.dto.request.OrderCreateRequest;
import uz.salikhdev.google_shop.entity.order.Order;
import uz.salikhdev.google_shop.entity.order.Status;
import uz.salikhdev.google_shop.entity.product.Product;
import uz.salikhdev.google_shop.entity.user.User;
import uz.salikhdev.google_shop.exception.NotFoundException;
import uz.salikhdev.google_shop.repositroy.OrderRepository;
import uz.salikhdev.google_shop.repositroy.ProductRepository;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;

    @Transactional
    public void createOrder(OrderCreateRequest request, User user) {

        Product product = productRepository.findById(request.productId())
                .orElseThrow(() -> new NotFoundException("Product not found with id: " + request.productId()));

        if (product.getQuantity() < request.quantity()) {
            throw new NotFoundException("Insufficient product quantity for product id: " + request.productId());
        }

        BigDecimal totalPrice = product.getPrice().multiply(BigDecimal.valueOf(request.quantity()));
        Order order = Order.builder()
                .user(user)
                .product(product)
                .status(Status.PENDING)
                .quantity(request.quantity())
                .totalPrice(totalPrice)
                .build();

        orderRepository.save(order);
        product.setQuantity(product.getQuantity() - request.quantity());
        productRepository.save(product);
    }

}
