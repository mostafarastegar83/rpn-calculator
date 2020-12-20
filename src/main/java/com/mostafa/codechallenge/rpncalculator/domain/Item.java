package com.mostafa.codechallenge.rpncalculator.domain;

import com.mostafa.codechallenge.rpncalculator.util.CalculatorUtil;
import java.math.BigDecimal;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Item {

  private final BigDecimal value;
  private final Session session;
  /**
   * determines whether the item is result of operation or just new item. The processor should make
   * an upfront decision for undo operation accordingly.
   */
  private final Boolean isResultOfOthers;

  @Override
  public String toString() {
    return CalculatorUtil.getFormattedBigDecimal(value);
  }
}
