package com.api.myfavgames.controllers;

import com.api.myfavgames.dtos.UserDto;
import com.api.myfavgames.models.UserModel;
import com.api.myfavgames.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/my-fav-games")
@Tag(name = "API", description = "API para gerenciar usuários")
public class UserController {
    final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Operation(summary = "Cria um usuário")
    @PostMapping("/user")
    public ResponseEntity<Object> storeUser(@RequestBody @Valid UserDto userDto) {
        if(userService.isEmailRegistered(userDto.getEmail())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("CONFLICT: Email already regitered!");
        }
        var userModel = new UserModel();
        BeanUtils.copyProperties(userDto, userModel);
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.store(userModel));
    }

    @Operation(summary = "Retorna todos os usuários")
    @GetMapping("/user")
    public ResponseEntity<List<UserModel>> findAll() {
        return ResponseEntity.status(HttpStatus.OK).body(userService.findAll());
    }

    @Operation(summary = "Retorna um usuário pelo id")
    @GetMapping("/user/{id}")
    public ResponseEntity<Object> findOne(@PathVariable UUID id) {
        var user = userService.findOne(id);
        if (user.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("NOT FOUND: User not found!");
        }
        return ResponseEntity.status(HttpStatus.OK).body(userService.findOne(id));
    }

    @Operation(summary = "Atualiza os dados de um usuário")
    @PutMapping("/user/{id}")
    public ResponseEntity<Object> update(@RequestBody @Valid UserDto userDto, @PathVariable UUID id) {
        var user = userService.findOne(id);

        if (user.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("NOT FOUND: User not found!");
        }

        var userModel = user.get();

        var checkPassrod = userService.checkPassword(userModel.getPassword(), userDto.getPassword());

        if (!checkPassrod) {
            var newPassword = userService.hashPassword(userDto.getPassword());
            userModel.setPassword(newPassword);
        }

        if (!Objects.equals(userDto.getEmail(), userModel.getEmail())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("BAD REQUEST: Unable to update email");
        }

        userModel.setUsername(userDto.getUsername());

        return  ResponseEntity.status(HttpStatus.OK).body(userService.updateUser(userModel));
    }

    @Operation(summary = "Deleta um usuário")
    @DeleteMapping("/user/{id}")
    public ResponseEntity<Object> delete(@PathVariable UUID id) {
        var user = userService.findOne(id);

        if (user.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("NOT FOUND: User not found!");
        }

        userService.delete(user.get());

        return ResponseEntity.status(HttpStatus.OK).body("OK: Success in deleting user!");
    }

}
