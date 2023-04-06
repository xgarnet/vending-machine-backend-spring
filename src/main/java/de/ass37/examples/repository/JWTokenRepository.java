package de.ass37.examples.repository;

import de.ass37.examples.entities.JWToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface JWTokenRepository extends JpaRepository<JWToken, Integer> {
    @Query(value = """
      select t from JWToken t inner join User u\s
      on t.user.id = u.id\s
      where u.id = :id and (t.expired = false or t.revoked = false)\s
      """)
    List<JWToken> findAllValidTokenByUser(Integer id);

    Optional<JWToken> findByToken(String token);
}
