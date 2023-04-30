package com.api.myfavgames.modules.user.controllers;

import com.api.myfavgames.modules.user.dtos.UserDto;
import com.api.myfavgames.modules.user.models.UserModel;
import com.api.myfavgames.modules.user.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
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
        return userService.store(userDto);
    }

    @Operation(summary = "Retorna todos os usuários")
    @GetMapping("/user")
    public ResponseEntity<List<UserModel>> findAll() {
        return userService.findAll();
    }

    @Operation(summary = "Retorna um usuário pelo id")
    @GetMapping("/user/{id}")
    public ResponseEntity<Object> findOne(@PathVariable UUID id) {
        return userService.findOne(id);
    }

    @Operation(summary = "Atualiza os dados de um usuário")
    @PutMapping("/user/{id}")
    public ResponseEntity<Object> update(@RequestBody @Valid UserDto userDto, @PathVariable UUID id) {
        return userService.updateUser(userDto, id);
    }

    @Operation(summary = "Deleta um usuário")
    @DeleteMapping("/user/{id}")
    public ResponseEntity<Object> delete(@PathVariable UUID id) {
        return userService.delete(id);
    }
}
