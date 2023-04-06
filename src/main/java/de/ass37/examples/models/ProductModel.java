package de.ass37.examples.models;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductModel {
    private Integer id;

    private Integer amountAvailable;
    private Integer cost;
    private String productName;
    private Integer sellerId;

}
