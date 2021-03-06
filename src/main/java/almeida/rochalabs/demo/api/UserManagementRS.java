package almeida.rochalabs.demo.api;

import static java.util.stream.Collectors.toList;

import java.net.URI;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.function.Predicate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import almeida.rochalabs.demo.api.requests.CreateUserRequest;
import almeida.rochalabs.demo.api.responses.CreateUserResponse;
import almeida.rochalabs.demo.data.entities.UserProfile;
import almeida.rochalabs.demo.data.query.QueryFactory;
import almeida.rochalabs.demo.data.service.UserManagement;

/**
 * 
 * @author rochapaulo
 *
 */
@RestController
public class UserManagementRS {

    private final UserManagement service;
    private final QueryFactory queryFactory;

    @Autowired
    public UserManagementRS(UserManagement service, QueryFactory queryFactory) {
        this.service = service;
        this.queryFactory = queryFactory;
    }

    @RequestMapping(
            path = "/api/users", 
            method = RequestMethod.POST, 
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<?> createUser(@RequestBody CreateUserRequest request) throws Exception {
        
        CreateUserResponse profileCreated = service.createUser(request).exceptionally(exception -> null).get();
        if (profileCreated == null) {
            String message = "user email " + request.getEmail() + " already exists";
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(message); 
        }
        
        URI resourceLocation = new URI("/users/" + profileCreated.getUserId());
        return ResponseEntity.created(resourceLocation).build();

    }

    @RequestMapping(
            path = "/api/secure/users/{userId}", 
            method = RequestMethod.GET, 
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<?> getUserProfile(@PathVariable String userId) throws Exception {

        List<UserProfile> profile = service.findProfileBy(queryFactory.profileByUUID(UUID.fromString(userId))).get();

        if (profile.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(profile.get(0));
    }

    @RequestMapping(
            path = "/api/secure/users", 
            method = RequestMethod.GET, 
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<?> getUsersProfile(
    		@RequestParam(name = "firstName", required = false) String firstName,
            @RequestParam(name = "lastName", required = false) String lastName,
            @RequestParam(name = "email", required = false) String email) throws Exception {

        List<UserProfile> profiles = 
            service
                .findProfileBy(queryFactory.allProfiles())
                .get()
                .parallelStream()
                .filter(byFirstName(firstName))
                .filter(byLastname(lastName))
                .filter(byEmail(email))
                .collect(toList());

        if (profiles.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(profiles);
    }

    
    public static Predicate<? super UserProfile> byFirstName(String firstName) {
        return profile -> (firstName == null) ? true : contains(profile.getFirstName(), firstName);
    }

    public static Predicate<? super UserProfile> byLastname(String lastName) {
        return profile -> (lastName == null) ? true : contains(profile.getLastName(), lastName);
    }

    public static Predicate<? super UserProfile> byEmail(String email) {
        return profile -> (email == null) ? true : Objects.equals(email, profile.getEmail());
    }

    private static boolean contains(String source, String filter) {
        return source.contains(filter);
    }
    
}
