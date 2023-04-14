package de.ass37.examples.services;

import de.ass37.examples.entities.User;
import de.ass37.examples.models.UserModel;
import de.ass37.examples.repository.UserRepository;
import de.ass37.examples.services.exceptions.BadServiceCallException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ResetService {
    private final UserRepository userRepository;

    @Autowired
    private ModelMapper mapper;

    public ResetService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserModel resetDeposit(String username) {
        User user = userRepository.findByUsername(username).orElseThrow(() -> new BadServiceCallException("no such username found"));
        if(user.getRole().equalsIgnoreCase("buyer")) {
            user.setDeposit(0);
            User savedUser = userRepository.save(user);
            return mapper.map(savedUser, UserModel.class);
        } else  {
            throw new BadServiceCallException("no byuer role found");
        }

    }
}
