package com.product.controller;


import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.product.model.Product;
import com.product.service.ProductService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {

        this.productService = productService;
    }

    // Add Product
    @PostMapping
    public ResponseEntity<Product> addProduct(
            @Valid @RequestBody Product product) {

        return ResponseEntity.ok(
                productService.addProduct(product)
        );
    }

    // Get All Products
    @GetMapping
    public ResponseEntity<List<Product>> getAllProducts() {

        return ResponseEntity.ok(
                productService.getAllProducts()
        );
    }

    // Get Product By ID
    @GetMapping("/{productId}")
    public ResponseEntity<Product> getProductById(
            @PathVariable int productId) {

        Product product =
                productService.getProductById(productId);

        if (product == null) {

            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(product);
    }

    // Delete Product
    @DeleteMapping("/{productId}")
    public ResponseEntity<String> deleteProduct(
            @PathVariable int productId) {

        boolean deleted =
                productService.deleteProduct(productId);

        if (!deleted) {

            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(
                "Product deleted successfully"
        );
    }

    // Custom API 1
    // Get Products By Category
    @GetMapping("/category/{category}")
    public ResponseEntity<List<Product>> getProductsByCategory(
            @PathVariable String category) {

        return ResponseEntity.ok(
                productService.getProductsByCategory(category)
        );
    }

    // Custom API 2
    // Get Products Below Price
    @GetMapping("/price/{price}")
    public ResponseEntity<List<Product>> getProductsBelowPrice(
            @PathVariable double price) {

        return ResponseEntity.ok(
                productService.getProductsBelowPrice(price)
        );
    }

    // Custom API 3
    // Search Product By Name
    @GetMapping("/search/{name}")
    public ResponseEntity<List<Product>> searchProductByName(
            @PathVariable String name) {

        return ResponseEntity.ok(
                productService.searchProductByName(name)
        );
    }

    // Custom API 4
    // Update Stock
    @PutMapping("/{productId}/stock/{stock}")
    public ResponseEntity<Product> updateStock(
            @PathVariable int productId,
            @PathVariable int stock) {

        Product product =
                productService.updateStock(productId, stock);

        if (product == null) {

            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(product);
    }
}