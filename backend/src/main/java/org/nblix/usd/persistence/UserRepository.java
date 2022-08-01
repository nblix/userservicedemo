package org.nblix.usd.persistence;

import java.util.Optional;

import org.nblix.usd.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
}
