package de.ass37.examples.models;

import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductModel {
    private Integer id;

    private Integer amountAvailable;
    private Integer cost;
    private String productName;
    private Integer sellerId;

}
