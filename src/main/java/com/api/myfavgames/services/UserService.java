package com.api.myfavgames.services;

import com.api.myfavgames.models.UserModel;
import com.api.myfavgames.repositories.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserModel store(UserModel userModel) {
        PasswordEncoder encoder = new BCryptPasswordEncoder();
        var hashedPassword = encoder.encode(userModel.getPassword());
        userModel.setPassword(hashedPassword);
        return userRepository.save(userModel);
    }
}
