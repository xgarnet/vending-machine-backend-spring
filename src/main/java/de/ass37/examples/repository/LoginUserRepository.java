package de.ass37.examples.repository;
import java.util.Optional;

import de.ass37.examples.entities.LoginUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LoginUserRepository extends JpaRepository<LoginUser, Integer> {

    Optional<LoginUser> findByEmail(String email);

}
