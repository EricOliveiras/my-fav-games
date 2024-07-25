package com.api.myfavgames.domain.user;

import com.api.myfavgames.domain.user.payload.request.CreateUserRequest;
import com.api.myfavgames.domain.user.payload.request.UpdateUserRequest;
import com.api.myfavgames.domain.user.payload.response.UserResponse;

import java.util.List;

public interface IUserService {
    UserResponse create(CreateUserRequest userRequest);
    UserResponse findById(Long id);
    List<UserResponse> findAll();
    UserResponse update(Long id, UpdateUserRequest updateUserRequest);
}
