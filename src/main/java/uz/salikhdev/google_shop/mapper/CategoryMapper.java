package uz.salikhdev.google_shop.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import uz.salikhdev.google_shop.dto.request.CategoryCreateRequest;
import uz.salikhdev.google_shop.dto.response.CategoryResponse;
import uz.salikhdev.google_shop.entity.product.Category;

@Mapper(componentModel = "spring")
public interface CategoryMapper {

    CategoryResponse toResponse(Category category);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "products", ignore = true)
    Category createCategory(CategoryCreateRequest request);
}
