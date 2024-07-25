package com.api.myfavgames.domain.user;

import com.api.myfavgames.config.CustomException;
import com.api.myfavgames.domain.user.payload.request.CreateUserRequest;
import com.api.myfavgames.domain.user.payload.request.UpdateUserRequest;
import com.api.myfavgames.domain.user.payload.response.UserResponse;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.stream.Collectors;

public class UserServiceImpl implements IUserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public UserServiceImpl(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    @Override
    public UserResponse create(CreateUserRequest userRequest) {
        User user = userMapper.toEntity(userRequest);
        checkIfUserAlreadyExist(userRequest.getEmail());
        String hashedPassword = passwordEncoder.encode(userRequest.getPassword());
        user.setPassword(hashedPassword);
        return userMapper.toDto(userRepository.save(user));
    }

    @Override
    public UserResponse findById(Long id) {
        return userMapper.toDto(findUserById(id));
    }

    @Override
    public List<UserResponse> findAll() {
        Page<User> users = userRepository.findAll(
                PageRequest.of(0, 20, Sort.by("id"))
        );
        return users
                .stream()
                .map(userMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public UserResponse update(Long id, UpdateUserRequest updateUserRequest) {
        User user = findUserById(id);
        BeanUtils.copyProperties(updateUserRequest, user, "id");
        return userMapper.toDto(userRepository.save(user));
    }

    private User findUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(
                        () -> new CustomException("user not found", HttpStatus.NOT_FOUND)
                );
    }

    private void checkIfUserAlreadyExist(String email) {
        User user = userRepository.getUserByEmail(email);
        if (user != null) {
            throw new CustomException("client already registered.", HttpStatus.CONFLICT);
        }
    }
}
