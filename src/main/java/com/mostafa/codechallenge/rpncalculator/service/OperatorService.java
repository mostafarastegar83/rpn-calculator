package com.mostafa.codechallenge.rpncalculator.service;

import static com.mostafa.codechallenge.rpncalculator.domain.operator.Operator.UNDO;
import static com.mostafa.codechallenge.rpncalculator.domain.operator.OperatorType.BINARY;
import static com.mostafa.codechallenge.rpncalculator.domain.operator.OperatorType.UNARY;
import static com.mostafa.codechallenge.rpncalculator.exception.Reason.INVALID_INPUT_VALUE;

import com.mostafa.codechallenge.rpncalculator.domain.Item;
import com.mostafa.codechallenge.rpncalculator.domain.OperatorItem;
import com.mostafa.codechallenge.rpncalculator.domain.Session;
import com.mostafa.codechallenge.rpncalculator.domain.operator.Operator;
import com.mostafa.codechallenge.rpncalculator.exception.InvalidOperandException;
import com.mostafa.codechallenge.rpncalculator.repository.ItemRepository;
import com.mostafa.codechallenge.rpncalculator.repository.OperatorItemRepository;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class OperatorService<S extends Session, OI extends OperatorItem, I extends Item> {

  private final ItemRepository<S, I> itemRepository;
  private final OperatorItemRepository<S, OI> operatorItemRepository;
  private final OperatorItemBuilder<S, OI> operatorItemBuilder;


  public Optional<BigDecimal> doOperation(S session, Operator operator) {
    Optional<BigDecimal> result;
    List<I> items = Collections.emptyList();
    try {
      if (operator.getType().equals(BINARY)) {
        items = itemRepository.pop(session, 2);
        result = operator.doOperation(items.get(0).getValue(), items.get(1).getValue());
      } else if (operator.getType().equals(UNARY)) {
        items = itemRepository.pop(session, 1);
        result = operator.doOperation(items.get(0).getValue());
      } else {
        handleZeroOperandOperator(session, operator);
        result = operator.doOperation();
      }
      save(session, operator);
    } catch (InvalidOperandException e) {
      if (INVALID_INPUT_VALUE.equals(e.getReason())) {
        itemRepository.popFromUndo(session);
        items.forEach(itemRepository::push);
      }
      throw e;
    }
    return result;
  }

  private void save(S session, Operator operator) {
    if (UNDO.equals(operator)) {
      operatorItemRepository.pop(session);
    } else {
      operatorItemRepository.push(operatorItemBuilder.build(session, operator));
    }
  }

  private void handleZeroOperandOperator(S session, Operator operator) {
    switch (operator) {
      case CLEAR:
        itemRepository.clear(session);
        break;
      case UNDO:
        itemRepository.undo(session);
        break;
    }
  }
}
