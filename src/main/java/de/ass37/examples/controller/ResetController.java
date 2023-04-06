package de.ass37.examples.controller;

import de.ass37.examples.models.UserModel;
import de.ass37.examples.services.LoginService;
import de.ass37.examples.services.ResetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class ResetController {

    @Autowired
    private ResetService resetService;

    @Autowired
    private LoginService loginService;


    @GetMapping("/reset")
    public ResponseEntity<UserModel> resetDeposit(@RequestHeader(HttpHeaders.AUTHORIZATION) String autorization) {
        String username =  loginService.extractUsername(autorization.substring(7));
        return new ResponseEntity<>(resetService.resetDeposit(username), HttpStatus.OK);
    }
}
