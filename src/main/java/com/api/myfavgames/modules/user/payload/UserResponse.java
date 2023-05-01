package com.api.myfavgames.modules.user.payload;

import java.sql.Timestamp;
import java.util.UUID;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "UserResponse", description = "Payload o return a user")
public class UserResponse {
  @Schema(name = "id", description = "A user id")
  private UUID id;

  @Schema(name = "username", description = "A user username")
  private String username;

  @Schema(name = "email", description = "A user email")
  private String email;

  @Schema(name = "created at", description = "When a user was created")
  private Timestamp createdAt;

  @Schema(name = "updated at", description = "When a user was updated")
  private Timestamp updatedAt;

  public UUID getId() {
    return this.id;
  }

  public void setId(UUID id) {
    this.id = id;
  }

  public String getUsername() {
    return this.username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getEmail() {
    return this.email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public Timestamp getCreatedAt() {
    return this.createdAt;
  }

  public void setCreatedAt(Timestamp createdAt) {
    this.createdAt = createdAt;
  }

  public Timestamp getUpdatedAt() {
    return this.updatedAt;
  }

  public void setUpdatedAt(Timestamp updatedAt) {
    this.updatedAt = updatedAt;
  }

}
