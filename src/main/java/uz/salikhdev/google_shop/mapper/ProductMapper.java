package uz.salikhdev.google_shop.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import uz.salikhdev.google_shop.dto.response.ProductResponse;
import uz.salikhdev.google_shop.entity.product.Product;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    @Mapping(target = "imageUrl", ignore = true)
    ProductResponse toResponse(Product product);

    List<ProductResponse> toResponse(List<Product> products);

}
