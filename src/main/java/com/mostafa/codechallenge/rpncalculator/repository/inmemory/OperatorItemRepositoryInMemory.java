package com.mostafa.codechallenge.rpncalculator.repository.inmemory;

import com.mostafa.codechallenge.rpncalculator.domain.OperatorItem;
import com.mostafa.codechallenge.rpncalculator.domain.Session;
import com.mostafa.codechallenge.rpncalculator.repository.OperatorItemRepository;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Stack;

public class OperatorItemRepositoryInMemory<S extends Session, OI extends OperatorItem> implements
    OperatorItemRepository<S, OI> {

  private final Map<Session, Stack<OI>> stack = new HashMap<>();

  @Override
  public void push(OI operatorItem) {
    this.getStackFromMain(operatorItem.getSession()).push(operatorItem);
  }

  @Override
  public Optional<OI> pop(S session) {
    if (this.getStackFromMain(session).size() > 0) {
      return Optional.of(this.getStackFromMain(session).pop());
    }
    return Optional.empty();
  }

  @Override
  public List<OI> operatorItemsInMainStack(S session) {
    return Collections.unmodifiableList(getStackFromMain(session));
  }

  /**
   * this method is not thread safe
   *
   * @param session
   * @return stack details
   */
  private Stack<OI> getStackFromMain(Session session) {
    if (!this.stack.containsKey(session)) {
      this.stack.put(session, new Stack<>());
    }
    return this.stack.get(session);
  }
}
