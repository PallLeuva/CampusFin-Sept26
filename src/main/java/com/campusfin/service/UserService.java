package com.campusfin.service;

import com.campusfin.model.SignupRequest;
import com.campusfin.model.User;
import com.campusfin.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder) {

        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public boolean emailExists(String email) {

        return userRepository.existsByEmail(
                email.trim().toLowerCase()
        );
    }

    public User registerUser(SignupRequest signupRequest) {

        String normalizedEmail =
                signupRequest.getEmail()
                        .trim()
                        .toLowerCase();

        if (userRepository.existsByEmail(normalizedEmail)) {

            throw new IllegalArgumentException(
                    "An account already exists with this email."
            );
        }

        User user = new User();

        user.setName(
                signupRequest.getName().trim()
        );

        user.setEmail(normalizedEmail);

        user.setPassword(
                passwordEncoder.encode(
                        signupRequest.getPassword()
                )
        );

        return userRepository.save(user);
    }
}