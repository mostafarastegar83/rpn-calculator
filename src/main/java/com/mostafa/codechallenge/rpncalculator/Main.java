package com.mostafa.codechallenge.rpncalculator;

import static com.mostafa.codechallenge.rpncalculator.ApplicationConstants.EXIT;

import com.mostafa.codechallenge.rpncalculator.domain.Item;
import com.mostafa.codechallenge.rpncalculator.domain.OperatorItem;
import com.mostafa.codechallenge.rpncalculator.domain.Session;
import com.mostafa.codechallenge.rpncalculator.exception.CalculatorProcessingException;
import com.mostafa.codechallenge.rpncalculator.repository.ItemRepository;
import com.mostafa.codechallenge.rpncalculator.repository.OperatorItemRepository;
import com.mostafa.codechallenge.rpncalculator.repository.inmemory.ItemRepositoryInMemory;
import com.mostafa.codechallenge.rpncalculator.repository.inmemory.OperatorItemRepositoryInMemory;
import com.mostafa.codechallenge.rpncalculator.service.CalculatorProcessor;
import com.mostafa.codechallenge.rpncalculator.service.CalculatorService;
import com.mostafa.codechallenge.rpncalculator.service.OperatorService;
import java.util.Scanner;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Main {

  public static void main(String[] args) {
    CalculatorProcessor<Session, OperatorItem, Item> calculatorProcessor = getCalculatorProcessor();
    Session session = Session.builder().id(1L).build();
    Scanner scanner = new Scanner(System.in);
    System.out.println("Please enter your inputs:");
    while (scanner.hasNextLine()) {
      try {
        String entry = scanner.nextLine();
        if (entry.equalsIgnoreCase(EXIT)) {
          break;
        }
        log.info("new entry: {}", entry);
        calculatorProcessor.processNewEntry(session, entry);
      } catch (CalculatorProcessingException e) {
        log.error(e.getErrorMessage());
        System.out.println(e.getErrorMessage());
      }
      String result = calculatorProcessor.getStackValueString(session);
      log.info(result);
      System.out.println(result);
      System.out.println("Enter next inputs(To exit the application, type exit):");
    }
  }

  // in a context aware framework, such as spring framework,  the dependency injection is done
  // automatically
  private static CalculatorProcessor<Session, OperatorItem, Item> getCalculatorProcessor() {
    ItemRepository<Session, Item> itemRepository = new ItemRepositoryInMemory<>();
    OperatorItemRepository<Session, OperatorItem> operatorItemRepository = new OperatorItemRepositoryInMemory<>();
    OperatorService<Session, OperatorItem, Item> operatorService = new OperatorService(
        itemRepository, operatorItemRepository,
        (session, operator) -> OperatorItem.builder().session(session).operator(operator).build());
    CalculatorService<Session, OperatorItem, Item> calculatorService = new CalculatorService(
        operatorService, itemRepository,
        (session, value, isResult) -> Item.builder().session(session).value(value)
            .isResultOfOthers(isResult).build());
    CalculatorProcessor<Session, OperatorItem, Item> calculatorProcessor = new CalculatorProcessor(
        calculatorService);
    return calculatorProcessor;
  }
}
