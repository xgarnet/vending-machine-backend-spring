package de.ass37.examples.controller;


import de.ass37.examples.models.BuyReqModel;
import de.ass37.examples.models.BuyRespModel;
import de.ass37.examples.services.BuyService;
import de.ass37.examples.services.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class BuyController {
    @Autowired
    private BuyService buyService;

    @Autowired
    private LoginService loginService;


    @PostMapping(value = "/buy", consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BuyRespModel> buy(@RequestHeader(HttpHeaders.AUTHORIZATION) String autorization, @RequestBody BuyReqModel buyReqModel) {
        String username =  loginService.extractUsername(autorization.substring(7));
        return new ResponseEntity<>(buyService.buyByUser(buyReqModel, username), HttpStatus.OK);
    }
}
