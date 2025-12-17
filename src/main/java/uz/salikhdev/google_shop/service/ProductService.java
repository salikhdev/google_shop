package uz.salikhdev.google_shop.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.salikhdev.google_shop.dto.response.ProductResponse;
import uz.salikhdev.google_shop.entity.product.Product;
import uz.salikhdev.google_shop.mapper.ProductMapper;
import uz.salikhdev.google_shop.repositroy.ProductRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;


    public List<ProductResponse> getAll() {


        List<Product> all = productRepository.findAll();
        return productMapper.toResponse(all);
    }


}
