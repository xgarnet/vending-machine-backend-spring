package de.ass37.examples.controller;

import de.ass37.examples.models.DepositModel;
import de.ass37.examples.services.DepositService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class DepositController {

    @Autowired
    private DepositService depositService;

    @GetMapping("/deposit")
    public ResponseEntity<DepositModel> getDeposit() {
        return new ResponseEntity<>(depositService.getDeposit(), HttpStatus.FOUND);
    }

    @PostMapping(value = "/deposit", consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DepositModel> addToDeposit(@RequestBody DepositModel depositModel) {
        return new ResponseEntity<>(depositService.addToDepositByUserId(depositModel), HttpStatus.OK);
    }
}
