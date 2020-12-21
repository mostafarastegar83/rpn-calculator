package com.mostafa.codechallenge.rpncalculator.repository;

import com.mostafa.codechallenge.rpncalculator.domain.Item;
import com.mostafa.codechallenge.rpncalculator.domain.Session;
import com.mostafa.codechallenge.rpncalculator.exception.InvalidOperandException;
import java.util.List;

/**
 * Repository for pushing, popping and fetching items from datasource
 */
public interface ItemRepository<S extends Session, I extends Item> {

  /**
   * @param session
   * @return the size of items in the main stack
   */
  int size(S session);

  /**
   * should push new item into the main stack
   *
   * @param item
   */
  void push(I item);

  /**
   * should pop a number of items from the stack and push them into the undo stack
   *
   * @param session
   * @param numberOfItems
   * @return a list of items
   * @throws InvalidOperandException - if proper number of items are not available in the stack
   */
  List<I> pop(S session, int numberOfItems);

  /**
   * should pop the last item from undo stack
   *
   * @param session
   * @return a list of items
   */
  List<I> popFromUndo(S session);

  /**
   * should pop from the undo stack and push items into the main stack
   *
   * @param session
   */
  void undo(S session);

  /**
   * retrieves the relevant items from the main stack
   *
   * @param session
   * @return list of items in the main stack
   */
  List<I> itemsInMainStack(S session);

  default void clear(S session) {
    pop(session, size(session));
  }
}
