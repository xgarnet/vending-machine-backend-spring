package de.ass37.examples.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Product {
    @Id
    @GeneratedValue(generator="product_sequence")
    @SequenceGenerator(name="product_sequence",sequenceName="product_sequence", allocationSize=1)
    @Column(name = "id", nullable = false)
    private Long id;

    private Long amountAvailable;
    private Long cost;
    private String productName;
    private Long sellerId;

}
