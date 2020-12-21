package com.mostafa.codechallenge.rpncalculator.service;

import com.mostafa.codechallenge.rpncalculator.domain.Item;
import com.mostafa.codechallenge.rpncalculator.domain.Session;
import java.math.BigDecimal;

public interface ItemBuilder<S extends Session, I extends Item> {

  I build(S session, BigDecimal value, Boolean isResult);

}
