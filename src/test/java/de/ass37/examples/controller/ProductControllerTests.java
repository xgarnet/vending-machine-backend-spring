//package de.ass37.examples.controller;
//
//import de.ass37.examples.config.WebSecurityConfigurer;
//import de.ass37.examples.repository.JWTokenRepository;
//import de.ass37.examples.services.LoginService;
//import de.ass37.examples.services.ProductService;
//import org.junit.jupiter.api.BeforeAll;
//import org.junit.jupiter.api.Disabled;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.Mockito;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.context.annotation.Import;
//import org.springframework.security.test.context.support.WithMockUser;
//import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.junit.jupiter.SpringExtension;
//import org.springframework.test.context.web.WebAppConfiguration;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
//import org.springframework.test.web.servlet.setup.MockMvcBuilders;
//import org.springframework.web.context.WebApplicationContext;
//
//import java.util.Collections;
//
//import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//@Import(WebSecurityConfigurer.class)
//@ExtendWith(SpringExtension.class)
//@SpringBootTest(classes = {ProductController.class})
//@AutoConfigureMockMvc
////@WebAppConfiguration
//@ContextConfiguration(classes = WebSecurityConfigurer.class)
//public class ProductControllerTests {
//
//   //  @Autowired
//    private static MockMvc mvc;
//
//    /*
//    @BeforeAll
//    public static void setup() {
//        mvc = MockMvcBuilders
//                .webAppContextSetup(context)
//                .apply(springSecurity())
//                .build();
//    }
//
//     */
//
//    @MockBean
//    private ProductService productService;
//
//    @MockBean
//    private LoginService loginService;
//
//    @Autowired
//    private static WebApplicationContext context;
//
//    @MockBean
//    private JWTokenRepository jWTokenRepository;
//
//    @Test
//    @Disabled
//    @WithMockUser(username="user", password = "user")
//    public void authenticateTest() throws Exception {
//        Mockito.when(productService.getAllProducts()).thenReturn(Collections.emptyList());
//        mvc.perform(MockMvcRequestBuilders.get("/api/user")
//                //.with(SecurityMockMvcRequestPostProcessors.user("user").password("user").roles("buyer")))
//                //.with(SecurityMockMvcRequestPostProcessors.jwt())
//                ).andExpect(status().isOk());
//
//    }
//
//
//}
