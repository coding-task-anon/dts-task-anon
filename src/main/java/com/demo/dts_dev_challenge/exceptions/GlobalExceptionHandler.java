package com.demo.dts_dev_challenge.exceptions;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

  @Override
  protected ResponseEntity<Object> handleHttpMessageNotReadable(
      HttpMessageNotReadableException ex,
      HttpHeaders headers,
      HttpStatusCode status,
      WebRequest request) {

    log.error("JSON parsing/conversion error: {}", ex.getMessage(), ex);

    CustomErrorResponse error = new CustomErrorResponse(HttpStatus.BAD_REQUEST, ex.getMessage());
    return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(IllegalArgumentException.class)
  public ResponseEntity<CustomErrorResponse> handleIllegalArgumentException(
      IllegalArgumentException ex) {
    log.error("Illegal argument exception occurred: {}", ex.getMessage(), ex);
    CustomErrorResponse customErrorResponse =
        new CustomErrorResponse(HttpStatus.BAD_REQUEST, "Invalid request data: " + ex.getMessage());
    return new ResponseEntity<>(customErrorResponse, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(RuntimeException.class)
  public ResponseEntity<CustomErrorResponse> handleRuntimeException(RuntimeException ex) {
    log.error("Runtime exception occurred: {}", ex.getMessage(), ex);
    CustomErrorResponse customErrorResponse =
        new CustomErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
    return new ResponseEntity<>(customErrorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<CustomErrorResponse> handleGenericException(Exception ex) {
    log.error("Unexpected exception occurred: {}", ex.getMessage(), ex);
    CustomErrorResponse customErrorResponse =
        new CustomErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, "An unexpected error occurred");
    return new ResponseEntity<>(customErrorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
  }
}
