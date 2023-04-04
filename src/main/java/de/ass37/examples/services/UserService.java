package de.ass37.examples.services;

import de.ass37.examples.entities.User;
import de.ass37.examples.models.UserModel;
import de.ass37.examples.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.modelmapper.ModelMapper;

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

    public UserModel getUserById(String id) {
        return userRepository.findById(Long.getLong(id))
                .map(user -> mapper.map(user, UserModel.class))
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    public UserModel addUser(UserModel userModel) {
        User savedUser =  userRepository.save(mapper.map(userModel, User.class));
        return mapper.map(savedUser, UserModel.class);
    }

    public UserModel updateUser(String id, UserModel userModel) {
        if(userRepository.existsById(Long.getLong(id))) {
            User user = mapper.map(userModel, User.class);
            UserModel savedModel = mapper.map(user, UserModel.class);
            return savedModel;
        } else {
            throw new RuntimeException("no such id for user found");
        }
    }

    public void deleteUser(String id) {
        if(userRepository.existsById(Long.getLong(id))) {
            userRepository.deleteById(Long.getLong(id));
        } else {
            new RuntimeException("no such id for user found");
        }
    }
}
