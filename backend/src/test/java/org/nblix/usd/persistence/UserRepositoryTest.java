package org.nblix.usd.persistence;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.nblix.usd.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

/**
 * Some basic tests for the JPA layer.
 * <ul>
 * <li>using {@link ExtendWith} to bring up the spring context</li>
 * <li>using {@link DataJpaTest} to load the jpa layer only (instead of
 * {@link SpringBootTest} that would load the whole stack)</li>
 * </ul>
 */
@ExtendWith(SpringExtension.class)
@DataJpaTest
public class UserRepositoryTest {
    @Autowired
    private UserRepository repository;

    @AfterEach
    public void teardown() {
        repository.deleteAll();
    }

    @Test
    public void testSaveUser() {
        User savedUser = repository.save(new User("annonymus", "verySecure"));
        assertNotNull(savedUser);
        assertNotNull(savedUser.getId());
    }

    @Test
    public void testReadUser() {
        User savedUser = repository.save(new User("annonymus", "verySecure"));
        User reloadedUser = repository.findById(savedUser.getId()).get();
        assertEquals(savedUser.getId(), reloadedUser.getId());
        assertEquals(savedUser.getUsername(), reloadedUser.getUsername());
        assertEquals(savedUser.getPassword(), reloadedUser.getPassword());
    }

    @Test
    public void testUpdateUser() {
        User savedUser = repository.save(new User("annonymus", "verySecure"));
        String newPwd = "SomethingQuiteDifferent";
        savedUser.setPassword(newPwd);
        repository.save(savedUser);
        User reloadedUser = repository.findById(savedUser.getId()).get();
        assertNotNull(reloadedUser);
        assertEquals(newPwd, reloadedUser.getPassword());
    }

    @Test
    public void testDeleteUserById() {
        User savedUser = repository.save(new User("annonymus", "verySecure"));
        assertEquals(1, repository.findAll().size());
        repository.deleteById(savedUser.getId());
        assertEquals(0, repository.findAll().size());
    }

}
