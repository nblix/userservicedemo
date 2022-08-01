package org.nblix.usd.service;

import org.nblix.usd.USDConstants;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Jann Schneider <jann.schneider@googlemail.com>
 *
 * 
 *         Simple REST controller handling request to the root path.
 */
@CrossOrigin(origins = { "localhost:8080" })
@RestController
@RequestMapping("/")
public class HomeController {
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<String> index() {
        return new ResponseEntity<String>(
                "Base API URL: " + USDConstants.API_ROOT + " API root for user objects: " + USDConstants.API_USERS,
                HttpStatus.OK);
    }
}
