package de.ass37.examples.models;


import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class BuyModel {
    private Integer userId;
    private Long productId;
    private int menge;
    private List<Integer> changes;
    private String message;
    private Long totalSpent;
}
