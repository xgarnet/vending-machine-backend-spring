package de.ass37.examples.services;

import de.ass37.examples.entities.Product;
import de.ass37.examples.entities.User;
import de.ass37.examples.models.ProductModel;
import de.ass37.examples.repository.ProductRepository;
import de.ass37.examples.repository.UserRepository;
import de.ass37.examples.services.exceptions.BadServiceCallException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ModelMapper mapper;
    @Autowired
    private UserRepository userRepository;

    public List<ProductModel> getAllProducts() {
        return productRepository.findAll()
                .stream()
                .map(user -> mapper.map(user, ProductModel.class))
                .toList();
    }

    public ProductModel getProductById(String id) {
        return productRepository.findById(Integer.parseInt(id))
                .map(product -> mapper.map(product, ProductModel.class))
                .orElseThrow(() -> new BadServiceCallException("Product not found"));
    }

    public ProductModel addProduct(ProductModel productModel, String username) {

        User user = userRepository.findByUsername(username).orElseThrow(() -> new BadServiceCallException("no such user"));
        if(user.getRole().equalsIgnoreCase("seller")) {
            productModel.setSellerId(user.getId());
        } else {
            throw new BadServiceCallException("no seller role found");
        }
        Product savedProduct =  productRepository.save(mapper.map(productModel, Product.class));
        return mapper.map(savedProduct, ProductModel.class);
    }

    public ProductModel updateProduct(String id, ProductModel productModel, String username) {
        User user = userRepository.findByUsername(username).orElseThrow(() -> new BadServiceCallException("no such user"));
        if(user.getRole().equalsIgnoreCase("seller")) {
            Product product = productRepository.findById(Integer.parseInt(id)).orElseThrow(() -> new BadServiceCallException("no such product id"));
            if(product.getSellerId() == user.getId()) {
                 product.setProductName(productModel.getProductName());
                 product.setCost(productModel.getCost());
                 product.setAmountAvailable(productModel.getAmountAvailable());
                Product savedProduct = productRepository.save(product);
                ProductModel savedModel = mapper.map(savedProduct, ProductModel.class);
                return savedModel;
            } else {
                throw new BadServiceCallException("no match: seller id" + product.getSellerId() + "user id: " + user.getId());
            }
        } else  {
            throw new BadServiceCallException("no seller role found");
        }

    }

    public void  deleteProduct(String id, String username) {
        User user = userRepository.findByUsername(username).orElseThrow(() -> new BadServiceCallException("no such user"));
        if(user.getRole().equalsIgnoreCase("seller")) {
            Product product = productRepository.findById(Integer.parseInt(id)).orElseThrow(() -> new BadServiceCallException("no such product found"));
            if(product.getSellerId() == user.getId()) {
                productRepository.deleteById(Integer.parseInt(id));
            } else {
                throw new BadServiceCallException("no match: seller id" + product.getSellerId() + "user id: " + user.getId());
            }
        } else {
            throw new BadServiceCallException("no seller role found");
        }

    }
}
