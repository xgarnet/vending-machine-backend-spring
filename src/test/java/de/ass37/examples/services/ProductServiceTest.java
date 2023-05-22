package de.ass37.examples.services;

import de.ass37.examples.entities.Product;
import de.ass37.examples.entities.User;
import de.ass37.examples.models.ProductModel;
import de.ass37.examples.repository.ProductRepository;
import de.ass37.examples.repository.UserRepository;
import de.ass37.examples.services.exceptions.BadServiceCallException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = {ProductService.class, ModelMapper.class})
@AutoConfigureMockMvc
public class ProductServiceTest {

    @Autowired
    private ProductService productService;
    @MockBean
    private ProductRepository productRepository;
    @MockBean
    private UserRepository userRepository;

    @Test
    public void testGetAllProducts() {
        when(productRepository.findAll()).thenReturn(Collections.emptyList());
        Assertions.assertEquals(Collections.emptyList(), productService.getAllProducts());
    }

    @Test
    public void testGetProductById_ProductNotFound() {
        //Given
        final String errorMessage = "Product not found";

        //Then
        when(productRepository.findById(anyInt())).thenReturn(Optional.empty());

        //Then
        Throwable exception = assertThrows(BadServiceCallException.class, () -> productService.getProductById("1"));
        Assertions.assertEquals(errorMessage, exception.getMessage());
    }

    @Test
    public void testGetProductById_Successful() {
        //Given
        final Product product = Product.builder().id(1).productName("Tee").amountAvailable(10).cost(120).sellerId(3).build();

        //When
        when(productRepository.findById(anyInt())).thenReturn(Optional.of(product));
        ProductModel productModel = productService.getProductById("1");

        //Then
        assertEquals(productModel.getProductName(), product.getProductName());
        assertEquals(productModel.getId(), product.getId());
        assertEquals(productModel.getCost(), product.getCost());
        assertEquals(productModel.getAmountAvailable(), product.getAmountAvailable());
    }

    @Test
    public void testAddProduct_NoSuchUser() {
        //Given
        final String errorMessage = "no such user";
        final ProductModel product = ProductModel.builder().build();

        //When
        Mockito.when(userRepository.findByUsername(anyString())).thenReturn(Optional.empty());

        //Then
        Throwable exception = assertThrows(BadServiceCallException.class, () -> productService.addProduct(product, anyString()));
        Assertions.assertEquals(errorMessage, exception.getMessage());

    }

    @Test
    public void testAddProduct_NoSellerRole() {
        //Given
        final String errorMessage = "no seller role found";
        final User user = User.builder().role("buyer").build();
        final ProductModel productModel = ProductModel.builder().build();

        //When
        Mockito.when(userRepository.findByUsername(anyString())).thenReturn(Optional.of(user));

        //Then
        Throwable exception = assertThrows(BadServiceCallException.class, () -> productService.addProduct(productModel , anyString()));
        Assertions.assertEquals(errorMessage, exception.getMessage());

    }

    @Test
    public void testAddProduct_Successful() {
        //Given
        final ProductModel productModel = ProductModel.builder().productName("Water").build();
        final Product product = Product.builder()
                .amountAvailable(productModel.getAmountAvailable())
                .sellerId(productModel.getSellerId())
                .cost(productModel.getCost())
                .productName(productModel.getProductName())
                .id(productModel.getId())
                .build();

        final User user = User.builder().username("user").role("seller").build();

        //When
        Mockito.when(userRepository.findByUsername(anyString())).thenReturn(Optional.of(user));
        Mockito.when(productRepository.save(any())).thenReturn(product);

        //Then
        ProductModel returnedProductModel  = productService.addProduct(productModel, user.getUsername());
        Assertions.assertEquals(returnedProductModel.getId(), productModel.getId());
        Assertions.assertEquals(returnedProductModel.getProductName(), productModel.getProductName());
        Assertions.assertEquals(returnedProductModel.getSellerId(), productModel.getSellerId());
        Assertions.assertEquals(returnedProductModel.getCost(), productModel.getCost());
    }

    @Test
    void testUpdateProduct_NoSuchUser() {
        //Given
        final String errorMessage = "no such user";
        final String id = "1";
        final ProductModel product = ProductModel.builder().build();

        //When
        Mockito.when(userRepository.findByUsername(anyString())).thenReturn(Optional.empty());

        //Then
        Throwable exception = assertThrows(BadServiceCallException.class, () -> productService.updateProduct(id, product, anyString()));
        Assertions.assertEquals(errorMessage, exception.getMessage());
    }

    @Test
    void testUpdateProduct_NoSuchProductId() {
        //Given
        final User user = User.builder().id(1).role("seller").build();
        final String errorMessage = "no such product id";
        final String id = "1";
        final ProductModel product = ProductModel.builder().sellerId(1).build();

        //When
        Mockito.when(userRepository.findByUsername(anyString())).thenReturn(Optional.of(user));
        Mockito.when(productRepository.findById(anyInt())).thenReturn(Optional.empty());

        //Then
        Throwable exception = assertThrows(BadServiceCallException.class, () -> productService.updateProduct(id, product, anyString()));
        Assertions.assertEquals(errorMessage, exception.getMessage());

    }

    @Test
    void testUpdateProduct_NoSellerRole() {
        //Given
        final String errorMessage = "no seller role found";
        final User user = User.builder().role("buyer").build();
        final ProductModel productModel = ProductModel.builder().build();
        final String id = "1";

        //When
        Mockito.when(userRepository.findByUsername(anyString())).thenReturn(Optional.of(user));

        //Then
        Throwable exception = assertThrows(BadServiceCallException.class, () -> productService.updateProduct(id, productModel , anyString()));
        Assertions.assertEquals(errorMessage, exception.getMessage());

    }

    @Test
    void testUpdateProduct_Successful() {
        //Given
        final String id = "1";
        final User user = User.builder().role("seller").id(1).build();
        final ProductModel productModel = ProductModel.builder().productName("Vodka").sellerId(1).build();
        final Product product = Product.builder()
                .amountAvailable(productModel.getAmountAvailable())
                .sellerId(productModel.getSellerId())
                .cost(productModel.getCost())
                .productName(productModel.getProductName())
                .id(productModel.getId())
                .build();

        //When
        Mockito.when(userRepository.findByUsername(anyString())).thenReturn(Optional.of(user));
        Mockito.when(productRepository.findById(anyInt())).thenReturn(Optional.of(product));
        Mockito.when(productRepository.save(product)).thenReturn(product);

        //Then
        ProductModel returnedProductModel = productService.updateProduct(id, productModel, anyString());
        Assertions.assertEquals(returnedProductModel.getProductName(), productModel.getProductName());
        Assertions.assertEquals(returnedProductModel.getId(), productModel.getId());
        Assertions.assertEquals(returnedProductModel.getProductName(), productModel.getProductName());
        Assertions.assertEquals(returnedProductModel.getSellerId(), productModel.getSellerId());
        Assertions.assertEquals(returnedProductModel.getCost(), productModel.getCost());
    }

    @Test
    void testDeleteProduct_NoSuchUser() {
        //Given
        final String id = "1";
        final String errorMessage = "no such user";

        //When
        Mockito.when(userRepository.findByUsername(anyString())).thenReturn(Optional.empty());

        //Then
        Throwable exception = assertThrows(BadServiceCallException.class, () -> productService.deleteProduct(id, anyString()));
        Assertions.assertEquals(errorMessage, exception.getMessage());
    }

    @Test
    void testDeleteProduct_NoSuchProductId() {
        //Given
        final User user = User.builder().id(1).role("seller").build();
        final String errorMessage = "no such product id";
        final String id = "1";

        //When
        Mockito.when(userRepository.findByUsername(anyString())).thenReturn(Optional.of(user));
        Mockito.when(productRepository.findById(anyInt())).thenReturn(Optional.empty());

        //Then
        Throwable exception = assertThrows(BadServiceCallException.class, () -> productService.deleteProduct(id, anyString()));
        Assertions.assertEquals(errorMessage, exception.getMessage());
    }


    @Test
    void testDeleteProduct_NoSellerRole() {
        //Given
        final String errorMessage = "no seller role found";
        final User user = User.builder().role("buyer").build();
        final String id = "1";

        //When
        Mockito.when(userRepository.findByUsername(anyString())).thenReturn(Optional.of(user));

        //Then
        Throwable exception = assertThrows(BadServiceCallException.class, () -> productService.deleteProduct(id, anyString()));
        Assertions.assertEquals(errorMessage, exception.getMessage());

    }

}
