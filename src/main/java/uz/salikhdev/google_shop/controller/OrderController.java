package uz.salikhdev.google_shop.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import uz.salikhdev.google_shop.dto.request.OrderCreateRequest;
import uz.salikhdev.google_shop.dto.request.OrderFilterRequest;
import uz.salikhdev.google_shop.dto.response.SuccessResponse;
import uz.salikhdev.google_shop.entity.order.Status;
import uz.salikhdev.google_shop.entity.user.User;
import uz.salikhdev.google_shop.service.OrderService;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
@Tag(name = "Order")
public class OrderController {

    private final OrderService orderService;

    @PostMapping("/create")
    public ResponseEntity<?> createOrder(
            @RequestBody OrderCreateRequest orderRequest,
            @AuthenticationPrincipal User user) {
        orderService.createOrder(orderRequest, user);
        return ResponseEntity.ok(SuccessResponse.ok("Order created successfully"));
    }

    @PostMapping("/create-many")
    public ResponseEntity<?> createManyOrder(
            @RequestBody List<OrderCreateRequest> ordersRequest,
            @AuthenticationPrincipal User user) {
        orderService.createManyOrder(ordersRequest, user);
        return ResponseEntity.ok(SuccessResponse.ok("Order created successfully"));
    }

    @DeleteMapping("/cancel")
    public ResponseEntity<?> cancelOrder(
            @RequestParam Long orderId,
            @AuthenticationPrincipal User user) {
        orderService.cancelOrder(orderId, user);
        return ResponseEntity.ok(SuccessResponse.ok("Order cancelled successfully"));
    }

    @DeleteMapping("/cancel-many")
    public ResponseEntity<?> cancelManyOrder(
            @RequestBody List<Long> orderIds,
            @AuthenticationPrincipal User user) {
        orderService.cancelManyOrder(orderIds, user);
        return ResponseEntity.ok(SuccessResponse.ok("Orders cancelled successfully"));
    }


    @GetMapping("/my-orders")
    public ResponseEntity<?> getUserOrders(
            @RequestParam(required = false) String search,
            @RequestParam(required = false) Status status,
            @RequestParam(required = false) String fromDate,
            @RequestParam(required = false) String toDate,
            @AuthenticationPrincipal User user) {

        OrderFilterRequest filter = OrderFilterRequest.builder()
                .search(search)
                .status(status != null ? status.name() : null)
                .fromDate(fromDate != null ? LocalDateTime.parse(fromDate) : null)
                .toDate(toDate != null ? LocalDateTime.parse(toDate) : null)
                .build();

        return ResponseEntity.ok(orderService.getUserOrders(filter, user));
    }

    @GetMapping("/all-orders")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Get all orders (Admin only)")
    public ResponseEntity<?> getAllOrders(
            @RequestParam(required = false) String search,
            @RequestParam(required = false) Status status,
            @RequestParam(required = false) String fromDate,
            @RequestParam(required = false) String toDate
    ) {
        OrderFilterRequest filter = OrderFilterRequest.builder()
                .search(search)
                .status(status != null ? status.name() : null)
                .fromDate(fromDate != null ? LocalDateTime.parse(fromDate) : null)
                .toDate(toDate != null ? LocalDateTime.parse(toDate) : null)
                .build();

        return ResponseEntity.ok(orderService.getAllOrders(filter));
    }


}