package com.mostafa.codechallenge.rpncalculator.domain.operator;

import static com.mostafa.codechallenge.rpncalculator.ApplicationConstants.MATH_CONTEXT;
import static com.mostafa.codechallenge.rpncalculator.domain.operator.OperatorType.BINARY;
import static com.mostafa.codechallenge.rpncalculator.domain.operator.OperatorType.UNARY;
import static com.mostafa.codechallenge.rpncalculator.domain.operator.OperatorType.ZERO_OPERAND;

import com.mostafa.codechallenge.rpncalculator.exception.InvalidEntryInputException;
import com.mostafa.codechallenge.rpncalculator.exception.InvalidOperandException;
import com.mostafa.codechallenge.rpncalculator.exception.Reason;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@RequiredArgsConstructor
@Getter
@ToString
public enum Operator {
  ADD("+", BINARY) {
    @Override
    protected Optional<BigDecimal> apply(BigDecimal opr1, BigDecimal opr2) {
      return Optional.of(opr1.add(opr2));
    }
  }, SUBTRACT("-", BINARY) {
    @Override
    protected Optional<BigDecimal> apply(BigDecimal opr1, BigDecimal opr2) {
      return Optional.of(opr1.subtract(opr2));
    }
  }, MULTIPLY("*", BINARY) {
    @Override
    protected Optional<BigDecimal> apply(BigDecimal opr1, BigDecimal opr2) {
      return Optional.of(opr1.multiply(opr2));
    }
  }, DIVIDE("/", BINARY) {
    @Override
    protected Optional<BigDecimal> apply(BigDecimal opr1, BigDecimal opr2) {
      try {
        return Optional.of(opr1.divide(opr2, MATH_CONTEXT));
      } catch (ArithmeticException e) {
        throw new InvalidOperandException(Reason.INVALID_INPUT_VALUE, e);
      }
    }
  }, SQRT("sqrt", UNARY) {
    @Override
    protected Optional<BigDecimal> apply(BigDecimal opr1) {
      try {
        return Optional.of(opr1.sqrt(MATH_CONTEXT));
      } catch (ArithmeticException e) {
        throw new InvalidOperandException(Reason.INVALID_INPUT_VALUE, e);
      }
    }
  }, CLEAR("clear", ZERO_OPERAND) {
    @Override
    protected Optional<BigDecimal> apply() {
      return Optional.empty();
    }
  }, UNDO("undo", ZERO_OPERAND) {
    @Override
    protected Optional<BigDecimal> apply() {
      return Optional.empty();
    }
  };

  private final String symbol;
  @ToString.Exclude
  private final OperatorType type;

  public final Optional<BigDecimal> doOperation(BigDecimal... items) {
    switch (items.length) {
      case 0:
        if (!ZERO_OPERAND.equals(this.type)) {
          throw new IllegalArgumentException("parameter is not supported for " + this);
        }
        return apply();
      case 1:
        if (!UNARY.equals(this.type)) {
          throw new IllegalArgumentException("only one parameter is supported for " + this);
        }
        return apply(items[0]);
      case 2:
        if (!BINARY.equals(this.type)) {
          throw new IllegalArgumentException("only two parameters is supported for " + this);
        }
        return apply(items[0], items[1]);
      default:
        throw new IllegalArgumentException(
            items.length + " parameters is not supported for " + this);
    }
  }

  protected Optional<BigDecimal> apply(BigDecimal opr1, BigDecimal opr2) {
    throw new IllegalStateException("This method is not implemented");
  }

  protected Optional<BigDecimal> apply(BigDecimal opr1) {
    throw new IllegalStateException("This method is not implemented");
  }

  protected Optional<BigDecimal> apply() {
    throw new IllegalStateException("This method is not implemented");
  }

  public static Operator getBySymbol(String symbol) {
    List<Operator> operators = Arrays.stream(Operator.values())
        .filter(item -> item.symbol.equalsIgnoreCase(symbol)).collect(Collectors.toList());
    if (operators.size() != 1) {
      throw new InvalidEntryInputException(symbol);
    }
    return operators.get(0);
  }
}
