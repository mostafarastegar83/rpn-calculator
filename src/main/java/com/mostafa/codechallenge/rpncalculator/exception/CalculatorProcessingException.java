package com.mostafa.codechallenge.rpncalculator.exception;

import lombok.Getter;

@Getter
public class CalculatorProcessingException extends RuntimeException {

  private final String errorMessage;

  public CalculatorProcessingException(String errorMessage) {
    this.errorMessage = errorMessage;
  }
}
