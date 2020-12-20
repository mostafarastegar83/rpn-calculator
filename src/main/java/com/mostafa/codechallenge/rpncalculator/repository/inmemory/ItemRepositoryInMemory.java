package com.mostafa.codechallenge.rpncalculator.repository.inmemory;

import static com.mostafa.codechallenge.rpncalculator.exception.Reason.INVALID_INPUT_COUNT;

import com.mostafa.codechallenge.rpncalculator.domain.Item;
import com.mostafa.codechallenge.rpncalculator.domain.Session;
import com.mostafa.codechallenge.rpncalculator.exception.InvalidOperandException;
import com.mostafa.codechallenge.rpncalculator.repository.ItemRepository;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;


public class ItemRepositoryInMemory<S extends Session, I extends Item> implements
    ItemRepository<S, I> {

  private final Map<Session, Stack<I>> mainStack = new HashMap<>();
  private final Map<Session, Stack<List<I>>> undoStack = new HashMap<>();

  @Override
  public void push(I item) {
    getStackFromMain(item.getSession()).push(item);
  }

  @Override
  public List<I> pop(S session, int numberOfItems) {
    if (numberOfItems > getStackFromMain(session).size()) {
      throw new InvalidOperandException(INVALID_INPUT_COUNT);
    }
    List<I> items = new ArrayList<>();
    for (int i = 0; i < numberOfItems; i++) {
      items.add(0, getStackFromMain(session).pop());
    }
    getStackFromUndo(session).push(items);
    return items;
  }

  @Override
  public List<I> popFromUndo(S session) {
    if (getStackFromUndo(session).size() > 0) {
      return getStackFromUndo(session).pop();
    } else {
      return Collections.emptyList();
    }
  }

  @Override
  public void undo(S session) {
    if (getStackFromMain(session).size() > 0) {
      I itemInMain = getStackFromMain(session).pop();
      if (itemInMain.getIsResultOfOthers()) {
        if (getStackFromUndo(session).isEmpty()) {
          throw new IllegalStateException("Items in undo stack is empty");
        }
        List<I> items = getStackFromUndo(session).pop();
        items.forEach(item -> getStackFromMain(session).push(item));
      }
    }
  }

  @Override
  public List<I> itemsInMainStack(S session) {
    return Collections.unmodifiableList(getStackFromMain(session));
  }

  @Override
  public int size(S session) {
    return getStackFromMain(session).size();
  }

  /**
   * this method is not thread safe
   *
   * @param session
   * @return stack details from main stack
   */
  private Stack<I> getStackFromMain(Session session) {
    if (!this.mainStack.containsKey(session)) {
      this.mainStack.put(session, new Stack<>());
    }
    return this.mainStack.get(session);
  }

  /**
   * this method is not thread safe
   *
   * @param session
   * @return stack details from undo stack
   */
  private Stack<List<I>> getStackFromUndo(Session session) {
    if (!this.undoStack.containsKey(session)) {
      this.undoStack.put(session, new Stack<>());
    }
    return this.undoStack.get(session);
  }
}
