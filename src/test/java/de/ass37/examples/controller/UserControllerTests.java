package de.ass37.examples.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.ass37.examples.models.ProductModel;
import de.ass37.examples.models.UserModel;
import de.ass37.examples.repository.JWTokenRepository;
import de.ass37.examples.services.LoginService;
import de.ass37.examples.services.UserService;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
public class UserControllerTests {
    @MockBean
    private UserService userService;
    @Autowired
    private MockMvc mvc;
    @MockBean
    private LoginService loginService;
    @MockBean
    private JWTokenRepository jwTokenRepository;

    @WithMockUser
    @Test
    public void testGetUsers() throws Exception {
        Mockito.when(userService.getAllUsers()).thenReturn(List.of(new UserModel()));
        mvc.perform(MockMvcRequestBuilders.get("/api/user").accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
    }

    @WithMockUser
    @Test
    public void testGetUserById_NotFound() throws Exception {
        Mockito.when(userService.getUserById(anyString())).thenThrow(BadServiceCallException.class);
        mvc.perform(MockMvcRequestBuilders.get("/api/user/1")).andExpect(status().isNotFound());
    }

    @WithMockUser
    @Test
    public void testGetUserById_BadRequest() throws Exception {
        Mockito.when(userService.getUserById(anyString())).thenThrow(NumberFormatException.class);
        mvc.perform(MockMvcRequestBuilders.get("/api/user/1")).andDo(print()).andExpect(status().isBadRequest());
    }

    @WithMockUser
    @Test
    public void testGetUserById_Succecc() throws Exception {
        //Given
        final UserModel userModel = UserModel.builder().build();

        //When
        Mockito.when(userService.getUserById(anyString())).thenReturn(userModel);
        MvcResult result = mvc.perform(MockMvcRequestBuilders.get("/api/user/1"))
                .andExpect(status().isOk()).andReturn();

        ObjectMapper objectMapper = new ObjectMapper();
        final  String returnedContent = result.getResponse().getContentAsString();
        final String expectedContent = objectMapper.writeValueAsString(userModel);

        //Then
        Assertions.assertEquals(expectedContent, returnedContent);
    }

    @WithMockUser
    @Test
    public void testUpdateUser_BadRequest() throws Exception {
        Mockito.when(userService.updateUser(anyString(), any())).thenThrow(BadServiceCallException.class);
        mvc.perform(MockMvcRequestBuilders.put("/api/user/1")
                .with(csrf())
                .content("{}")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
        ).andDo(print()).andExpect(status().isNotFound());
    }

    @WithMockUser
    @Test
    public void testUpdateUser_NotFound() throws Exception {
        Mockito.when(userService.updateUser(anyString(), any())).thenThrow(NumberFormatException.class);
        mvc.perform(MockMvcRequestBuilders.put("/api/user/1")
                .with(csrf())
                .content("{}")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
        ).andDo(print()).andExpect(status().isBadRequest());
    }

    @WithMockUser
    @Test
    public void testUpdateUser_Success() throws Exception {
        //Given
        UserModel userModel = UserModel.builder().build();
        //When
        //Mockito.when(loginService.extractUsername(any())).thenReturn("user");
        Mockito.when(userService.updateUser(any(), any())).thenReturn(userModel);

        // Then
        mvc.perform(MockMvcRequestBuilders.put("/api/user/1")
                .with(csrf())
                .content("{}")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
        ).andDo(print()).andExpect(status().isFound());
    }

    @WithMockUser
    @Test
    public void testDeleteUser_BadRequest() throws Exception {
        //When
        Mockito.doThrow(new NumberFormatException()).when(userService).deleteUser(any());

        //Then
        mvc.perform(MockMvcRequestBuilders.delete("/api/user/{id}", 1).with(csrf())
                        .header(HttpHeaders.AUTHORIZATION, "Bearer test"))
                .andExpect(status().isBadRequest());
    }

    @WithMockUser
    @Test
    public void testDeleteUser_NotFound() throws Exception {
        //Given
        final String username = "user";

        //When
        //Mockito.when(loginService.extractUsername(any())).thenReturn(username);
        Mockito.doThrow(new BadServiceCallException("")).when(userService).deleteUser("1");

        //Then
        mvc.perform(MockMvcRequestBuilders.delete("/api/user/{id}", 1).with(csrf())
                        .header(HttpHeaders.AUTHORIZATION, "Bearer test"))
                .andExpect(status().isNotFound());
    }

    @WithMockUser
    @Test
    public void testDeleteUser_Success() throws Exception {
        mvc.perform(MockMvcRequestBuilders.delete("/api/user/1").with(csrf())
                        .header(HttpHeaders.AUTHORIZATION, "Bearer test"))
                .andExpect(status().isNoContent());
    }
}
