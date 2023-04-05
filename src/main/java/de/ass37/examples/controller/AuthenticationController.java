//package de.ass37.examples.controller;
//
//import de.ass37.examples.models.auth.AuthenticationRequest;
//import de.ass37.examples.models.auth.AuthenticationResponse;
//import de.ass37.examples.models.auth.RegisterRequest;
//import de.ass37.examples.services.AuthenticationService;
//import de.ass37.examples.services.LoginService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//@RestController
//@RequestMapping("/api/v1/auth")
//public class AuthenticationController {
//
//    @Autowired
//    private AuthenticationService service;
//
//    @PostMapping("/register")
//    public ResponseEntity<AuthenticationResponse> register(
//            @RequestBody RegisterRequest request
//    ) {
//        return ResponseEntity.ok(service.register(request));
//    }
//    @PostMapping("/authenticate")
//    public ResponseEntity<AuthenticationResponse> authenticate(
//            @RequestBody AuthenticationRequest request
//    ) {
//        return ResponseEntity.ok(service.authenticate(request));
//    }
//}
