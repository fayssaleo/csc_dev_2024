package com.ta.csc;


import com.ta.csc.domain.User;
import com.ta.csc.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class CscApplication {

    public static void main(String[] args) {
        SpringApplication.run(CscApplication.class, args);

    }

    @Bean
    CommandLineRunner init(UserService userService, PasswordEncoder passwordEncoder) {
        return args -> {
                boolean userExits = userService.userEmailExists("admin@gmail.com");
                if(userExits) System.out.println("Already Exists");
                else {
                    User user = new User(null,"Admin","Admin","admin@gmail.com","Admin2021@","ADMIN");
                    userService.addUser(user);
                }

        };
    }

}