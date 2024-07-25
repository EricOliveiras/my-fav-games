package com.api.myfavgames.domain.user;

import com.api.myfavgames.domain.user.payload.request.CreateUserRequest;
import com.api.myfavgames.domain.user.payload.response.UserResponse;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User toEntity(CreateUserRequest userRequest);
    UserResponse toDto(User user);
    List<UserResponse> toDto(List<User> users);
}
