package com.mostafa.codechallenge.rpncalculator.service;

import static com.mostafa.codechallenge.rpncalculator.ApplicationConstants.PARAMETERS_ERROR_MESSAGE;
import static com.mostafa.codechallenge.rpncalculator.ApplicationConstants.INVALID_INPUT_ERROR_MESSAGE;
import static com.mostafa.codechallenge.rpncalculator.ApplicationConstants.WHITE_SPACE;

import com.mostafa.codechallenge.rpncalculator.domain.Item;
import com.mostafa.codechallenge.rpncalculator.domain.OperatorItem;
import com.mostafa.codechallenge.rpncalculator.domain.Session;
import com.mostafa.codechallenge.rpncalculator.domain.operator.Operator;
import com.mostafa.codechallenge.rpncalculator.exception.CalculatorProcessingException;
import com.mostafa.codechallenge.rpncalculator.exception.InvalidEntryInputException;
import com.mostafa.codechallenge.rpncalculator.exception.InvalidOperandException;
import java.math.BigDecimal;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

@RequiredArgsConstructor
public class CalculatorProcessor<S extends Session, OI extends OperatorItem, I extends Item> {

  private final CalculatorService<S, OI, I> calculatorService;

  public void processNewEntry(S session, String entry) {
    String[] entryDetails = entry.split("\\s");
    int i = 0;
    try {
      for (i = 0; i < entryDetails.length; i++) {
        if (StringUtils.isNotBlank(entryDetails[i])) {
          processInput(session, entryDetails, i);
        }
      }
    } catch (InvalidEntryInputException e) {
      throw new CalculatorProcessingException(
          String
              .format(INVALID_INPUT_ERROR_MESSAGE, entryDetails[i], findPosition(entryDetails, i)));
    } catch (InvalidOperandException e) {
      throw new CalculatorProcessingException(
          String.format(PARAMETERS_ERROR_MESSAGE, entryDetails[i],
              findPosition(entryDetails, i), e.getReason().getMessage()));
    }
  }

  private void processInput(S session, String[] entryDetails, int i) {
    if (StringUtils.isNumeric(entryDetails[i])) {
      calculatorService.storeNewInput(session, new BigDecimal(entryDetails[i]));
    } else {
      Operator operator = Operator.getBySymbol(entryDetails[i]);
      calculatorService.processOperator(session, operator);
    }
  }

  private int findPosition(String[] entryDetails, int i) {
    int position;
    if (i == 0) {
      position = 1;
    } else {
      position = 2;
    }
    return position + String.join(WHITE_SPACE, ArrayUtils.subarray(entryDetails, 0, i))
        .length();
  }

  public String getStackValueString(S session) {
    return "stack: " + calculatorService.getStackDetails(session).stream().map(Item::toString)
        .collect(Collectors.joining(WHITE_SPACE));
  }
}
