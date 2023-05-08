package de.ass37.examples.controller;

import de.ass37.examples.models.auth.AuthenticationResponse;
import de.ass37.examples.models.auth.RegisterRequest;
import de.ass37.examples.repository.JWTokenRepository;
import de.ass37.examples.services.AuthenticationService;
import de.ass37.examples.services.LoginService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AuthenticationController.class)
public class AuthenticationControllerTests {

    @MockBean
    private AuthenticationService service;

    @Autowired
    private MockMvc mvc;

    @MockBean
    private LoginService loginService;
    @MockBean
    private JWTokenRepository jwTokenRepository;

    @WithMockUser
    @Test
    public void testRegister_Success() throws Exception {
        RegisterRequest request =  RegisterRequest.builder().build();
        AuthenticationResponse response = AuthenticationResponse.builder().build();
        Mockito.when(service.register(request)).thenReturn(response);
        mvc.perform(MockMvcRequestBuilders.post("/api/user").with(csrf())
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content("{}")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer test"))
                .andExpect(status().isOk());
    }

    @WithMockUser
    @Test
    public void testLogin_Success() throws Exception {
        AuthenticationResponse  response = AuthenticationResponse.builder().build();
        Mockito.when(service.login(any())).thenReturn(response);

        mvc.perform(MockMvcRequestBuilders.post("/api/login").with(csrf())
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content("{}")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer test"))
                .andExpect(status().isOk());

    }
}
