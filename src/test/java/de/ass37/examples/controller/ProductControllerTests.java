package de.ass37.examples.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.ass37.examples.models.ProductModel;
import de.ass37.examples.repository.JWTokenRepository;
import de.ass37.examples.services.LoginService;
import de.ass37.examples.services.ProductService;
import de.ass37.examples.services.exceptions.BadServiceCallException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ProductController.class)
public class ProductControllerTests {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private ProductService productService;

    @MockBean
    private LoginService loginService;

    @MockBean
    private JWTokenRepository jwTokenRepository;

    @WithMockUser
    @Test
    public void testGetAllProducts() throws Exception {
        Mockito.when(productService.getAllProducts()).thenReturn(List.of(new ProductModel()));
        mvc.perform(MockMvcRequestBuilders.get("/api/product").accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
    }

    @WithMockUser
    @Test
    public void testGetproductById_NotFound() throws Exception {
        Mockito.when(productService.getProductById(anyString())).thenThrow(BadServiceCallException.class);
        mvc.perform(MockMvcRequestBuilders.get("/api/product/1")).andExpect(status().isNotFound());
    }

    @WithMockUser
    @Test
    public void testGetproductById_BadRequest() throws Exception {
        Mockito.when(productService.getProductById(anyString())).thenThrow(NumberFormatException.class);
        mvc.perform(MockMvcRequestBuilders.get("/api/product/1")).andDo(print()).andExpect(status().isBadRequest());
    }

    @WithMockUser
    @Test
    public void testGetproductById_Success() throws Exception {
        //Given
        final ProductModel productModel = ProductModel.builder().build();
        Mockito.when(productService.getProductById(anyString())).thenReturn(productModel);
        MvcResult result = mvc.perform(MockMvcRequestBuilders.get("/api/product/1"))
                .andExpect(status().isOk()).andReturn();

        ObjectMapper objectMapper = new ObjectMapper();
        final  String returnedContent = result.getResponse().getContentAsString();
        final String expectedContent = objectMapper.writeValueAsString(productModel);
        Assertions.assertEquals(expectedContent, returnedContent);
    }

    @WithMockUser
    @Test
    public void testAddProduct_NotFound() throws Exception {

        Mockito.when(loginService.extractUsername(anyString())).thenReturn("user");
        Mockito.when(productService.addProduct(any(), anyString())).thenThrow(BadServiceCallException.class);

        mvc.perform(MockMvcRequestBuilders.post("/api/product").with(csrf())
                                .header(HttpHeaders.AUTHORIZATION, "Bearer test")
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                .content("{}")
                )
                .andDo(print())
                .andExpect(status().isNotFound());
    }


    @WithMockUser
    @Test
    public void testAddProduct_BadRequest() throws Exception {

        Mockito.when(loginService.extractUsername(anyString())).thenReturn("user");
        Mockito.when(productService.addProduct(any(), anyString())).thenThrow(NumberFormatException.class);

        mvc.perform(MockMvcRequestBuilders.post("/api/product").with(csrf())
                        .header(HttpHeaders.AUTHORIZATION, "Bearer test")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content("{}")
                )
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @WithMockUser
    @Test
    public void testAddProduct_Successful() throws Exception {
        //Given
        final ProductModel productModel = ProductModel.builder().build();
        final String username = "user";

        Mockito.when(productService.addProduct(productModel, username)).thenReturn(productModel);
        Mockito.when(loginService.extractUsername(username)).thenReturn(username);
        ObjectMapper objectMapper = new ObjectMapper();
        MvcResult result = mvc.perform(MockMvcRequestBuilders.post("/api/product").with(csrf())
                        .header(HttpHeaders.AUTHORIZATION, "Bearer test")
                        .content(objectMapper.writeValueAsString(productModel))
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk()).andReturn();

        // todo
        //final  String returnedContent = result.getResponse().getContentAsString(); objectMapper.writeValueAsString(productModel);
        //final String expectedContent =  objectMapper.writeValueAsString(productModel);
        //Assertions.assertEquals(expectedContent, returnedContent);
    }

    @WithMockUser
    @Test
    public void testUpdateProduct_NotFound() throws Exception {
        // Given
        final ProductModel productModel = ProductModel.builder().build();
        final String username = "user";
        final String id = "1";

        // When
        Mockito.when(productService.updateProduct(id, productModel,  username)).thenThrow(BadServiceCallException.class);

        //Then
        mvc.perform(MockMvcRequestBuilders.put("/api/product/{id}", id)
                        .with(csrf())
                        .content("{}")
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isBadRequest());
    }

    @WithMockUser
    @Test
    public void testUpdateProduct_BadRequest() throws Exception {
        Mockito.when(productService.getProductById(anyString())).thenThrow(NumberFormatException.class);
        mvc.perform(MockMvcRequestBuilders.put("/api/product/1").with(csrf()).content("{}")
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isBadRequest());
    }


    @WithMockUser
    @Test
    public void testUpdateProduct_Successful() throws Exception {
        //Given
        final ProductModel productModel = ProductModel.builder().productName("Tea")
                .amountAvailable(100)
                .cost(25).sellerId(1)
                .build();

        //When
        ObjectMapper objectMapper = new ObjectMapper();
        Mockito.when(productService.updateProduct("1", productModel, "user")).thenReturn(productModel);
        Mockito.when(loginService.extractUsername(anyString())).thenReturn("user");

        //Then
        MvcResult result = mvc.perform(MockMvcRequestBuilders.put("/api/product/1").with(csrf())
                        .header(HttpHeaders.AUTHORIZATION, "Bearer test")
                        //.content(objectMapper.writeValueAsString(productModel))
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content("{}")
                )
                .andDo(print())
                //.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                //.andExpect(content().json("{}"))
                .andExpect(status().isFound()).andReturn();

        // todo
       final  String returnedContent =  result.getResponse().getContentAsString();
       final String expectedContent = objectMapper.writeValueAsString(productModel);

       // Assertions.assertEquals(expectedContent, returnedContent);
    }

    @WithMockUser
    @Test
    public void testDeleteProduct_NotFound() throws Exception {
        //Given
        final String username = "user";
        //When
        Mockito.when(loginService.extractUsername(anyString())).thenReturn(username);
        Mockito.doThrow(new BadServiceCallException("")).when(productService).deleteProduct("1", username);

        //Then
        mvc.perform(MockMvcRequestBuilders.delete("/api/product/{id}", 1).with(csrf())
                        .header(HttpHeaders.AUTHORIZATION, "Bearer test"))
                .andExpect(status().isNotFound());
    }

    @WithMockUser
    @Test
    public void testDeleteProduct_BadRequest() throws Exception {
        //Given
        final String username = "user";

        //When
        Mockito.when(loginService.extractUsername(anyString())).thenReturn(username);
        Mockito.doThrow(new NumberFormatException()).when(productService).deleteProduct("1", username);
        //Then
        mvc.perform(MockMvcRequestBuilders.delete("/api/product/{id}", 1).with(csrf())
                        .header(HttpHeaders.AUTHORIZATION, "Bearer test"))
                .andExpect(status().isBadRequest());
    }

    @WithMockUser
    @Test
    public void testDeleteProduct_Successful() throws Exception {
         mvc.perform(MockMvcRequestBuilders.delete("/api/product/1").with(csrf())
                         .header(HttpHeaders.AUTHORIZATION, "Bearer test"))
                .andExpect(status().isNoContent());
    }

}
