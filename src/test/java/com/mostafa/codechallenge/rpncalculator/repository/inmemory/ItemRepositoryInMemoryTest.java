package com.mostafa.codechallenge.rpncalculator.repository.inmemory;


import static com.mostafa.codechallenge.rpncalculator.exception.Reason.INVALID_INPUT_COUNT;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.mostafa.codechallenge.rpncalculator.domain.Item;
import com.mostafa.codechallenge.rpncalculator.domain.Session;
import com.mostafa.codechallenge.rpncalculator.exception.InvalidOperandException;
import java.math.BigDecimal;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ItemRepositoryInMemoryTest {

  private ItemRepositoryInMemory<Session, Item> itemRepositoryInMemory;

  @BeforeEach
  void setup() {
    itemRepositoryInMemory = new ItemRepositoryInMemory<>();
  }

  private void init() {
    Session session1 = Session.builder().id(1L).build();
    itemRepositoryInMemory.push(
        Item.builder().session(session1).value(new BigDecimal("1")).isResultOfOthers(false)
            .build());
    itemRepositoryInMemory.push(
        Item.builder().session(session1).value(new BigDecimal("2")).isResultOfOthers(false)
            .build());
    itemRepositoryInMemory.push(
        Item.builder().session(session1).value(new BigDecimal("3")).isResultOfOthers(false)
            .build());
    Session session2 = Session.builder().id(2L).build();
    itemRepositoryInMemory.push(
        Item.builder().session(session2).value(new BigDecimal("4")).isResultOfOthers(false)
            .build());
    itemRepositoryInMemory.push(
        Item.builder().session(session2).value(new BigDecimal("5")).isResultOfOthers(true).build());
  }

  @Test
  void push() {
//    given
//    when
    init();
//    then
    int size1 = itemRepositoryInMemory.size(Session.builder().id(1L).build());
    int size2 = itemRepositoryInMemory.size(Session.builder().id(2L).build());
    assertEquals(3, size1);
    assertEquals(2, size2);
  }

  @Test
  void pop() {
//    given
    init();
//    when
    List<Item> items = itemRepositoryInMemory.pop(Session.builder().id(1L).build(), 2);
//    then
    assertEquals(2, items.size());
  }

  @Test()
  void popBiggerSize() {
//    given
    init();
//    when
    InvalidOperandException e = assertThrows(
        InvalidOperandException.class,
        () -> itemRepositoryInMemory.pop(Session.builder().id(1L).build(), 4));
//    then
    assertEquals(INVALID_INPUT_COUNT, e.getReason());
  }

  @Test
  void undo() {
//    given
    Session session = Session.builder().id(1L).build();
    init();
    List<Item> pop = itemRepositoryInMemory.pop(session, 2);
    itemRepositoryInMemory.push(
        Item.builder().session(session).value(new BigDecimal("5")).isResultOfOthers(true).build());
//    when
    itemRepositoryInMemory.undo(session);
//    then
    int size = itemRepositoryInMemory.size(session);
    assertEquals(3, size);
  }

  @Test
  void undoError() {
//    given
    init();
    Session session = Session.builder().id(2L).build();
//    when
    IllegalStateException e = assertThrows(IllegalStateException.class,
        () -> itemRepositoryInMemory.undo(session));
//    then
    assertThat(e).hasMessage("Items in undo stack is empty");
  }

  @Test
  void itemsInStack() {
//    given
    init();
    Session session = Session.builder().id(1L).build();
//    when
    List<Item> items = itemRepositoryInMemory.itemsInMainStack(session);
//    then
    assertEquals(3, items.size());
  }

  @Test
  void popFromUndo() {
//    given
    Session session = Session.builder().id(1L).build();
    init();
    itemRepositoryInMemory.pop(session, 2);
//    when
    List<Item> items = itemRepositoryInMemory.popFromUndo(session);
//    then
    assertEquals(2, items.size());
  }

  @Test
  void popFromUndoEmpty() {
//    given
    Session session = Session.builder().id(1L).build();
//    when
    List<Item> items = itemRepositoryInMemory.popFromUndo(session);
//    then
    assertEquals(0, items.size());
  }
}