package org.nblix.usd.service;

import java.util.List;
import java.util.Optional;

import org.nblix.usd.USDConstants;
import org.nblix.usd.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

/**
 * @author Jann Schneider <jann.schneider@googlemail.com>
 * 
 *         This REST controller provides mappings to create, read, delete and
 *         authenticate {@link User}s
 */
@CrossOrigin(origins = { "localhost:8080" })
@RestController
@RequestMapping(USDConstants.API_USERS)
public class UserController {
    // Let the service deal with the JPA repository and just use the service here:
    @Autowired
    private UserService service;

    /**
     * The returned {@link ResponseEntity} will provide an empty {@link User}s list
     * if no {@link User}s are found.
     * 
     * @return A {@link ResponseEntity} containing a list of {@link User}s.
     */
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = service.getAllUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)

    public ResponseEntity<Long> createUser(@RequestParam String username, @RequestParam String password) {
        Optional<User> result = service.getUserByUsername(username);

        if (result.isPresent()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "A user with the given username already exists!");
        }

        Long id = service.createUser(username, password);
        return new ResponseEntity<>(id, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        Optional<User> user = service.getUserById(id);

        if (user.isPresent()) {
            return new ResponseEntity<>(user.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Void> deleteUserById(@PathVariable Long id) {
        if (service.deleteUserById(id)) {
            return new ResponseEntity<Void>(HttpStatus.OK);
        } else {
            return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/auth")
    public ResponseEntity<User> authenticateUser(@RequestParam String username, @RequestParam String password) {
        User user = service.authenticateUser(username, password);

        if (user != null) {
            return new ResponseEntity<>(user, HttpStatus.OK);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, "Invalid credentials!");
        }
    }

}
