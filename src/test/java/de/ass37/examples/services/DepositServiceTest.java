package de.ass37.examples.services;

import de.ass37.examples.entities.User;
import de.ass37.examples.models.UserModel;
import de.ass37.examples.repository.UserRepository;
import de.ass37.examples.services.exceptions.BadServiceCallException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class DepositServiceTest {

    @Autowired
    private DepositService depositService;

    @MockBean
    private UserRepository userRepository;


    @Test
    public void testAddToDepositNoSuchUsername() {
        //Given
        final String errorMessage = "no such user found";
        //When
        Mockito.when(userRepository.findByUsername(anyString())).thenReturn(Optional.empty());
        //Then
        Throwable exception = assertThrows(BadServiceCallException.class, () -> depositService.addToDepositByUser("user", "5"));
        Assertions.assertEquals(errorMessage, exception.getMessage());
    }


    @Test
    public void testAddToDepositNoSuchCoin() {
        //Given
        final String errorMessage = "no such coin allowed";
        final User user = User.builder().deposit(0).build();

        //When
        Mockito.when(userRepository.findByUsername(anyString())).thenReturn(Optional.of(user));
        //Then
        Throwable exception = assertThrows(BadServiceCallException.class, () -> depositService.addToDepositByUser("user", "15"));
        Assertions.assertEquals(errorMessage, exception.getMessage());
    }

    @Test
    public void testAddToDepositSuccessful() {
        //Given
        final User user = User.builder().deposit(10).build();

        //When
        Mockito.when(userRepository.findByUsername(anyString())).thenReturn(Optional.of(user));
        Mockito.when(userRepository.save(user)).thenReturn(user);

        UserModel userModel = depositService.addToDepositByUser("user", "10");
        assertEquals(userModel.getDeposit(), 20);
    }





}
