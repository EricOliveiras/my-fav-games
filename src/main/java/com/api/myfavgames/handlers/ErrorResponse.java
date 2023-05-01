package com.api.myfavgames.handlers;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "ErrorResponse", description = "Payload to return an exception message")
public class ErrorResponse {
  @Schema(name = "statusCode", description = "Status code", example = "400")
  private int code;

  @Schema(name = "message", description = "Message", example = "Bad Request")
  private String message;

  @Schema(
    name = "timestamp",
    description = "Timestamp",
    example = "01/01/2000 00:00:00"
  )
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy hh:mm:ss")
  private LocalDateTime timestamp;

  public ErrorResponse(int code, String message) {
    this.code = code;
    this.message = message;
    this.timestamp = LocalDateTime.now();
  }

  public int getCode() {
    return code;
  }

  public void setCode(int code) {
    this.code = code;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public LocalDateTime getTimestamp() {
    return this.timestamp;
  }
  
  public void setTimestamp(LocalDateTime timestamp) {
    this.timestamp = timestamp;
  }
}

