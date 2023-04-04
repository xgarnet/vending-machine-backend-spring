package de.ass37.examples.models;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductModel {
    private Long id;

    private Long amountAvailable;
    private Long cost;
    private String productName;
    private Long sellerId;

}
