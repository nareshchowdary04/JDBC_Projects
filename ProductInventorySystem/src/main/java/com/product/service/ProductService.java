package com.product.service;


import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.product.model.Product;

@Service
public class ProductService {

    private List<Product> products = new ArrayList<>();

    // Add Product
    public Product addProduct(Product product) {

        products.add(product);

        return product;
    }

    // Get All Products
    public List<Product> getAllProducts() {

        return products;
    }

    // Get Product By ID
    public Product getProductById(int productId) {

        return products.stream()
                .filter(product -> product.getProductId() == productId)
                .findFirst()
                .orElse(null);
    }

    // Delete Product
    public boolean deleteProduct(int productId) {

        return products.removeIf(
                product -> product.getProductId() == productId
        );
    }

    // Custom Method 1
    // Get Products By Category
    public List<Product> getProductsByCategory(String category) {

        return products.stream()
                .filter(product ->
                        product.getCategory().equalsIgnoreCase(category))
                .collect(Collectors.toList());
    }

    // Custom Method 2
    // Get Products Below Price
    public List<Product> getProductsBelowPrice(double price) {

        return products.stream()
                .filter(product -> product.getPrice() < price)
                .collect(Collectors.toList());
    }

    // Custom Method 3
    // Search Product By Name
    public List<Product> searchProductByName(String name) {

        return products.stream()
                .filter(product ->
                        product.getProductName()
                                .toLowerCase()
                                .contains(name.toLowerCase()))
                .collect(Collectors.toList());
    }

    // Custom Method 4
    // Update Stock
    public Product updateStock(int productId, int stock) {

        Product product = getProductById(productId);

        if (product != null) {

            product.setStock(stock);

        }

        return product;
    }
}