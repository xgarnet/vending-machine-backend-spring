package de.ass37.examples.controller;

import de.ass37.examples.services.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/demo-controller")
public class DemoController {

    @Autowired
    private LoginService loginService;

    @GetMapping
    public ResponseEntity<String> sayHello(@RequestHeader(HttpHeaders.AUTHORIZATION) String autorization) {
        System.out.println("Auth: " + autorization);
        System.out.println("username: " + loginService.extractUsername(autorization.substring(7)));
        return ResponseEntity.ok("Hello from secured endpoint");
    }

}
