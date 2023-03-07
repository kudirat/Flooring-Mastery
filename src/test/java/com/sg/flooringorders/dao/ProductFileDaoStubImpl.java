package com.sg.flooringorders.dao;

import com.sg.flooringorders.dto.Product;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProductFileDaoStubImpl implements ProductFileDao{

    Map<String, Product> products = new HashMap<>();

    public ProductFileDaoStubImpl(){
        Product product1 = new Product("Wood", new BigDecimal(5.00), new BigDecimal(2.50));
        Product product2 = new Product("Tile", new BigDecimal(2.00), new BigDecimal(4.70));
        Product product3 = new Product("Glass", new BigDecimal(4.60), new BigDecimal(5.50));
        Product product4 = new Product("Plastic", new BigDecimal(2.30), new BigDecimal(4.20));

        this.products.put(product1.getProductType(),product1);
        this.products.put(product2.getProductType(), product2);
        this.products.put(product3.getProductType(), product3);
        this.products.put(product4.getProductType(), product4);
    }

    @Override
    public Product getProduct(String productType) {
        return products.get(productType);
    }

    @Override
    public List<Product> getAllProducts() {
        List<Product> allProducts = new ArrayList<>();
        for(Product product: this.products.values()){
            allProducts.add(product);
        }
        return allProducts;
    }
}
