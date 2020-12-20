package com.mostafa.codechallenge.rpncalculator.service;


import static com.mostafa.codechallenge.rpncalculator.domain.operator.Operator.ADD;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.mostafa.codechallenge.rpncalculator.domain.Item;
import com.mostafa.codechallenge.rpncalculator.domain.OperatorItem;
import com.mostafa.codechallenge.rpncalculator.domain.Session;
import com.mostafa.codechallenge.rpncalculator.repository.ItemRepository;
import java.math.BigDecimal;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class CalculatorServiceTest {

  @Mock
  private OperatorService<Session, OperatorItem, Item> operatorService;
  @Mock
  private ItemRepository<Session, Item> itemRepository;
  @Mock
  private ItemBuilder<Session, Item> itemBuilder;

  @InjectMocks
  private CalculatorService<Session, OperatorItem, Item> calculatorService;

  void setup() {
    when(operatorService.doOperation(any(), eq(ADD))).thenReturn(Optional.of(new BigDecimal("4")));
  }

  @Test
  void processOperator() {
//    given
    setup();
//    when
    Session session = Session.builder().id(1L).build();
    calculatorService.processOperator(session, ADD);
//    then
    Item item = verify(itemBuilder, times(1)).build(session, new BigDecimal("4"), true);
    verify(itemRepository, times(1)).push(item);
  }

  @Test
  void storeNewInput() {
//    given
    Session session = Session.builder().id(1L).build();
//    when
    calculatorService.storeNewInput(session, new BigDecimal("4"));
//    then
    verify(itemBuilder, times(1)).build(session, new BigDecimal("4"), false);
  }

  @Test
  void getStackDetails() {
//    given
    Session session = Session.builder().id(1L).build();
//    when
    calculatorService.getStackDetails(session);
//    then
    verify(itemRepository, times(1)).itemsInMainStack(session);
  }
}