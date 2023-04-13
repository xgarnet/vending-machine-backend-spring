package de.ass37.examples.services;

import de.ass37.examples.entities.Product;
import de.ass37.examples.entities.User;
import de.ass37.examples.models.BuyReqModel;
import de.ass37.examples.models.BuyRespModel;
import de.ass37.examples.repository.ProductRepository;
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

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = {BuyService.class})
@AutoConfigureMockMvc
public class BuyServiceTest {

    @Autowired
    private BuyService buyService;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private ProductRepository productRepository;

    @Test
    public void testNoSuchUserFound() {
        //Given
        final String errorMessage = "no such user found";
        // When
        Mockito.when(userRepository.findByUsername(anyString())).thenThrow(new BadServiceCallException(errorMessage));
        //Then
        Throwable exception = assertThrows(BadServiceCallException.class, () -> buyService.buyByUser(new BuyReqModel(), anyString()));
        Assertions.assertEquals(errorMessage, exception.getMessage());
    }

    @Test
    public void testNoSuchRole() {
        //Given
        final String errorMessage = "no role buyer found";
        User user = User.builder().username("user").password("user").role("seller").build();

        //When
        Mockito.when(userRepository.findByUsername(anyString())).thenReturn(Optional.of(user));


        //Then
        Throwable exception = assertThrows(BadServiceCallException.class, () -> buyService.buyByUser(new BuyReqModel(), anyString()));
        Assertions.assertEquals(errorMessage, exception.getMessage());

    }

    @Test
    public void testNoSuchProductId() {
        // Given
        final String errorMessage = "no such product id found";
        final  BuyReqModel buyReqModel = new BuyReqModel();
        buyReqModel.setProductId(3);
        final User user = User.builder().username("user").password("user").role("buyer").build();

        //When
        Mockito.when(userRepository.findByUsername(anyString())).thenReturn(Optional.of(user));
        Mockito.when(productRepository.findById(anyInt())).thenReturn(Optional.empty());

        //Then
        Throwable exception = assertThrows(BadServiceCallException.class, () -> buyService.buyByUser(buyReqModel, anyString()));
        Assertions.assertEquals(errorMessage, exception.getMessage());
    }

    @Test
    public void testNotEnoghtProductsAvailable() {
        // Given
        final String errorMessage = "Not Enoght products available";
        final User user = User.builder().username("user").password("user").role("buyer").build();
        Product product = Product.builder().id(1).amountAvailable(2).build();
        BuyReqModel buyReqModel = new BuyReqModel();
        buyReqModel.setMenge(3);
        buyReqModel.setProductId(1);

        //When
        Mockito.when(userRepository.findByUsername(anyString())).thenReturn(Optional.of(user));
        Mockito.when(productRepository.findById(anyInt())).thenReturn(Optional.of(product));

        //Then
        Throwable exception = assertThrows(BadServiceCallException.class, () ->buyService.buyByUser(buyReqModel,anyString()));
        Assertions.assertEquals(errorMessage, exception.getMessage());
    }


    @Test
    public void testNotEnoghtDepositAvailable() {

        //Given
        final String errorMessage = "Not enoght deposit";

        User user = User.builder().role("buyer").deposit(10).build();
        Product product = Product.builder().id(1).amountAvailable(1).cost(11).build();
        final BuyReqModel buyReqModel = new BuyReqModel();
        buyReqModel.setMenge(1);
        buyReqModel.setProductId(1);

        // When
        Mockito.when(userRepository.findByUsername(anyString())).thenReturn(Optional.of(user));
        Mockito.when(productRepository.findById(anyInt())).thenReturn(Optional.of(product));

        // Then
        Throwable exception = assertThrows(BadServiceCallException.class, () ->buyService.buyByUser(buyReqModel,anyString()));
        Assertions.assertEquals(errorMessage, exception.getMessage());
    }

    @Test
    public void testBuySuccessful() {

        //Given
        final BuyReqModel buyReqModel = new BuyReqModel();
        buyReqModel.setMenge(2);
        buyReqModel.setProductId(1);
        final Product product = Product.builder().amountAvailable(3).cost(20).build();
        final User user = User.builder().deposit(45).role("buyer").build();

        //When
        Mockito.when(userRepository.findByUsername(anyString())).thenReturn(Optional.of(user));
        Mockito.when(userRepository.save(user)).thenReturn(user);
        Mockito.when(productRepository.findById(anyInt())).thenReturn(Optional.of(product));
        Mockito.when(productRepository.save(product)).thenReturn(product);

        //Then
        final BuyRespModel buyRespModel = buyService.buyByUser(buyReqModel, anyString());
        assertEquals("Successful", buyRespModel.getMessage());
        assertEquals(List.of(5), buyRespModel.getChanges());
    }
}
