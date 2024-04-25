package com.ta.csc.web;

import com.ta.csc.domain.User;
import com.ta.csc.dto.JwtResponseDTO;
import com.ta.csc.service.AuthenticationService;
import com.ta.csc.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api/v1")
public class JwtAuthenticationResource {
    private final AuthenticationService authenticationService;
    private final UserService userService;

    public JwtAuthenticationResource(AuthenticationService authenticationService, UserService userService) {
        this.authenticationService = authenticationService;
        this.userService = userService;
    }
    @PostMapping("/user")
    @PreAuthorize("@securityService.isCurrentUserHasRole('ADMIN')")
    public void addUser(@RequestBody User user){
        userService.addUser(user);
    }

    @PostMapping("/authenticate")
    public ResponseEntity<JwtResponseDTO> createAuthenticationToken(@RequestBody @Valid User authenticationRequest) {
        String token = authenticationService.authenticate(authenticationRequest);
        return ResponseEntity.ok(new JwtResponseDTO(token));
    }

    @GetMapping("/validateToken")
    public boolean validateToken(@RequestParam String token) {
        return authenticationService.validateToken(token);
    }

    @PreAuthorize("@securityService.itsMe(#email) || @securityService.isCurrentUserHasRole('ADMIN')")
    @GetMapping("/user")
    public User getUserByEmail(@RequestParam String email) {
        return userService.findByEmail(email);
    }

    @PreAuthorize("@securityService.itsMe(#user.email) || @securityService.isCurrentUserHasRole('ADMIN')")
    @PostMapping("/isUserTrue")
    public boolean UserExists(@RequestBody User user) {
        return userService.userExists(user);
    }

    @PreAuthorize("@securityService.itsMe(#user.email) || @securityService.isCurrentUserHasRole('ADMIN')")
    @PutMapping("/changePassword")
    public void changePassword(@RequestBody User user) {
        authenticationService.changePassword(user);
    }


    @PreAuthorize("@securityService.isCurrentUserHasRole('ADMIN')")
    @GetMapping("/user/all")
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @PreAuthorize("@securityService.isCurrentUserHasRole('ADMIN')")
    @GetMapping("/user/{id}")
    public User getUserById(@PathVariable("id") Long id) {
        return userService.getUserById(id);
    }

    @PreAuthorize("@securityService.isCurrentUserHasRole('ADMIN')")
    @DeleteMapping("/user/{id}")
    public void deleteUserById(@PathVariable Long id){
        userService.deleteUserById(id);
    }
    @PreAuthorize("@securityService.isCurrentUserHasRole('ADMIN')")
    @GetMapping("/user/email")
    public boolean userEmailExists(@RequestParam String email) {
        return userService.userEmailExists(email);
    }

    @PreAuthorize("@securityService.isCurrentUserHasRole('ADMIN')")
    @PutMapping("/edituser")
    public void editUser(@RequestBody User user){
        userService.editUser(user);
    }

}
