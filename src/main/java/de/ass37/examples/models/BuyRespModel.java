package de.ass37.examples.models;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class BuyRespModel {
    Integer spent;
    private List<Integer> changes;
    private String message;
}
