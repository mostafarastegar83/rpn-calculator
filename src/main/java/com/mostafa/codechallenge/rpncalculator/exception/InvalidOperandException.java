package com.mostafa.codechallenge.rpncalculator.exception;

import lombok.Getter;

@Getter
public class InvalidOperandException extends RuntimeException{
  private final Reason reason;

  public InvalidOperandException(Reason reason) {
    this.reason = reason;
  }
  public InvalidOperandException(Reason reason, Throwable e) {
    super(e);
    this.reason = reason;
  }
}
