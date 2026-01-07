package uz.salikhdev.google_shop.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.salikhdev.google_shop.dto.request.OrderCreateRequest;
import uz.salikhdev.google_shop.dto.request.OrderFilterRequest;
import uz.salikhdev.google_shop.dto.response.OrderResponse;
import uz.salikhdev.google_shop.entity.order.Order;
import uz.salikhdev.google_shop.entity.order.Status;
import uz.salikhdev.google_shop.entity.product.Product;
import uz.salikhdev.google_shop.entity.user.User;
import uz.salikhdev.google_shop.exception.NotFoundException;
import uz.salikhdev.google_shop.mapper.OrderMapper;
import uz.salikhdev.google_shop.repositroy.OrderRepository;
import uz.salikhdev.google_shop.repositroy.spesification.OrderSpecification;
import uz.salikhdev.google_shop.repositroy.ProductRepository;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final OrderMapper orderMapper;

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

    @Transactional
    public void cancelOrder(Long orderId, User user) {
        Order order = orderRepository.findByIdAndUser_Id(orderId, user.getId())
                .orElseThrow(() -> new NotFoundException("Order not found with id: " + orderId));

        if (order.getStatus() != Status.PENDING) {
            throw new IllegalStateException("Only pending orders can be cancelled.");
        }

        order.setStatus(Status.CANCELLED);
        orderRepository.save(order);

        Product product = order.getProduct();
        product.setQuantity(product.getQuantity() + order.getQuantity());
        productRepository.save(product);
    }

    @Transactional
    public void createManyOrder(List<OrderCreateRequest> ordersRequest, User user) {
        for (OrderCreateRequest r : ordersRequest) {
            createOrder(r, user);
        }
    }

    @Transactional
    public void cancelManyOrder(List<Long> orderIds, User user) {
        for (Long id : orderIds) {
            cancelOrder(id, user);
        }
    }

    public List<OrderResponse> getUserOrders(OrderFilterRequest filter, User user) {

        var specification = OrderSpecification.filterOrders(
                filter.search(),
                filter.status(),
                filter.fromDate(),
                filter.toDate(),
                user.getId()
        );

        List<Order> orders = orderRepository.findAll(specification);
        return orderMapper.toResponseList(orders);
    }

    public List<OrderResponse> getAllOrders(OrderFilterRequest filter) {

        var specification = OrderSpecification.filterOrders(
                filter.search(),
                filter.status(),
                filter.fromDate(),
                filter.toDate(),
                null
        );

        List<Order> orders = orderRepository.findAll(specification);
        return orderMapper.toResponseList(orders);
    }

}
