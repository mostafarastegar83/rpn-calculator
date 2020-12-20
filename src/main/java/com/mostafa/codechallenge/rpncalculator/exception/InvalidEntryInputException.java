package com.mostafa.codechallenge.rpncalculator.exception;

import lombok.Getter;

@Getter
public class InvalidEntryInputException extends RuntimeException {

  private final String input;

  public InvalidEntryInputException(String input) {
    super();
    this.input = input;
  }
}
