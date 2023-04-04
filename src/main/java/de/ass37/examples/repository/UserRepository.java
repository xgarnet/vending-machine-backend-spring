package de.ass37.examples.repository;

import de.ass37.examples.entities.User;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

@Repository
@EnableJpaRepositories
@EntityScan("de.ass37.examples.entities")
public interface UserRepository extends JpaRepository<User, Long> {
}
