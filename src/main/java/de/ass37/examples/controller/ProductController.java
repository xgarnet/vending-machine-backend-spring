package de.ass37.examples.controller;

import de.ass37.examples.models.ProductModel;
import de.ass37.examples.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ProductController {
    @Autowired
    private ProductService productService;

    @GetMapping(value = "/product", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ProductModel>> getProducts() {
        return  new ResponseEntity(productService.getAllProducts(), HttpStatus.OK);
    }
    @GetMapping( value = "/product/{id}")
    public ResponseEntity<ProductModel> getProductById(@PathVariable String id) {
        return  new ResponseEntity(productService.getProductById(id), HttpStatus.OK);
    }

    @PostMapping(value = "/product", produces = MediaType.APPLICATION_JSON_VALUE, consumes =  MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ProductModel> addProduct(@RequestBody ProductModel productModel) {
        return new ResponseEntity<>(productService.addProduct(productModel), HttpStatus.OK);
    }

    @PutMapping(value = "/product/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ProductModel> updateProduct(@PathVariable String id, @RequestBody ProductModel productModel) {
        return new ResponseEntity<>(productService.updateProduct(id, productModel), HttpStatus.FOUND);
    }


    @DeleteMapping(value = "/product/{id}")
    public ResponseEntity deleteProduct(@PathVariable String id) {
        productService.deleteProduct(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
