package de.ass37.examples.controller;

import de.ass37.examples.models.UserModel;
import de.ass37.examples.repository.JWTokenRepository;
import de.ass37.examples.services.LoginService;
import de.ass37.examples.services.ResetService;
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
import static org.mockito.ArgumentMatchers.anyString;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ResetController.class)
public class ResetControllerTests {

    @MockBean
    private ResetService resetService;

    @MockBean
    private LoginService loginService;

    @MockBean
    private JWTokenRepository jwTokenRepository;

    @Autowired
    private MockMvc mvc;

    @WithMockUser
    @Test
    public void testResetDeposit() throws Exception {
        //Given
        final UserModel userModel = UserModel.builder().deposit(0).build();
        //When
        Mockito.when(loginService.extractUsername(anyString())).thenReturn("user");
        Mockito.when(resetService.resetDeposit(any())).thenReturn(userModel);
        //Then
        mvc.perform(MockMvcRequestBuilders.get("/api/reset")
                        .with(csrf())
                        .header(HttpHeaders.AUTHORIZATION, "Bearer test")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content("{}")
                )
                .andExpect(status().isOk());
    }
}
