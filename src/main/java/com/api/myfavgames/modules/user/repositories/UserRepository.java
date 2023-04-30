package com.api.myfavgames.modules.user.repositories;

import com.api.myfavgames.modules.user.models.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserRepository extends JpaRepository<UserModel, UUID> {
  boolean existsByEmail(String email);
  UserModel getByEmail(String email);
}
