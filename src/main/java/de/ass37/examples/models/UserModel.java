package de.ass37.examples.models;

import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserModel {
    private Long id;

    private String username;
    private String password;
    private Long deposit;
    private String role;

}
