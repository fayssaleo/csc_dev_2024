package com.ta.csc.service;

import com.ta.csc.domain.User;
import com.ta.csc.exception.NotFoundException;
import com.ta.csc.repositroy.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;


@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(() -> new NotFoundException("User not found with email :" +email));
    }
    public void addUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }
    public void addUserWithId(User user) {userRepository.save(user);}

    public boolean userExists(User user) {

        User newUser = userRepository.findByEmail(user.getEmail()).orElseThrow(() -> new NotFoundException("User not found"));
        return passwordEncoder.matches(user.getPassword(),newUser.getPassword());
    }
    public void deleteUserById(Long id){
        userRepository.deleteById(id);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUserById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new NotFoundException("User introuvable avec id:"+id));
    }

    public void editUser(User user){
        if(user.getPassword().equals("")) {
            User oldUser = getUserById(user.getId());
            user.setPassword(oldUser.getPassword());
            addUserWithId(user);
        }
        else {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            addUserWithId(user);
        }

    }

    public boolean userEmailExists(String email) {
        return userRepository.findByEmail(email).isPresent();
    }

}
