package de.ass37.examples.services;

import de.ass37.examples.entities.Product;
import de.ass37.examples.entities.User;
import de.ass37.examples.models.BuyModel;
import de.ass37.examples.repository.ProductRepository;
import de.ass37.examples.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class BuyService {
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    public BuyService(ProductRepository productRepository,
                      UserRepository userRepository) {
        this.productRepository = productRepository;
        this.userRepository = userRepository;
    }

    public BuyModel buyByUser(BuyModel buyModel) {
        Product product = productRepository.findById(buyModel.getProductId()).orElseThrow(() -> new RuntimeException("Kein Product mit dieser ID"));
        if(product.getAmountAvailable() < buyModel.getMenge()) {
            throw  new RuntimeException("Not Enoght products available");
        }

        User user = userRepository.findById(buyModel.getUserId()).orElseThrow(() -> new RuntimeException("User not found"));

        if(user.getDeposit() < product.getCost() * buyModel.getMenge()) {
            throw new RuntimeException("Not enoght deposit");
        }

        product.setAmountAvailable(product.getAmountAvailable() - buyModel.getMenge());
        user.setDeposit(user.getDeposit() - product.getCost() * buyModel.getMenge());
        buyModel.setChanges(calculateChanges(user.getDeposit()));
        buyModel.setMessage("Purchase successful");

        return buyModel;
    }

    private List<Long> calculateChanges(Long deposit) {
        long[] coins = new long[] {100, 50, 20, 10, 5};
        List<Long> changes = Collections.emptyList();
        for (long coin : coins) {
            while (deposit >= coin) {
                changes.add(coin);
                deposit -= coin;
            }
        }
        return changes;
    }

}
