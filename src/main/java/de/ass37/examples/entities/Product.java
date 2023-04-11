package de.ass37.examples.entities;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.relational.core.sql.In;

@Builder
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Product {
    @Id
    @GeneratedValue(generator="product_sequence")
    @SequenceGenerator(name="product_sequence",sequenceName="product_sequence", allocationSize=1)
    @Column(name = "id", nullable = false)
    private Integer id;

    private Integer amountAvailable;
    private Integer cost;
    private String productName;
    private Integer sellerId;

}
