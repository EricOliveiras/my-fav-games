package com.api.myfavgames.controllers;

import com.api.myfavgames.dtos.UserDto;
import com.api.myfavgames.models.UserModel;
import com.api.myfavgames.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/my-fav-games")
public class UserController {
    final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/user")
    public ResponseEntity<Object> storeUser(@RequestBody @Valid UserDto userDto) {
        if(userService.isEmailRegistered(userDto.getEmail())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("CONFLICT: Email already regitered!");
        }
        var userModel = new UserModel();
        BeanUtils.copyProperties(userDto, userModel);
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.store(userModel));
    }

    @GetMapping("/user")
    public ResponseEntity<List<UserModel>> findAll() {
        return ResponseEntity.status(HttpStatus.OK).body(userService.findAll());
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<Object> findOne(@PathVariable UUID id) {
        var user = userService.findOne(id);
        if (user.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("NOT FOUND: User not found!");
        }
        return ResponseEntity.status(HttpStatus.OK).body(userService.findOne(id));
    }
}
