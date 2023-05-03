package com.api.myfavgames.modules.user.controllers;

import com.api.myfavgames.handlers.ErrorResponse;
import com.api.myfavgames.modules.user.dtos.UserDto;
import com.api.myfavgames.modules.user.models.UserModel;
import com.api.myfavgames.modules.user.payload.UserResponse;
import com.api.myfavgames.modules.user.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/myfavgames")
@Tag(name = "API", description = "API to manager users")
public class UserController {
	@Autowired
    private UserService userService;

	@Operation(
		summary = "Store a user",
		operationId = "storeUser",
		responses = {
			@ApiResponse(
				responseCode = "201",
				description = "User created",
				content = {
					@Content(
						mediaType = "application/json",
						schema = @Schema(implementation = UserResponse.class)
					)
				}
			),
			@ApiResponse(
				responseCode = "409",
				description = "User already exist",
				content = @Content(
					mediaType = "application/json",
					schema = @Schema(implementation = ErrorResponse.class)
				)
			),
		}
	)
	@PostMapping("/user")
	public ResponseEntity<Object> storeUser(@RequestBody @Valid UserDto userDto) {
			return userService.store(userDto);
	}

	@Operation(
		summary = "List all users",
		operationId = "listUsers",
		responses = {
			@ApiResponse(
				responseCode = "200",
				description = "Users found",
				content = {
					@Content(
						mediaType = "application/json",
						schema = @Schema(implementation = UserResponse.class)
					)   
				}
			)
		}
	)
	@GetMapping("/user")
	public ResponseEntity<List<UserModel>> findAll() {
		return userService.findAll();
	}

	@Operation(
		summary = "Get a user by id",
		operationId = "getUser",
		responses = {
			@ApiResponse(
				responseCode = "200",
				description = "User found",
				content = {
					@Content(
						mediaType = "application/json",
						schema = @Schema(implementation = UserResponse.class)
					)
				}
			),
			@ApiResponse(
				responseCode = "404",
				description = "User not found",
				content = {
					@Content(
						mediaType = "application/json",
						schema = @Schema(implementation = ErrorResponse.class)
					)
				}
			)
		}
	)
	@GetMapping("/user/{id}")
	public ResponseEntity<Object> findOne(@PathVariable UUID id) {
		return userService.findOne(id);
	}

	@Operation(
		summary = "Update a user",
		operationId = "updateUser",
		responses = {
			@ApiResponse(
				responseCode = "200",
				description = "User updated successfully",
				content = {
					@Content(
						mediaType = "application/json",
						schema = @Schema(implementation = UserResponse.class)
					)
				}
			),
			@ApiResponse(
				responseCode = "404",
				description = "User not found",
				content = {
					@Content(
						mediaType = "application/json",
						schema = @Schema(implementation = ErrorResponse.class)
					)
				}
			),
			@ApiResponse(
				responseCode = "400",
				description = "Unable to update email",
				content = {
					@Content(
						mediaType = "application/json",
						schema = @Schema(implementation = ErrorResponse.class)
					)
				}
			)
		}
	)
	@PutMapping("/user/{id}")
	public ResponseEntity<Object> update(@RequestBody @Valid UserDto userDto, @PathVariable UUID id) {
		return userService.updateUser(userDto, id);
	}

	@Operation(
		summary = "Delet a user",
		operationId = "deleteUser",
		responses = {
			@ApiResponse(
				responseCode = "200",
				description = "User deleted successfully"
			),
			@ApiResponse(
				responseCode = "404",
				description = "User not found",
				content = {
					@Content(
						mediaType = "application/json",
						schema = @Schema(implementation = ErrorResponse.class)
					)
				}
			)
		}
	)
	@DeleteMapping("/user/{id}")
	public ResponseEntity<Object> delete(@PathVariable UUID id) {
		return userService.delete(id);
	}
}
