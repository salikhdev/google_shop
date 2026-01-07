package uz.salikhdev.google_shop.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import uz.salikhdev.google_shop.dto.request.ProductCreateRequest;
import uz.salikhdev.google_shop.dto.response.OrderProductResponse;
import uz.salikhdev.google_shop.dto.response.ProductResponse;
import uz.salikhdev.google_shop.entity.product.Product;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    @Mapping(target = "imageUrl", source = "imageUrl")
    ProductResponse toResponse(Product product);

    List<ProductResponse> toResponse(List<Product> products);


    @Mapping(target = "id", ignore = true)
    @Mapping(target = "imageUrl", source = "imageUrl")
    @Mapping(target = "color.id", source = "colorId")
    @Mapping(target = "category.id", source = "categoryId")
    @Mapping(target = "size.id", source = "sizeId")
    Product createProduct(ProductCreateRequest request);


    OrderProductResponse toOrderProductResponse(Product product);

}
