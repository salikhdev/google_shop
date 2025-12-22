package uz.salikhdev.google_shop.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.salikhdev.google_shop.dto.request.ProductCreateRequest;
import uz.salikhdev.google_shop.dto.response.ProductResponse;
import uz.salikhdev.google_shop.entity.product.Product;
import uz.salikhdev.google_shop.exception.ConflictException;
import uz.salikhdev.google_shop.exception.NotFoundException;
import uz.salikhdev.google_shop.mapper.ProductMapper;
import uz.salikhdev.google_shop.repositroy.CategoryRepository;
import uz.salikhdev.google_shop.repositroy.ColorRepository;
import uz.salikhdev.google_shop.repositroy.ProductRepository;
import uz.salikhdev.google_shop.repositroy.SizeRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final ColorRepository colorRepository;
    private final SizeRepository sizeRepository;
    private final ProductMapper productMapper;

    public void create(ProductCreateRequest request) {
        if (productRepository.existsByName(request.name())) {
            throw new ConflictException("Product with this name already exists");
        }

        if (!categoryRepository.existsById(request.categoryId())) {
            throw new NotFoundException("Category not found");
        }

        if (!sizeRepository.existsById(request.sizeId())) {
            throw new NotFoundException("Size not found");
        }

        if (!colorRepository.existsById(request.colorId())) {
            throw new NotFoundException("Color not found");
        }

        Product product = productMapper.createProduct(request);
        productRepository.save(product);
    }

    public List<ProductResponse> getAll(String search) {

        if (search != null && !search.isBlank()) {
            List<Product> products = productRepository.findByNameContainingIgnoreCase(search);
            return productMapper.toResponse(products);
        }

        List<Product> products = productRepository.findAll();
        return productMapper.toResponse(products);
    }

    public ProductResponse getById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Product not found"));

        return productMapper.toResponse(product);
    }

    public void delete(Long id) {
        if (!productRepository.existsById(id)) {
            throw new NotFoundException("Product not found");
        }
        productRepository.deleteById(id);
    }

}
