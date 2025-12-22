package uz.salikhdev.google_shop.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.salikhdev.google_shop.dto.request.ColorCreateRequest;
import uz.salikhdev.google_shop.dto.response.ColorResponse;
import uz.salikhdev.google_shop.entity.product.Color;
import uz.salikhdev.google_shop.exception.ConflictException;
import uz.salikhdev.google_shop.exception.NotFoundException;
import uz.salikhdev.google_shop.mapper.ColorMapper;
import uz.salikhdev.google_shop.repositroy.ColorRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ColorService {

    private final ColorRepository colorRepository;
    private final ColorMapper colorMapper;


    public void create(ColorCreateRequest request) {
        if (colorRepository.existsByName(request.name())) {
            throw new ConflictException("Color with this name already exists");
        }

        Color color = colorMapper.createColor(request);
        colorRepository.save(color);
    }

    public List<ColorResponse> getAll() {
        List<Color> colors = colorRepository.findAll();
        return colorMapper.toResponse(colors);
    }

    public ColorResponse getById(Long id) {
        Color color = colorRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Color not found"));

        return colorMapper.toResponse(color);
    }

    public void delete(Long id) {
        if (!colorRepository.existsById(id)) {
            throw new NotFoundException("Color not found");
        }
        colorRepository.deleteById(id);
    }

}
