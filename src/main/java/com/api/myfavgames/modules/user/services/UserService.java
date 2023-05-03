package com.api.myfavgames.modules.user.services;

import com.api.myfavgames.modules.user.dtos.UserDto;
import com.api.myfavgames.modules.user.models.UserModel;
import com.api.myfavgames.modules.user.repositories.UserRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {
	@Autowired
	private UserRepository userRepository;

	private static final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

	@Transactional
	public ResponseEntity<Object> store(UserDto userDto) {
		boolean checkIfEmailRegistered = isEmailRegistered(userDto.getEmail());

		if (checkIfEmailRegistered) {
				return ResponseEntity.status(HttpStatus.CONFLICT).body("CONFLICT: Email already registered");
		}

		var hashedPassword = hashPassword(userDto.getPassword());
		userDto.setPassword(hashedPassword);

		UserModel user = new UserModel();

		BeanUtils.copyProperties(userDto, user);

		userRepository.save(user);

		return ResponseEntity.ok().body("CREATED: User created");
	}

	private boolean isEmailRegistered(String email) {
		return userRepository.existsByEmail(email);
	}

	public ResponseEntity<List<UserModel>> findAll() {
		return ResponseEntity.ok().body(userRepository.findAll());
	}

	public ResponseEntity<Object> findOne(UUID id) {
		Optional<UserModel> user = userRepository.findById(id);

		if (user.isEmpty()) {
				return ResponseEntity.badRequest().body("NOT FOUND: User not found");
		}

		return ResponseEntity.ok().body(user);
	}

	public ResponseEntity<Object> updateUser(UserDto userDto, UUID id) {
		Optional<UserModel> user = userRepository.findById(id);

		if (user.isEmpty()) {
				return ResponseEntity.badRequest().body("NOT FOUND: User not found!");
		}

		var userModel = user.get();

		var checkPassrod = checkPassword(userModel.getPassword(), userDto.getPassword());

		if (!checkPassrod) {
				var newPassword = hashPassword(userDto.getPassword());
				userModel.setPassword(newPassword);
		}

		if (!Objects.equals(userDto.getEmail(), userModel.getEmail())) {
				return ResponseEntity.badRequest().body("BAD REQUEST: Unable to update email");
		}

		userModel.setUsername(userDto.getUsername());

		return ResponseEntity.ok().body(userRepository.save(userModel));
	}

	private String hashPassword(String password) {
		return passwordEncoder.encode(password);
	}

	private boolean checkPassword(@Valid String bodyPassword, String password) {
		return passwordEncoder.matches(password, bodyPassword);
	}

	public ResponseEntity<Object> delete(UUID id) {
		var user = userRepository.findById(id);

		if (user.isEmpty()) {
			return ResponseEntity.badRequest().body("NOT FOUND: User not found!");
		}

		userRepository.delete(user.get());

		return ResponseEntity.ok().body("OK: Success in deleting user!");
	}
}
