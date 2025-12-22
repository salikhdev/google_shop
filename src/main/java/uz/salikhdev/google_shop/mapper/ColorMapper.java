package uz.salikhdev.google_shop.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import uz.salikhdev.google_shop.dto.request.ColorCreateRequest;
import uz.salikhdev.google_shop.dto.response.ColorResponse;
import uz.salikhdev.google_shop.entity.product.Color;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ColorMapper {

    ColorResponse toResponse(Color color);

    List<ColorResponse> toResponse(List<Color> colors);


    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "products", ignore = true)
    Color createColor(ColorCreateRequest color);


}
