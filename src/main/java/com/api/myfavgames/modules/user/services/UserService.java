package com.api.myfavgames.services;

import com.api.myfavgames.models.UserModel;
import com.api.myfavgames.repositories.UserRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {
    final UserRepository userRepository;

    private static final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    public UserModel store(UserModel userModel) {
        var hashedPassword = hashPassword(userModel.getPassword());
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

    public UserModel updateUser(UserModel userModel) {
        return userRepository.save(userModel);
    }

    public String hashPassword(String password) {
        return passwordEncoder.encode(password);
    }

    public boolean checkPassword(@Valid String bodyPassword, String password) {
        return passwordEncoder.matches(password, bodyPassword);
    }

    public UserModel finUserModel(String email) {
        return userRepository.getByEmail(email);
    }

    public void delete(UserModel userModel) {
        userRepository.delete(userModel);
    }
}
