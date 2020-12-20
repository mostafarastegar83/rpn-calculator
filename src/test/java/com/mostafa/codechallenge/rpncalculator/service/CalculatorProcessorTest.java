package com.mostafa.codechallenge.rpncalculator.service;


import static com.mostafa.codechallenge.rpncalculator.ApplicationConstants.INVALID_INPUT_ERROR_MESSAGE;
import static com.mostafa.codechallenge.rpncalculator.ApplicationConstants.PARAMETERS_ERROR_MESSAGE;
import static com.mostafa.codechallenge.rpncalculator.domain.operator.Operator.ADD;
import static com.mostafa.codechallenge.rpncalculator.exception.Reason.INVALID_INPUT_COUNT;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.mostafa.codechallenge.rpncalculator.domain.Item;
import com.mostafa.codechallenge.rpncalculator.domain.OperatorItem;
import com.mostafa.codechallenge.rpncalculator.domain.Session;
import com.mostafa.codechallenge.rpncalculator.exception.CalculatorProcessingException;
import com.mostafa.codechallenge.rpncalculator.exception.InvalidOperandException;
import java.math.BigDecimal;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class CalculatorProcessorTest {

  @Mock
  private CalculatorService<Session, OperatorItem, Item> calculatorService;

  @InjectMocks
  private CalculatorProcessor<Session, OperatorItem, Item> calculatorProcessor;

  @Test
  void processNewEntry() {
//    given
    Session session = Session.builder().id(1L).build();
//    when
    calculatorProcessor.processNewEntry(session, "4 2 +");
//    then
    verify(calculatorService, times(1)).storeNewInput(session, new BigDecimal("4"));
    verify(calculatorService, times(1)).storeNewInput(session, new BigDecimal("2"));
    verify(calculatorService, times(1)).processOperator(session, ADD);
  }

  @Test
  void processNewEntryErrorInput() {
//    given
    Session session = Session.builder().id(1L).build();
//    when
    CalculatorProcessingException e = assertThrows(CalculatorProcessingException.class,
        () -> calculatorProcessor.processNewEntry(session, "asdfadsf"));
//    then
    assertEquals(String.format(INVALID_INPUT_ERROR_MESSAGE, "asdfadsf", 1), e.getErrorMessage());
  }

  @Test
  void processNewEntryErrorOperatorParameters() {
//    given
    Session session = Session.builder().id(1L).build();
    doThrow(new InvalidOperandException(INVALID_INPUT_COUNT)).when(calculatorService)
        .processOperator(any(), any());
//    when
    CalculatorProcessingException e = assertThrows(CalculatorProcessingException.class,
        () -> calculatorProcessor.processNewEntry(session, "1 +"));
//    then
    assertEquals(String.format(PARAMETERS_ERROR_MESSAGE, "+", 3, "insufficient parameters"),
        e.getErrorMessage());
  }

  @Test
  void getStackValueString() {
//    given
    Session session = Session.builder().id(1L).build();
    when(calculatorService.getStackDetails(any())).thenReturn(List.of(
        Item.builder().value(new BigDecimal("4")).session(session).isResultOfOthers(false).build(),
        Item.builder().value(new BigDecimal("2")).session(session).isResultOfOthers(false)
            .build()));
//    when
    String result = calculatorProcessor.getStackValueString(session);
//    then
    assertEquals("stack: 4 2", result);
  }

}