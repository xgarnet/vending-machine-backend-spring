package de.ass37.examples.services;

import de.ass37.examples.models.BuyModel;
import org.springframework.stereotype.Service;

@Service
public class BuyService {
    public BuyModel buyByUser(BuyModel buyModel) {
        return buyModel;
    }
}
