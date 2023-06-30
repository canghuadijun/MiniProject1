package com.example.MiniProject1.services.impl;

import com.example.MiniProject1.models.Product;
import com.example.MiniProject1.payload.Request.ProductRequest;
import com.example.MiniProject1.repositories.ProductRepository;
import com.example.MiniProject1.services.IProductService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService implements IProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public List<Product> searchProducts(String keyword) {
        return productRepository.findByName(keyword);
    }

    @Override
    public Product getProductById(Long id) {
        return productRepository.findById(id).orElse(null);
    }

    @Override
    public Product createProduct(ProductRequest productRequest) {
        if (productRepository.existsByName(productRequest.getName())) {
            return null;
        }
        Product product = new Product();
        product.setName(productRequest.getName());
        product.setCategory(productRequest.getCategory());
        product.setPrice(productRequest.getPrice());
        return productRepository.save(product);
    }

    @Override
    public Product updateProduct(Long id, ProductRequest productRequest) {
        Product product = productRepository.findById(id).orElse(null);
        if (product == null) {
            return null;
        }
        product.setName(productRequest.getName());
        product.setCategory(productRequest.getCategory());
        product.setPrice(productRequest.getPrice());
        return productRepository.save(product);
    }

    @Override
    public boolean deleteProduct(Long id) {
        if (productRepository.existsById(id)) {
            productRepository.deleteById(id);
            return true;
        }
        return false;
    }
}

