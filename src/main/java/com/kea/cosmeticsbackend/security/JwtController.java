package com.kea.cosmeticsbackend.security;

import com.kea.cosmeticsbackend.model.User;
import com.kea.cosmeticsbackend.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(origins = "https://happy-sky-0a8b63303.4.azurestaticapps.net")
public class JwtController {
    @Autowired
    private JwtUserDetailsService userDetailsService;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtTokenManager jwtTokenManager;
    @Autowired
    private IUserService userService;

    @PostMapping("/signup")
    public ResponseEntity<JwtResponseModel> signup(@RequestBody JwtRequestModel request) {
        System.out.println("signup: username:" + request.getUsername() + " password: " + request.getPassword());
        User user = new User(request.getUsername(), request.getPassword());

        try {
            if (userService.findByName(user.getUsername()).size() == 0) {
                if (userService.save(user) != null) {
                    return ResponseEntity.ok(new JwtResponseModel("User created successfully!"));
                } else {
                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new JwtResponseModel("Error creating user."));
                }
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new JwtResponseModel("Error: User already exists."));
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new JwtResponseModel("An error occurred while processing your request."));
        }
    }


    @PostMapping("/login")
    public ResponseEntity<JwtResponseModel> createToken(@RequestBody JwtRequestModel request) throws Exception {
        // HttpServletRequest servletRequest is available from Spring, if needed.
        System.out.println(" JwtController createToken Call: 4" + request.getUsername());
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getUsername(),
                            request.getPassword())
            );
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            // Changed the response to use HttpStatus.UNAUTHORIZED for invalid credentials
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new JwtResponseModel("Invalid credentials"));
        }
        final UserDetails userDetails = userDetailsService.loadUserByUsername(request.getUsername());
        final String jwtToken = jwtTokenManager.generateJwtToken(userDetails);
        return ResponseEntity.ok(new JwtResponseModel(jwtToken));
    }

        @PostMapping("/getSecret")
    public ResponseEntity<Map> getSecret() {
        System.out.println("getSecret is called");
        Map<String, String> map = new HashMap<>();
        map.put("message", "this is secret from server");
        return ResponseEntity.ok(map);
    }

    @DeleteMapping("/deleteUser")
    public ResponseEntity<Map> deleteUser(@RequestBody User user) { // hvis man kommer hertil, er token OK
        System.out.println("deleteUser is called with user: " + user.getUsername());
        // evt. findById, som finder hele objektet fra MySQL, inkl. id.
        List<User> users = userService.findByName(user.getUsername());
        User userToDelete = users.get(0);
        userService.delete(userToDelete);
        Map<String, String> map = new HashMap<>();
        map.put("message", "user deleted, if found " + user.getUsername());
        return ResponseEntity.ok(map);
    }
}
