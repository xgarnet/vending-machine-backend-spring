package de.ass37.examples.controller;

import de.ass37.examples.models.UserModel;
import de.ass37.examples.services.ResetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class ResetController {

    @Autowired
    private ResetService resetService;

    @GetMapping("/reset")
    public ResponseEntity<UserModel> resetDeposit() {
        return new ResponseEntity<>(resetService.resetDeposit(), HttpStatus.OK);
    }
}
