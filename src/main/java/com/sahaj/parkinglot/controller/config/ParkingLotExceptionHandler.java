package com.sahaj.parkinglot.controller.config;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ParkingLotExceptionHandler {

  @ExceptionHandler(value = RuntimeException.class)
  public String handleException(RuntimeException exception) {
    return "{ \"error\":\""+exception.getMessage()+"\"}";
  }
}
