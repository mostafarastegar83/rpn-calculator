package com.mostafa.codechallenge.rpncalculator.util;


import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;
import org.junit.jupiter.api.Test;

class CalculatorUtilTest {

  @Test
  void getFormattedBigDecimalWithoutDecimal() {
    String val = "65345235243436";
    String formattedBigDecimal = CalculatorUtil.getFormattedBigDecimal(new BigDecimal(val));
    assertEquals(val, formattedBigDecimal);
  }

  @Test
  void getFormattedBigDecimalWithDecimalLessThan10() {
    String val = "3.14";
    String formattedBigDecimal = CalculatorUtil.getFormattedBigDecimal(new BigDecimal(val));
    assertEquals(val, formattedBigDecimal);
  }

  @Test
  void getFormattedBigDecimalWithDecimalMoreThan10() {
    String val = "3.14159265358979";
    String formattedBigDecimal = CalculatorUtil.getFormattedBigDecimal(new BigDecimal(val));
    assertEquals("3.1415926536", formattedBigDecimal);
  }

  @Test
  void getFormattedBigDecimalWithDecimalMoreThan15() {
    String val = "3.141592653589793238";
    String formattedBigDecimal = CalculatorUtil.getFormattedBigDecimal(new BigDecimal(val));
    assertEquals("3.1415926536", formattedBigDecimal);
  }


  @Test
  void getFormattedBigDecimalWithExtraZeros() {
    String val = "00100.0012000";
    String formattedBigDecimal = CalculatorUtil.getFormattedBigDecimal(new BigDecimal(val));
    assertEquals("100.0012", formattedBigDecimal);
  }

}