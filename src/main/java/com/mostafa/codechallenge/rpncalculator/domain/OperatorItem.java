package com.mostafa.codechallenge.rpncalculator.domain;

import com.mostafa.codechallenge.rpncalculator.domain.operator.Operator;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OperatorItem {

  private final Operator operator;
  private final Session session;
}
