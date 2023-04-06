package de.ass37.examples.controller;

import de.ass37.examples.models.UserModel;
import de.ass37.examples.services.DepositService;
import de.ass37.examples.services.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class DepositController {

    @Autowired
    private DepositService depositService;

    @Autowired
    private LoginService loginService;

    @GetMapping("/deposit")
    public ResponseEntity<UserModel> getDeposit(@RequestHeader(HttpHeaders.AUTHORIZATION) String autorization) {
        String username =  loginService.extractUsername(autorization.substring(7));
        return new ResponseEntity<>(depositService.getDeposit(username), HttpStatus.FOUND);
    }

    @PostMapping(value = "/deposit/{coin}", consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserModel> addToDeposit(@RequestHeader(HttpHeaders.AUTHORIZATION) String autorization, @PathVariable("coin") String coin) {
        String username =  loginService.extractUsername(autorization.substring(7));
        return new ResponseEntity<>(depositService.addToDepositByUser(username, coin), HttpStatus.OK);
    }
}
