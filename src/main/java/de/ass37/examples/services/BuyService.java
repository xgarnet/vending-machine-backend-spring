package de.ass37.examples.services;

import de.ass37.examples.entities.Product;
import de.ass37.examples.entities.User;
import de.ass37.examples.models.BuyReqModel;
import de.ass37.examples.models.BuyRespModel;
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

    public BuyRespModel buyByUser(BuyReqModel buyReqModel, String username) {

        User user = userRepository.findByUsername(username).orElseThrow(() -> new RuntimeException("no such user found"));
        if(user.getRole().equalsIgnoreCase("buyer")) {
            Product product = productRepository.findById(buyReqModel.getProductId()).orElseThrow(() -> new RuntimeException("Kein Product mit dieser ID"));
            if(product.getAmountAvailable() < buyReqModel.getMenge()) {
                throw  new RuntimeException("Not Enoght products available");
            }
            if(user.getDeposit() < product.getCost() * buyReqModel.getMenge()) {
                throw new RuntimeException("Not enoght deposit");
            }
            product.setAmountAvailable(product.getAmountAvailable() - buyReqModel.getMenge());
            product = productRepository.save(product);
            user.setDeposit(user.getDeposit() - product.getCost() * buyReqModel.getMenge());
            user = userRepository.save(user);
            BuyRespModel buyRespModel = new BuyRespModel();
            buyRespModel.setChanges(user.getDeposit());
            buyRespModel.setMessage("Susseccful");
            return buyRespModel;
        } else {
            throw new RuntimeException("no role buyer found");
        }

    }

}
