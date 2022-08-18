package com.verifone.simcard.exception;

import java.util.function.Supplier;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class AppException extends RuntimeException implements Supplier<AppException> {

  HttpStatus code;
  private String[] args;

  public AppException(AppErrorCodes errorCodes) {
    super(errorCodes.getMessage());
    this.code = errorCodes.getHttpStatus();
  }

  public AppException(AppErrorCodes errorCodes, String... args) {
    super(errorCodes.getMessage());
    this.code = errorCodes.getHttpStatus();
    this.args = args;
  }

  public HttpStatus getHttpStatus() {
    return code;
  }

  @Override
  public AppException get() {
    return null;
  }
}
