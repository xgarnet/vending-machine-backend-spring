package de.ass37.examples.controller;

import de.ass37.examples.models.UserModel;
import de.ass37.examples.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping(value = "/user", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<UserModel>> getUsers() {
        return  new ResponseEntity<>(userService.getAllUsers(), HttpStatus.OK);
    }
    @GetMapping( value = "/user/{id}",  produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserModel> getUserById(@PathVariable String id) {
        return  new ResponseEntity<>(userService.getUserById(id), HttpStatus.OK);
    }


    @PutMapping(value = "/user/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserModel> updateUser(@PathVariable String id, @RequestBody UserModel userModel) {
        return new ResponseEntity<>(userService.updateUser(id, userModel), HttpStatus.FOUND);
    }

    @DeleteMapping(value = "/user/{id}")
    public ResponseEntity deleteUser(@PathVariable String id) {
        userService.deleteUser(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
