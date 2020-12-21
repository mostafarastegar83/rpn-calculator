package com.mostafa.codechallenge.rpncalculator.service;


import static com.mostafa.codechallenge.rpncalculator.domain.operator.Operator.ADD;
import static com.mostafa.codechallenge.rpncalculator.domain.operator.Operator.CLEAR;
import static com.mostafa.codechallenge.rpncalculator.domain.operator.Operator.DIVIDE;
import static com.mostafa.codechallenge.rpncalculator.domain.operator.Operator.MULTIPLY;
import static com.mostafa.codechallenge.rpncalculator.domain.operator.Operator.SQRT;
import static com.mostafa.codechallenge.rpncalculator.domain.operator.Operator.SUBTRACT;
import static com.mostafa.codechallenge.rpncalculator.domain.operator.Operator.UNDO;
import static com.mostafa.codechallenge.rpncalculator.exception.Reason.INVALID_INPUT_VALUE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

import com.mostafa.codechallenge.rpncalculator.domain.Item;
import com.mostafa.codechallenge.rpncalculator.domain.OperatorItem;
import com.mostafa.codechallenge.rpncalculator.domain.Session;
import com.mostafa.codechallenge.rpncalculator.exception.InvalidOperandException;
import com.mostafa.codechallenge.rpncalculator.repository.ItemRepository;
import com.mostafa.codechallenge.rpncalculator.repository.OperatorItemRepository;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class OperatorServiceTest {

  @Mock
  private ItemRepository<Session, Item> itemRepository;
  @Mock
  private OperatorItemRepository<Session, OperatorItem> operatorItemRepository;
  @Mock
  private OperatorItemBuilder<Session, OperatorItem> operatorItemBuilder;

  @InjectMocks
  private OperatorService<Session, OperatorItem, Item> operatorService;

  void setup() {
    Session session = Session.builder().id(1L).build();
    List<Item> items = List
        .of(Item.builder().value(new BigDecimal("4")).session(session).isResultOfOthers(false)
                .build(),
            Item.builder().value(new BigDecimal("2")).session(session).isResultOfOthers(false)
                .build());
    when(itemRepository.pop(any(), anyInt())).thenReturn(items);
  }

  @Test
  void doOperationAdd() {
//    given
    setup();
//    when
    Optional<BigDecimal> resultOptional = operatorService
        .doOperation(Session.builder().id(1L).build(), ADD);
//    then
    assertThat(resultOptional).isNotEmpty();
    assertEquals(new BigDecimal("6"), resultOptional.get());
  }

  @Test
  void doOperationSubtract() {
//    given
    setup();
//    when
    Optional<BigDecimal> resultOptional = operatorService
        .doOperation(Session.builder().id(1L).build(), SUBTRACT);
//    then
    assertThat(resultOptional).isNotEmpty();
    assertEquals(new BigDecimal("2"), resultOptional.get());
  }

  @Test
  void doOperationMultiply() {
//    given
    setup();
//    when
    Optional<BigDecimal> resultOptional = operatorService
        .doOperation(Session.builder().id(1L).build(), MULTIPLY);
//    then
    assertThat(resultOptional).isNotEmpty();
    assertEquals(new BigDecimal("8"), resultOptional.get());
  }

  @Test
  void doOperationDivide() {
//    given
    setup();
//    when
    Optional<BigDecimal> resultOptional = operatorService
        .doOperation(Session.builder().id(1L).build(), DIVIDE);
//    then
    assertThat(resultOptional).isNotEmpty();
    assertEquals(new BigDecimal("2"), resultOptional.get());
  }

  @Test
  void doOperationDivideError() {
//    given
    Session session = Session.builder().id(1L).build();
    List<Item> items = List
        .of(Item.builder().value(new BigDecimal("4")).session(session).isResultOfOthers(false)
                .build(),
            Item.builder().value(new BigDecimal("0")).session(session).isResultOfOthers(false)
                .build());
    when(itemRepository.pop(any(), anyInt())).thenReturn(items);
//    when
    InvalidOperandException e = assertThrows(InvalidOperandException.class,
        () -> operatorService.doOperation(session, DIVIDE));
//    then
    assertEquals(INVALID_INPUT_VALUE, e.getReason());
  }

  @Test
  void doOperationSqrt() {
//    given
    setup();
//    when
    Optional<BigDecimal> resultOptional = operatorService
        .doOperation(Session.builder().id(1L).build(), SQRT);
//    then
    assertThat(resultOptional).isNotEmpty();
    assertEquals(new BigDecimal("2"), resultOptional.get());
  }

  @Test
  void doOperationSqrtError() {
//    given
    Session session = Session.builder().id(1L).build();
    List<Item> items = List
        .of(Item.builder().value(new BigDecimal("-4")).session(session).isResultOfOthers(false)
            .build());
    when(itemRepository.pop(any(), anyInt())).thenReturn(items);
//    when
    InvalidOperandException e = assertThrows(InvalidOperandException.class,
        () -> operatorService.doOperation(session, SQRT));
//    then
    assertEquals(INVALID_INPUT_VALUE, e.getReason());
  }

  @Test
  void doOperationClear() {
//    given
//    when
    Optional<BigDecimal> resultOptional = operatorService
        .doOperation(Session.builder().id(1L).build(), CLEAR);
//    then
    assertThat(resultOptional).isEmpty();
  }

  @Test
  void doOperationUndo() {
//    given
//    when
    Optional<BigDecimal> resultOptional = operatorService
        .doOperation(Session.builder().id(1L).build(), UNDO);
//    then
    assertThat(resultOptional).isEmpty();
  }
}