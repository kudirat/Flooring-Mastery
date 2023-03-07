package com.sg.flooringorders.dao;

import com.sg.flooringorders.dto.Product;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.math.BigDecimal;
import java.util.*;

public class ProductFileDaoImpl implements ProductFileDao {
    private String productType;
    private BigDecimal costPerSquareFoot;
    private BigDecimal laborCostPerSquareFoot;
    public final String PRODUCT_FILE;

    public static final String DELIMITER = ",";

    private Map<String, Product> products = new HashMap<>();


    public ProductFileDaoImpl(){
        //PRODUCT_FILE = "products.txt";
        this.PRODUCT_FILE = "src\\main\\Data\\Products\\products.txt";
    }

    public ProductFileDaoImpl(String productTextFile){
        this.PRODUCT_FILE = productTextFile;
    }

    public Product getProduct(String productType){
        loadProducts();
        Product product = products.get(productType);
        return product;
    }

    public BigDecimal getCostPerSquareFoot(){
        return this.costPerSquareFoot;
    }

    public BigDecimal getLaborCostPerSquareFoot(){
        return this.laborCostPerSquareFoot;
    }


    public List<Product> getAllProducts(){
        loadProducts();
        List<Product> currProducts = new ArrayList<Product>(products.values());
        return currProducts;
    }

    private Product unmarshallProduct(String currentLine) {
        String [] productTokens = currentLine.split(DELIMITER);

        Product productFromFile = new Product(productTokens[0], new BigDecimal(productTokens[1]), new BigDecimal(productTokens[2]));

        productFromFile.setProductType(productTokens[0]);
        productFromFile.setCostPerSquareFoot(new BigDecimal(productTokens[1]));
        productFromFile.setLaborCostPerSquareFoot(new BigDecimal(productTokens[2]));

        return productFromFile;
    }

    private void loadProducts()  {
        Scanner scanner = null;
        try {
            scanner = new Scanner(new BufferedReader(new FileReader(PRODUCT_FILE)));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        String currLine;
        Product currProduct;
        while (scanner.hasNextLine()) {
            currLine = scanner.nextLine();
            //ignore the first line, the heading
            if (currLine.startsWith("ProductType")) {
                continue;
            }
            currProduct = unmarshallProduct(currLine);
            products.put(currProduct.getProductType(), currProduct);
        }
        scanner.close();
    }


}
