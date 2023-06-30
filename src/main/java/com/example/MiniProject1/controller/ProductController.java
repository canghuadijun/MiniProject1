package com.example.MiniProject1.controller;

import com.example.MiniProject1.models.Product;
import com.example.MiniProject1.payload.Request.ProductRequest;
import com.example.MiniProject1.payload.Response.ResponseObject;
import com.example.MiniProject1.services.impl.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/admin")
    public ResponseEntity<?> checkAuthorize() {
        return ResponseEntity.ok().body("admin");
    }


    @GetMapping
    public ResponseEntity<List<Product>> getAllProducts() {
        List<Product> products = productService.getAllProducts();
        return ResponseEntity.ok(products);
    }

    @GetMapping("/search")
    public ResponseEntity<List<Product>> searchProducts(@RequestParam("keyword") String keyword) {
        List<Product> products = productService.searchProducts(keyword);
        return ResponseEntity.ok(products);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseObject> getProductById(@PathVariable("id") Long id) {
        Product product = productService.getProductById(id);
        if (product == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ResponseObject("NOT_FOUND", "get product failed", ""));
        }
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ResponseObject("ok", "get product successfully", product));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/create")
    public ResponseEntity<ResponseObject> createProduct(@RequestBody ProductRequest productRequest) {
        Product product = productService.createProduct(productRequest);
        if (product == null) {
            return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED)
                    .body(new ResponseObject("failed", "already exists", ""));

        }
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ResponseObject("ok", "create product successfully", product));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/update/{id}")
    public ResponseEntity<ResponseObject> updateProduct(@PathVariable("id") Long id, @RequestBody ProductRequest productRequest) {
        Product product = productService.updateProduct(id, productRequest);
        if (product == null) {
            return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED)
                    .body(new ResponseObject("failed", "update failed",""));
        }
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("ok", "update successfully", product));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ResponseObject> deleteProduct(@PathVariable("id") Long id) {
        boolean deleted = productService.deleteProduct(id);
        if (!deleted) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ResponseObject("fail", "product not found", ""));
        }
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ResponseObject("ok", "delete successfully", ""));
    }
}
