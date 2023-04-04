package de.ass37.examples.services;

import de.ass37.examples.models.ProductModel;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductService {
    public List<ProductModel> getAllProducts() {
        return new ArrayList<>();
    }

    public ProductModel getProductById(String id) {
        return new ProductModel();
    }

    public ProductModel addProduct(ProductModel productModel) {
        return new ProductModel();
    }

    public ProductModel updateProduct(String id, ProductModel productModel) {
        return new ProductModel();
    }

    public void  deleteProduct(String id) {

    }
}
