package uz.salikhdev.google_shop.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import uz.salikhdev.google_shop.dto.request.SizeCreateRequest;
import uz.salikhdev.google_shop.dto.response.SizeResponse;
import uz.salikhdev.google_shop.entity.product.Size;

import java.util.List;

@Mapper(componentModel = "spring")
public interface SizeMapper {

    SizeResponse toResponse(Size size);

    List<SizeResponse> toResponse(List<Size> sizes);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "products", ignore = true)
    Size createSize(SizeCreateRequest request);
}
