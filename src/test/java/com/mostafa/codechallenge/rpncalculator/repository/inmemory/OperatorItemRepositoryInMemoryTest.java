package com.mostafa.codechallenge.rpncalculator.repository.inmemory;


import static com.mostafa.codechallenge.rpncalculator.domain.operator.Operator.ADD;
import static com.mostafa.codechallenge.rpncalculator.domain.operator.Operator.SUBTRACT;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.mostafa.codechallenge.rpncalculator.domain.OperatorItem;
import com.mostafa.codechallenge.rpncalculator.domain.Session;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class OperatorItemRepositoryInMemoryTest {

  private OperatorItemRepositoryInMemory<Session, OperatorItem> operatorItemRepositoryInMemory;

  @BeforeEach
  void setup() {
    this.operatorItemRepositoryInMemory = new OperatorItemRepositoryInMemory();
  }

  private void init() {
    this.operatorItemRepositoryInMemory.push(
        OperatorItem.builder().operator(ADD).session(Session.builder().id(1l).build()).build());
    this.operatorItemRepositoryInMemory.push(
        OperatorItem.builder().operator(SUBTRACT).session(Session.builder().id(2l).build())
            .build());
  }

  @Test
  void testPush() {
//    given
    init();
//    when
    List<OperatorItem> ois1 = this.operatorItemRepositoryInMemory
        .operatorItemsInMainStack(Session.builder().id(1l).build());
    List<OperatorItem> ois2 = this.operatorItemRepositoryInMemory
        .operatorItemsInMainStack(Session.builder().id(1l).build());
//    then
    assertEquals(1, ois1.size());
    assertEquals(ADD, ois1.get(0).getOperator());
    assertEquals(1, ois2.size());
    assertEquals(ADD, ois2.get(0).getOperator());
  }

  @Test
  void testPop() {
//    given
    init();
//    when
    Optional<OperatorItem> oiOptional = this.operatorItemRepositoryInMemory
        .pop(Session.builder().id(1l).build());
//    then
    assertThat(oiOptional).isNotEmpty();
    assertEquals(ADD, oiOptional.get().getOperator());
  }

  @Test
  void testPopEmpty() {
//    given
    init();
//    when
    Session session = Session.builder().id(1l).build();
    this.operatorItemRepositoryInMemory.pop(session);
    Optional<OperatorItem> oiOptional = this.operatorItemRepositoryInMemory.pop(session);
//    then
    assertThat(oiOptional).isEmpty();
  }
}