package com.paymentic.domain;

import com.paymentic.domain.shared.CheckoutId;
import com.paymentic.domain.shared.PaymentOrderId;
import com.paymentic.domain.shared.SellerInfo;
import com.paymentic.domain.shared.TransactionId;
import jakarta.persistence.Embeddable;

@Embeddable
public class PaymentOrder {
  private TransactionId transaction;
  private SellerInfo seller;
  private PaymentOrderId payment;
  private CheckoutId checkoutId;
  private String amount;
  private String currency;

  public PaymentOrder(){}

  public TransactionId getTransaction() {
    return transaction;
  }

  public SellerInfo getSeller() {
    return seller;
  }

  public PaymentOrderId getPayment() {
    return payment;
  }

  public CheckoutId getCheckoutId() {
    return checkoutId;
  }

  public String getAmount() {
    return amount;
  }

  public String getCurrency() {
    return currency;
  }
}
