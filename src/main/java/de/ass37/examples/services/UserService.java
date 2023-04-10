package de.ass37.examples.services;

import de.ass37.examples.entities.User;
import de.ass37.examples.models.UserModel;
import de.ass37.examples.repository.UserRepository;
import de.ass37.examples.services.exceptions.BadServiceCallException;
import de.ass37.examples.services.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper mapper;

    public List<UserModel> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(user -> mapper.map(user, UserModel.class))
                .toList();
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public UserModel getUserById(String id) {
        return userRepository.findById(Integer.parseInt(id))
                .map(user -> mapper.map(user, UserModel.class))
                .orElseThrow(() -> new BadServiceCallException("User with id: " + id + " not found"));
    }

    public UserModel addUser(UserModel userModel) {
        User savedUser =  userRepository.save(mapper.map(userModel, User.class));
        return mapper.map(savedUser, UserModel.class);
    }

    public UserModel updateUser(String id, UserModel userModel) {
        if(userRepository.existsById(Integer.parseInt(id))) {
            userModel.setId(Long.parseLong(id));
            User user = mapper.map(userModel, User.class);
            User savedUser = userRepository.save(user);
            UserModel savedModel = mapper.map(savedUser, UserModel.class);
            return savedModel;
        } else {
            throw new BadServiceCallException("User with id: " + id + " not found");
        }
    }

    public void deleteUser(String id) {
        if(userRepository.existsById(Integer.parseInt(id))) {
            userRepository.deleteById(Integer.parseInt(id));
        } else {
           throw new BadServiceCallException("User with id: " + id + " not found");
        }
    }
}
