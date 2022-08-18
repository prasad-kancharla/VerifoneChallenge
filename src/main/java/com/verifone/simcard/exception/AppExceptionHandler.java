package com.verifone.simcard.exception;

import lombok.Getter;
import lombok.Setter;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class AppExceptionHandler extends ResponseEntityExceptionHandler {


  @ExceptionHandler({AppException.class})
  protected ResponseEntity<Object> handleUserExceptions(AppException exception,
      WebRequest request) {
    ErrorDetail errorDetail = new ErrorDetail(exception);
    HttpHeaders headers = this.getRequiredHeaders();
    return this.handleExceptionInternal(exception, errorDetail, headers,
        exception.getHttpStatus(),
        request);
  }

  @ExceptionHandler({DataIntegrityViolationException.class})
  protected ResponseEntity<Object> handleDataIntegrityViolationException(
      DataIntegrityViolationException exception,
      WebRequest request) {
    HttpHeaders headers = this.getRequiredHeaders();
    return this.handleExceptionInternal(exception, new ErrorDetail(exception), headers,
        HttpStatus.BAD_REQUEST,
        request);
  }

  @ExceptionHandler({Exception.class})
  protected ResponseEntity<Object> handleBaseExceptions(Exception exception,
      WebRequest request) {
    HttpHeaders headers = this.getRequiredHeaders();
    return this.handleExceptionInternal(exception, new ErrorDetail(exception), headers,
        HttpStatus.INTERNAL_SERVER_ERROR,
        request);
  }

  @Override
  protected ResponseEntity<Object> handleMissingPathVariable(
      MissingPathVariableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
    return handleExceptionInternal(ex, new ErrorDetail(ex), headers, status, request);
  }

  @Override
  protected ResponseEntity<Object> handleMissingServletRequestParameter(
      MissingServletRequestParameterException ex, HttpHeaders headers, HttpStatus status,
      WebRequest request) {
    return handleExceptionInternal(ex, new ErrorDetail(ex), headers, status, request);
  }

  @Override
  protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
      HttpHeaders headers, HttpStatus status, WebRequest request) {
    return this.handleExceptionInternal(ex, new ErrorDetail(ex), headers, status, request);
  }


  private HttpHeaders getRequiredHeaders() {
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);
    return headers;
  }

  @Setter
  @Getter
  public static class ErrorDetail {

    private String code;
    private String message;

    public ErrorDetail(AppException e) {
      this.code = e.getCode().name();
      if (!ObjectUtils.isEmpty(e.getArgs())) {
        this.message = String.format(e.getMessage(), e.getArgs());
      } else {
        this.message = e.getMessage();
      }
    }

    ErrorDetail(MissingPathVariableException e) {
      this.code = "MISSING_PATH_VARIABLE";
      this.message = "Missing mandatory path variable : " + e.getVariableName();
    }

    ErrorDetail(MissingServletRequestParameterException e) {
      this.code = "MISSING_REQUEST_PARAMETER_VARIABLE";
      this.message = "Missing mandatory request parameter variable : " + e.getParameterName();
    }

    ErrorDetail(DataIntegrityViolationException e) {
      this.code = "DATA_INTEGRITY_VIOLATION";
      this.message = "Data Integrity is being violated";
    }

    ErrorDetail(Exception e) {
      this.code = "UNEXPECTED_ERROR";
      this.message = "Unexpected error";
    }
  }

}
