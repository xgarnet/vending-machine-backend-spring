package de.ass37.examples.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;



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

/*
public class User  implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    private Integer id;

    private String firstname;
    private String lastname;
    private String username;
    private String password;
    private Long deposit;
    private String roleEntity;

    private String email;

    @Enumerated(EnumType.STRING)
    private Role role;

    @OneToMany(mappedBy = "user")
    private List<Token> tokens;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }
}

 */
