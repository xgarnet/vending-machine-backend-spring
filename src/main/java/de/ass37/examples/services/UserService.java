package de.ass37.examples.services;

import de.ass37.examples.models.UserModel;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class UserService {
    public List<UserModel> getAllUsers() {
        return Collections.EMPTY_LIST;
    }

    public UserModel getUserById(String id) {
        return new UserModel();
    }

    public UserModel addUser(UserModel userModel) {
        return new UserModel();
    }

    public UserModel updateUser(String id, UserModel userModel) {
        return new UserModel();
    }

    public void deleteUser(String id) {
    }
}
