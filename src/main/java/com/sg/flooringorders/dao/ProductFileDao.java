package com.sg.flooringorders.dao;

import com.sg.flooringorders.dto.Product;

import java.util.List;

public interface ProductFileDao {
    public Product getProduct(String productType);
    public List<Product> getAllProducts();
}
