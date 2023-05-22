package de.ass37.examples.services;


import de.ass37.examples.entities.User;
import de.ass37.examples.models.UserModel;
import de.ass37.examples.repository.UserRepository;
import de.ass37.examples.services.exceptions.BadServiceCallException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = {UserService.class, ModelMapper.class})
@AutoConfigureMockMvc
public class UserServiceTests {
    @Autowired
    private UserService userService;
    @MockBean
    private UserRepository userRepository;

    @Test
    void testGetAllUsers() {
        when(userRepository.findAll()).thenReturn(Collections.emptyList());
        Assertions.assertEquals(Collections.emptyList(), userService.getAllUsers());
    }

    @Test
    void testGetUserById_UserNotFound() {
        //Given
        final String errorMessage = "User with id: 1 not found";

        //Then
        when(userRepository.findById(anyInt())).thenReturn(Optional.empty());

        //Then
        Throwable exception = assertThrows(BadServiceCallException.class, () -> userService.getUserById("1"));
        Assertions.assertEquals(errorMessage, exception.getMessage());
    }

    @Test
    void testGetUserById_Successful() {
        //Given
        final User user = User.builder().id(1).username("user").role("role").build();

        //When
        when(userRepository.findById(anyInt())).thenReturn(Optional.of(user));
        UserModel userModel = userService.getUserById("1");

        //Then
        assertEquals(userModel.getUsername(), user.getUsername());
        assertEquals(userModel.getId(), user.getId());
        assertEquals(userModel.getRole(), user.getRole());
    }

    @Test
    void testUpdateUser_NoSuchUser() {
        //Given
        final String id = "1";
        final String errorMessage = "User with id: 1 not found";
        final UserModel userModel = UserModel.builder().build();

        //When
        Mockito.when(userRepository.findByUsername(anyString())).thenReturn(Optional.empty());

        //Then
        Throwable exception = assertThrows(BadServiceCallException.class, () -> userService.updateUser(id, userModel));
        Assertions.assertEquals(errorMessage, exception.getMessage());
    }

    @Test
    void testUpdateUser_Successful() {
        //Given
        final String username = "username";
        final String id = "1";
        User user = User.builder()
                .username(username)
                .deposit(10)
                .role("testrole")
                .password("geheim")
                .id(Integer.parseInt(id))
                .build();

        UserModel userModel = UserModel.builder()
                .username(username)
                .deposit(10)
                .role("testrole")
                .password("geheim")
                .id(Integer.parseInt(id))
                .build();

        //When
        Mockito.when(userRepository.findByUsername(username)).thenReturn(Optional.of(user));
        Mockito.when(userRepository.existsById(Integer.parseInt(id))).thenReturn(true);
        Mockito.when(userRepository.save(any())).thenReturn(user);

        //Then
        UserModel returnedUserModel =  userService.updateUser(id, userModel);

        Assertions.assertEquals(user.getId(), returnedUserModel.getId());
        Assertions.assertEquals(user.getDeposit(), returnedUserModel.getDeposit());
        Assertions.assertEquals(user.getRole(), returnedUserModel.getRole());
        Assertions.assertEquals(user.getPassword(), returnedUserModel.getPassword());

    }

    @Test
    void testDeleteUser_NoSuchUser() {
        //Given
        final String id = "1";
        final String errorMessage = "User with id: 1 not found";

        //When
        Mockito.when(userRepository.findByUsername(anyString())).thenReturn(Optional.empty());

        //Then
        Throwable exception = assertThrows(BadServiceCallException.class, () -> userService.deleteUser(id));
        Assertions.assertEquals(errorMessage, exception.getMessage());
    }



}
