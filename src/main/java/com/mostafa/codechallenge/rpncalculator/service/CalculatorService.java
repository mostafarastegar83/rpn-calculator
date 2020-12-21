package com.mostafa.codechallenge.rpncalculator.service;

import com.mostafa.codechallenge.rpncalculator.domain.Item;
import com.mostafa.codechallenge.rpncalculator.domain.OperatorItem;
import com.mostafa.codechallenge.rpncalculator.domain.Session;
import com.mostafa.codechallenge.rpncalculator.domain.operator.Operator;
import com.mostafa.codechallenge.rpncalculator.repository.ItemRepository;
import java.math.BigDecimal;
import java.util.List;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CalculatorService<S extends Session, OI extends OperatorItem, I extends Item> {

  private final OperatorService<S, OI, I> operatorService;
  private final ItemRepository<S, I> itemRepository;
  private final ItemBuilder<S, I> itemBuilder;

  public void processOperator(S session, Operator operator) {
    operatorService.doOperation(session, operator)
        .ifPresent(value -> itemRepository.push(itemBuilder.build(session, value, true)));
  }

  public void storeNewInput(S session, BigDecimal value) {
    itemRepository.push(itemBuilder.build(session, value, false));
  }

  public List<I> getStackDetails(S session) {
    return itemRepository.itemsInMainStack(session);
  }
}
