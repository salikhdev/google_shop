package uz.salikhdev.google_shop.mapper;


import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import uz.salikhdev.google_shop.dto.response.OrderResponse;
import uz.salikhdev.google_shop.dto.response.UserResponse;
import uz.salikhdev.google_shop.entity.order.Order;
import uz.salikhdev.google_shop.entity.user.User;

import java.util.List;

@Mapper(componentModel = "spring")
public interface OrderMapper {

    @Mapping(target = "product", source = "product")
    @Mapping(target = "user", source = "user")
    OrderResponse toResponse(Order order);

    List<OrderResponse> toResponseList(List<Order> orders);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "fullName", expression = "java(user.getFirstName() + \" \" + user.getLastName())")
    @Mapping(target = "phoneNumber", source = "phoneNumber")
    UserResponse userToUserResponse(User user);
}
