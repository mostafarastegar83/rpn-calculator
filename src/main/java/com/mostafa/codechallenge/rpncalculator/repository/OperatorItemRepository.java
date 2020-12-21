package com.mostafa.codechallenge.rpncalculator.repository;

import com.mostafa.codechallenge.rpncalculator.domain.OperatorItem;
import com.mostafa.codechallenge.rpncalculator.domain.Session;
import java.util.List;
import java.util.Optional;

/**
 * Repository for pushing, popping and fetching operator items from datasource
 */
public interface OperatorItemRepository<S extends Session, OI extends OperatorItem> {

  /**
   * should push new operatorItem into the main stack
   *
   * @param operatorItem
   */
  void push(OI operatorItem);

  /**
   * should pop the item from the stack and push it into the undo stack
   *
   * @param session
   * @return the operator item
   */
  Optional<OI> pop(S session);

  /**
   * retrieves the relevant items from the main stack
   *
   * @param session
   * @return stack structure
   */
  List<OI> operatorItemsInMainStack(S session);
}
