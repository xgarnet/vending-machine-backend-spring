package de.ass37.examples.models;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserModel {
    private Long id;

    private String username;
    private String password;
    private Long deposit;
    private String role;

}
