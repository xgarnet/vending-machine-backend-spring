package de.ass37.examples.services;

import de.ass37.examples.entities.Product;
import de.ass37.examples.entities.User;
import de.ass37.examples.models.BuyReqModel;
import de.ass37.examples.models.BuyRespModel;
import de.ass37.examples.repository.ProductRepository;
import de.ass37.examples.repository.UserRepository;
import de.ass37.examples.services.exceptions.BadServiceCallException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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

    public BuyRespModel buyByUser(BuyReqModel buyReqModel, String username) {

        User user = userRepository.findByUsername(username).orElseThrow(() -> new BadServiceCallException("no such user found"));
        if(user.getRole().equalsIgnoreCase("buyer")) {
            Product product = productRepository.findById(buyReqModel.getProductId()).orElseThrow(() -> new BadServiceCallException("no such product id found"));
            if(product.getAmountAvailable() < buyReqModel.getMenge()) {
                throw  new BadServiceCallException("Not Enoght products available");
            }
            if(user.getDeposit() < product.getCost() * buyReqModel.getMenge()) {
                throw new BadServiceCallException("Not enoght deposit");
            }
            product.setAmountAvailable(product.getAmountAvailable() - buyReqModel.getMenge());
            product = productRepository.save(product);
            user.setDeposit(user.getDeposit() - product.getCost() * buyReqModel.getMenge());
            user = userRepository.save(user);
            BuyRespModel buyRespModel = new BuyRespModel();
            buyRespModel.setChanges(changeCoins(user.getDeposit()));
            buyRespModel.setMessage("Successful");
            return buyRespModel;
        } else {
            throw new BadServiceCallException("no role buyer found");
        }

    }

    private List<Integer> changeCoins(Integer deposit) {

        List<Integer> changes = new ArrayList<>();
        List<Integer> coins = List.of(100, 50, 20, 10, 5);

        for (Integer coin : coins) {
            while (deposit >= coin) {
                changes.add(coin);
                deposit-=coin;
            }
        }
        if(deposit > 0) changes.add(deposit);
        return  changes;
    }
}
