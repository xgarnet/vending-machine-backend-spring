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

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = {ResetService.class, ModelMapper.class})
@AutoConfigureMockMvc
public class ResetServiceTests {
    @Autowired
    private ResetService resetService;
    @MockBean
    private UserRepository userRepository;

    @Test
    void testResetDeposit_NoUsername() {
        //Given
        final String  errorMessage = "no such username found";
        final String username = "username";

        //When
        Mockito.when(userRepository.findByUsername(any())).thenReturn(Optional.empty());

        //Then
        Throwable exception = assertThrows(BadServiceCallException.class, () -> resetService.resetDeposit(username));
        Assertions.assertEquals(errorMessage, exception.getMessage());
    }

    @Test
    void testResetDeposit_NoBuyerRole() {
        //Given
        final String  errorMessage = "no byuer role found";
        final String username = "username";
        final User user = User.builder()
                .username(username)
                .role("testrolle")
                .build();

        //When
        Mockito.when(userRepository.findByUsername(any())).thenReturn(Optional.of(user));

        //Then
        Throwable exception = assertThrows(BadServiceCallException.class, () -> resetService.resetDeposit(username));
        Assertions.assertEquals(errorMessage, exception.getMessage());
    }

    @Test
    void testResetDeposit_Successfull() {
        //Given
        final String username = "username";
        final User user = User.builder()
                .username(username)
                .role("buyer")
                .build();
        final UserModel userModel = UserModel.builder()
                .username(username)
                .role("buyer")
                .build();

        //When
        Mockito.when(userRepository.findByUsername(any())).thenReturn(Optional.of(user));
        Mockito.when(userRepository.save(user)).thenReturn(user);

        //Then
        final UserModel returnedUserModel = resetService.resetDeposit(username);
        Assertions.assertEquals(returnedUserModel.getRole(), userModel.getRole());
        Assertions.assertEquals(returnedUserModel.getUsername(), userModel.getUsername());
    }

}
