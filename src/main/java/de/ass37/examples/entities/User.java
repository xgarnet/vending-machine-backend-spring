package de.ass37.examples.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;



@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "login")
public class User {
    @Id
    @GeneratedValue(generator="user_sequence")
    @SequenceGenerator(name="user_sequence",sequenceName="user_sequence", allocationSize=1)
    private Integer id;

    private String username;
    private String password;
    private Long deposit;
    private String role;
}


