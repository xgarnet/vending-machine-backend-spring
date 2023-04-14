package de.ass37.examples.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.ass37.examples.models.UserModel;
import de.ass37.examples.repository.JWTokenRepository;
import de.ass37.examples.services.DepositService;
import de.ass37.examples.services.LoginService;
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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(value = DepositController.class)
public class DepositControllerTests {
    @Autowired
    private MockMvc mvc;

    @MockBean
    private DepositService depositService;
    @MockBean
    private LoginService loginService;


    @MockBean
    private JWTokenRepository jwTokenRepository;

    @WithMockUser
    @Test
    public void testGetDeposit() throws Exception {
        //Given
        ObjectMapper objectMapper = new ObjectMapper();
        final UserModel userModel = UserModel.builder().build();
        final String username = "user";

        //When
        Mockito.when(depositService.getDeposit(anyString())).thenReturn(userModel);
        Mockito.when(loginService.extractUsername(any())).thenReturn(username);

        //Then
        MvcResult result = mvc.perform(MockMvcRequestBuilders.get("/api/deposit")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer  test")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isFound()).andReturn();

        final String returnedContent =  result.getResponse().getContentAsString();
        final String expectedContent = objectMapper.writeValueAsString(userModel);

        Assertions.assertEquals(expectedContent, returnedContent);
    }

    @WithMockUser
    @Test
    public void testAddToDeposit_NotFound() throws Exception {
        //When
        Mockito.when(depositService.addToDepositByUser(anyString(), anyString())).thenThrow(BadServiceCallException.class);
        Mockito.when(loginService.extractUsername(anyString())).thenReturn("user");

        //Then
        mvc.perform(MockMvcRequestBuilders.post("/api/deposit/5").with(csrf())
                        .content("{}")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .header(HttpHeaders.AUTHORIZATION, "Bearer  test")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

    }

    @WithMockUser
    @Test
    public void testAddToDeposit_BadRequest() throws Exception {
        //When
        Mockito.when(depositService.addToDepositByUser(anyString(), anyString())).thenThrow(NumberFormatException.class);
        Mockito.when(loginService.extractUsername(anyString())).thenReturn("user");
        //Then
        mvc.perform(MockMvcRequestBuilders.post("/api/deposit/5").with(csrf())
                        .content("{}")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .header(HttpHeaders.AUTHORIZATION, "Bearer  test")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @WithMockUser
    @Test
    public void testAddToDeposit_Successful() throws Exception {
        //Given
        final UserModel userModel = UserModel.builder()
                .id(0)
                .deposit(120)
                .password("passws")
                .role("seller")
                .build();
        ObjectMapper objectMapper = new ObjectMapper();
        String username = "user";

        //When
        Mockito.when(loginService.extractUsername(any())).thenReturn(username);
        Mockito.when(depositService.addToDepositByUser(anyString(), anyString())).thenReturn(userModel);

        //Then
        MvcResult result = mvc.perform(MockMvcRequestBuilders.post("/api/deposit/5").with(csrf())
                        .header(HttpHeaders.AUTHORIZATION, "Bearer  test")
                        .content("{}")
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk()).andReturn();

        final String returnedContent =  result.getResponse().getContentAsString();
        final String expectedContent = objectMapper.writeValueAsString(userModel);
        Assertions.assertEquals(expectedContent, returnedContent);
    }
}
