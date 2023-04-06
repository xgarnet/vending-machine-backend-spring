package de.ass37.examples.services;

import de.ass37.examples.entities.User;
import de.ass37.examples.models.UserModel;
import de.ass37.examples.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DepositService {

    @Autowired
    private  UserRepository userRepository;

    @Autowired
    private ModelMapper mapper;

    public DepositService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserModel addToDepositByUser(String username, String coin) {
        User user = userRepository.findByUsername(username).orElseThrow(() -> new RuntimeException("no such username"));

        List<Integer> coins = new ArrayList<>();
        coins.add(5);
        coins.add(10);
        coins.add(20);
        coins.add(50);
        coins.add(100);

        if(coins.contains(Integer.parseInt(coin))) {
            user.setDeposit(user.getDeposit() + Integer.parseInt(coin));
            User savedUser = userRepository.save(user);
            return mapper.map(savedUser, UserModel.class);
        } else {
            throw new RuntimeException("no such coin allowed");
        }
    }

    public UserModel getDeposit(String username) {
         User user = userRepository.findByUsername(username).orElseThrow(() -> new RuntimeException("no such username"));
         return mapper.map(user, UserModel.class);
    }
}
