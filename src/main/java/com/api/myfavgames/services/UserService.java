package com.api.myfavgames.services;

import com.api.myfavgames.models.UserModel;
import com.api.myfavgames.repositories.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {
    final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    public UserModel store(UserModel userModel) {
        PasswordEncoder encoder = new BCryptPasswordEncoder();
        var hashedPassword = encoder.encode(userModel.getPassword());
        userModel.setPassword(hashedPassword);

        return userRepository.save(userModel);
    }

    public boolean isEmailRegistered(String email) {
        return userRepository.existsByEmail(email);
    }

    public List<UserModel> findAll() {
        return userRepository.findAll();
    }

    public Optional<UserModel> findOne(UUID id) {
        return userRepository.findById(id);
    }
}
