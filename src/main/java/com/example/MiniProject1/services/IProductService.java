package com.example.MiniProject1.services;

import com.example.MiniProject1.models.Product;
import com.example.MiniProject1.payload.Request.ProductRequest;

import java.util.List;

public interface IProductService {
    Product createProduct(ProductRequest productRequest);
    Product updateProduct(Long id, ProductRequest productRequest);

    boolean deleteProduct(Long id);
    List<Product> getAllProducts();
    List<Product> searchProducts(String keyword);

    Product getProductById(Long id);
}
