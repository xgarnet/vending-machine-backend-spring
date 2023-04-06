package de.ass37.examples.controller;

import de.ass37.examples.models.ProductModel;
import de.ass37.examples.services.LoginService;
import de.ass37.examples.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ProductController {
    @Autowired
    private ProductService productService;

    @Autowired
    private LoginService loginService;

    @GetMapping(value = "/product", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ProductModel>> getProducts() {
        return  new ResponseEntity<>(productService.getAllProducts(), HttpStatus.OK);
    }
    @GetMapping(value = "/product/{id}")
    public ResponseEntity<ProductModel> getProductById(@PathVariable("id") String id) {
        return  new ResponseEntity<>(productService.getProductById(id), HttpStatus.OK);
    }

    @PostMapping(value = "/product", produces = MediaType.APPLICATION_JSON_VALUE, consumes =  MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ProductModel> addProduct(@RequestHeader(HttpHeaders.AUTHORIZATION) String autorization, @RequestBody ProductModel productModel) {
        String username =  loginService.extractUsername(autorization.substring(7));
        return new ResponseEntity<>(productService.addProduct(productModel, username), HttpStatus.OK);
    }

    @PutMapping(value = "/product/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ProductModel> updateProduct(@RequestHeader(HttpHeaders.AUTHORIZATION) String autorization, @PathVariable("id") String id, @RequestBody ProductModel productModel) {
        String username =  loginService.extractUsername(autorization.substring(7));
        return new ResponseEntity<>(productService.updateProduct(id, productModel, username), HttpStatus.FOUND);
    }

    @DeleteMapping(value = "/product/{id}")
    public ResponseEntity deleteProduct(@RequestHeader(HttpHeaders.AUTHORIZATION) String autorization, @PathVariable String id) {
        String username =  loginService.extractUsername(autorization.substring(7));
        productService.deleteProduct(id, username);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
