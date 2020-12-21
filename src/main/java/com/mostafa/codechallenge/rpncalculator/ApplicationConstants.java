package com.mostafa.codechallenge.rpncalculator;

import java.math.MathContext;

public class ApplicationConstants {

  public static final int DECIMAL_PLACES = 15;
  public static final int DECIMAL_PLACES_DISPLAY = 10;
  public static final MathContext MATH_CONTEXT = new MathContext(DECIMAL_PLACES);
  public static final String INVALID_INPUT_ERROR_MESSAGE = "operator %s (position: %s) is not proper";
  public static final String PARAMETERS_ERROR_MESSAGE = "operator %s (position: %s): %s";
  public static final String WHITE_SPACE = " ";
  public static final String EXIT = "exit";
}
