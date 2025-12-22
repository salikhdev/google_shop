package uz.salikhdev.google_shop.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.salikhdev.google_shop.dto.request.SizeCreateRequest;
import uz.salikhdev.google_shop.dto.response.SizeResponse;
import uz.salikhdev.google_shop.entity.product.Size;
import uz.salikhdev.google_shop.exception.ConflictException;
import uz.salikhdev.google_shop.exception.NotFoundException;
import uz.salikhdev.google_shop.mapper.SizeMapper;
import uz.salikhdev.google_shop.repositroy.SizeRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SizeService {

    private final SizeRepository sizeRepository;
    private final SizeMapper sizeMapper;

    public void create(SizeCreateRequest request) {
        if (sizeRepository.existsByName(request.name())) {
            throw new ConflictException("Size with this name already exists");
        }

        Size size = sizeMapper.createSize(request);
        sizeRepository.save(size);
    }

    public List<SizeResponse> getAll() {
        List<Size> sizes = sizeRepository.findAll();
        return sizeMapper.toResponse(sizes);
    }

    public SizeResponse getById(Long id) {
        Size size = sizeRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Size not found"));

        return sizeMapper.toResponse(size);
    }

    public void delete(Long id) {
        if (!sizeRepository.existsById(id)) {
            throw new NotFoundException("Size not found");
        }
        sizeRepository.deleteById(id);
    }

}
