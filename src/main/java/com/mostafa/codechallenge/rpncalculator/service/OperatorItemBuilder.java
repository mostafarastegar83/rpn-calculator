package com.mostafa.codechallenge.rpncalculator.service;

import com.mostafa.codechallenge.rpncalculator.domain.OperatorItem;
import com.mostafa.codechallenge.rpncalculator.domain.Session;
import com.mostafa.codechallenge.rpncalculator.domain.operator.Operator;

public interface OperatorItemBuilder<S extends Session, OI extends OperatorItem> {
  OI build(S session, Operator operator);

}
