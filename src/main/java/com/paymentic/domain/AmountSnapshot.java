package com.paymentic.domain;

import jakarta.persistence.Embeddable;
import java.math.BigDecimal;

@Embeddable
public class AmountSnapshot {
  private BigDecimal fromAmount;
  private BigDecimal toAmount;
  public AmountSnapshot() {
  }
  public AmountSnapshot(BigDecimal fromAmount, BigDecimal toAmount) {
    this.fromAmount = fromAmount;
    this.toAmount = toAmount;
  }
  public static AmountSnapshot zero(){
    return new AmountSnapshot(BigDecimal.ZERO,BigDecimal.ZERO);
  }
  public BigDecimal getFromAmount() {
    return fromAmount;
  }
  public BigDecimal getToAmount() {
    return toAmount;
  }
}
