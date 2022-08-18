package com.verifone.simcard.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum AppErrorCodes {

  INVALID_RECORD_ID("Record ID is not valid. Please enter a valid ID", HttpStatus.BAD_REQUEST),
  DUPLICATE_RECORD("Record is already present with this mobile number", HttpStatus.BAD_REQUEST),;

  private String message;
  private HttpStatus httpStatus;
}
