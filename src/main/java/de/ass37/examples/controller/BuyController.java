package de.ass37.examples.controller;


import de.ass37.examples.models.BuyModel;
import de.ass37.examples.services.BuyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BuyController {
    @Autowired
    private BuyService buyService;

    @PostMapping(value = "/buy", consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BuyModel> buy(@RequestBody BuyModel buyModel) {
        return new ResponseEntity<>(buyService.buyByUser(buyModel), HttpStatus.OK);
    }
}
