package com.mostafa.codechallenge.rpncalculator.util;

import static com.mostafa.codechallenge.rpncalculator.ApplicationConstants.DECIMAL_PLACES_DISPLAY;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class CalculatorUtil {

  public static String getFormattedBigDecimal(BigDecimal value) {
    double d = value.setScale(DECIMAL_PLACES_DISPLAY, RoundingMode.HALF_UP).doubleValue();
    if (d == (long) d) {
      return String.format("%d", (long) d);
    } else {
      return String.format("%s", d);
    }
  }
}
