package de.ass37.examples.services;

import de.ass37.examples.entities.Product;
import de.ass37.examples.entities.User;
import de.ass37.examples.models.ProductModel;
import de.ass37.examples.models.UserModel;
import de.ass37.examples.repository.ProductRepository;
import de.ass37.examples.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ModelMapper mapper;

    public List<ProductModel> getAllProducts() {
        return productRepository.findAll()
                .stream()
                .map(user -> mapper.map(user, ProductModel.class))
                .toList();
    }

    public ProductModel getProductById(String id) {
        return productRepository.findById(Long.getLong(id))
                .map(user -> mapper.map(user, ProductModel.class))
                .orElseThrow(() -> new RuntimeException("Product not found"));
    }

    public ProductModel addProduct(ProductModel productModel) {
        Product savedProduct =  productRepository.save(mapper.map(productModel, Product.class));
        return mapper.map(savedProduct, ProductModel.class);
    }

    public ProductModel updateProduct(String id, ProductModel productModel) {
        if(productRepository.existsById(Long.getLong(id))) {
            Product product = mapper.map(productModel, Product.class);
            ProductModel savedProduct = mapper.map(product, ProductModel.class);
            return savedProduct;
        } else {
            throw new RuntimeException("no such id for product found");
        }
    }

    public void  deleteProduct(String id) {
        if(productRepository.existsById(Long.getLong(id))) {
            productRepository.deleteById(Long.getLong(id));
        } else {
            new RuntimeException("no such id for product found");
        }
    }
}
