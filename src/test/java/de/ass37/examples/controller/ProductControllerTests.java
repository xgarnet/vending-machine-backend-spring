package de.ass37.examples.controller;

import de.ass37.examples.services.LoginService;
import de.ass37.examples.services.ProductService;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Collections;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = ProductController.class)
@AutoConfigureMockMvc
public class ProductControllerTests {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private ProductService productService;

    @MockBean
    private LoginService loginService;

    @Disabled
    @Test
    public void getAllProducts() throws Exception {

        Mockito.when(productService.getAllProducts()).thenReturn(Collections.emptyList());

        mvc.perform(MockMvcRequestBuilders.get("/api/product").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}
