package com.mostafa.codechallenge.rpncalculator.exception;

import lombok.Getter;

@Getter
public enum Reason {
  INVALID_INPUT_VALUE("invalid input parameter"), INVALID_INPUT_COUNT("insufficient parameters");
  private final String message;

  Reason(String message) {
    this.message = message;
  }
}
