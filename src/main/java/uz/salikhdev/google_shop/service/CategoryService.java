package uz.salikhdev.google_shop.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.salikhdev.google_shop.dto.request.CategoryCreateRequest;
import uz.salikhdev.google_shop.dto.response.CategoryResponse;
import uz.salikhdev.google_shop.entity.product.Category;
import uz.salikhdev.google_shop.exception.ConflictException;
import uz.salikhdev.google_shop.exception.NotFoundException;
import uz.salikhdev.google_shop.mapper.CategoryMapper;
import uz.salikhdev.google_shop.repositroy.CategoryRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    public void create(CategoryCreateRequest request) {
        if (categoryRepository.existsByName(request.name())) {
            throw new ConflictException("Category with this name already exists");
        }

        Category category = categoryMapper.createCategory(request);
        categoryRepository.save(category);
    }

    public List<CategoryResponse> getAll() {
        List<Category> categories = categoryRepository.findAll();
        return categoryMapper.toResponse(categories);
    }

    public CategoryResponse getById(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Category not found"));

        return categoryMapper.toResponse(category);
    }

    public void delete(Long id) {
        if (!categoryRepository.existsById(id)) {
            throw new NotFoundException("Category not found");
        }
        categoryRepository.deleteById(id);
    }
}
