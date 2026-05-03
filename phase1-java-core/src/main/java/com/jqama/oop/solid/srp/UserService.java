package com.jqama.oop.solid.srp;

import java.util.List;
import java.util.Optional;

public class UserService {
    private final UserRepository userRepository;
    private final EmailService emailService;

    public UserService(UserRepository userRepository, EmailService emailService) {
        this.userRepository = userRepository;
        this.emailService = emailService;
    }

    public void registerUser(User user) {
        userRepository.save(user);
        emailService.sendWelcome(user);
    }

    public Optional<User> findUser(long id) {
        return userRepository.findById(id);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public void removeUser(long id) {
        userRepository.deleteById(id);
    }
}