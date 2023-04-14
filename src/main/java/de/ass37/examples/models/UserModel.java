package de.ass37.examples.models;

import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserModel {
    private Integer id;

    private String username;
    private String password;
    private Integer deposit;
    private String role;

}
