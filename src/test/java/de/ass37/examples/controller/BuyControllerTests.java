package de.ass37.examples.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.ass37.examples.models.BuyReqModel;
import de.ass37.examples.models.BuyRespModel;
import de.ass37.examples.repository.JWTokenRepository;
import de.ass37.examples.services.BuyService;
import de.ass37.examples.services.LoginService;
import de.ass37.examples.services.exceptions.BadServiceCallException;
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

@WebMvcTest(BuyController.class)
public class BuyControllerTests {
    @Autowired
    private MockMvc mvc;

    @MockBean
    private BuyService buyService;
    @MockBean
    private LoginService loginService;

    @MockBean
    private JWTokenRepository jwTokenRepository;


    @WithMockUser
    @Test
    public void testBuy_NotFound() throws Exception {
        Mockito.when(buyService.buyByUser(any(), anyString())).thenThrow(BadServiceCallException.class);
        Mockito.when(loginService.extractUsername(anyString())).thenReturn("user");
        mvc.perform(MockMvcRequestBuilders.post("/api/buy").with(csrf())
                        .header(HttpHeaders.AUTHORIZATION, "Bearer  test")
                        .content("{}")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotFound());

    }


    @WithMockUser
    @Test
    public void testBuy_BadRequest() throws Exception {
        Mockito.when(buyService.buyByUser(any(), anyString())).thenThrow(NumberFormatException.class);
        Mockito.when(loginService.extractUsername(anyString())).thenReturn("user");
        mvc.perform(MockMvcRequestBuilders.post("/api/buy").with(csrf())
                        .header(HttpHeaders.AUTHORIZATION, "Bearer  test")
                        .content("{}")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @WithMockUser
    @Test
    public void testBuy_Successful() throws Exception {
        //Given
        final BuyReqModel buyReqModel = new BuyReqModel();
        buyReqModel.setProductId(1);
        buyReqModel.setMenge(2);
         final BuyRespModel buyRespModel = new BuyRespModel();
         buyRespModel.setChanges(15);
         buyRespModel.setMessage("Succeccful");
         final String user = "user";
         ObjectMapper objectMapper = new ObjectMapper();

         //When
        Mockito.when(buyService.buyByUser(buyReqModel, user)).thenReturn(buyRespModel);

        //Then
        MvcResult result = mvc.perform(MockMvcRequestBuilders.post("/api/buy").with(csrf())
                        .header(HttpHeaders.AUTHORIZATION, "Bearer  test")
                        .content(objectMapper.writeValueAsString(buyReqModel))
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk()).andReturn();
        //todo
        //final String val = result.getResponse().getContentAsString();
        //BuyRespModel returnedBuyRespModel = objectMapper.readValue(val, BuyRespModel.class);
        //Assertions.assertEquals(returnedBuyRespModel.getChanges(), buyRespModel.getChanges());
        //Assertions.assertEquals(returnedBuyRespModel.getMessage(), buyRespModel.getMessage());
    }

}
