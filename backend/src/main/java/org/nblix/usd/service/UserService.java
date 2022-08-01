package org.nblix.usd.service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.nblix.usd.model.User;
import org.nblix.usd.persistence.UserRepository;
import org.nblix.usd.security.PasswordHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserRepository repository;

    public List<User> getAllUsers() {
        List<User> users = repository.findAll();
        return users == null ? Collections.emptyList() : users;
    }

    public Long createUser(String username, String password) {
        User newUser = new User(username, PasswordHelper.encode(password));
        User savedUser = repository.save(newUser);
        return savedUser.getId();
    }

    public Optional<User> getUserById(Long id) {
        return repository.findById(id);
    }

    public Optional<User> getUserByUsername(String username) {
        return repository.findByUsername(username);
    }

    public boolean deleteUserById(Long id) {
        Optional<User> reloadedUser = repository.findById(id);

        if (reloadedUser.isPresent()) {
            repository.deleteById(id);
            return true;
        } else {
            return false;
        }

    }

    public User authenticateUser(String username, String password) {
        Optional<User> result = repository.findByUsername(username);

        if (result.isPresent()) {
            if (PasswordHelper.verify(password, result.get().getPassword())) {
                return result.get();
            }
        }

        return null;
    }
}
