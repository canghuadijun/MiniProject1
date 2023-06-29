package com.example.MiniProject1.services;

import com.example.MiniProject1.models.Product;
import com.example.MiniProject1.payload.Request.ProductRequest;

import java.util.List;

public interface IProductService {
    public abstract Product createProduct(ProductRequest productRequest);
    public abstract Product updateProduct(Long id, ProductRequest productRequest);

    public abstract boolean deleteProduct(Long id);
    public abstract List<Product> getAllProducts();
    public abstract  List<Product> searchProducts(String keyword);

    public abstract Product getProductById(Long id);
}
