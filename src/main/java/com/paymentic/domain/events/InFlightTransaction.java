package com.paymentic.domain.events;

import com.paymentic.domain.PaymentOrder;
import com.paymentic.domain.PayoutOrder;
import com.paymentic.domain.TransactionType;
import com.paymentic.domain.shared.SellerInfo;
import com.paymentic.domain.shared.TransactionId;

public class InFlightTransaction {
  private final TransactionId id;
  private final SellerInfo seller;
  private final TransactionType type;
  private PayoutOrder payout;
  private PaymentOrder payment;
  public InFlightTransaction(TransactionId id, SellerInfo seller, TransactionType type) {
    this.id = id;
    this.seller = seller;
    this.type = type;
  }
  public static InFlightTransaction createWithPayment(TransactionId id, SellerInfo seller, TransactionType type, PaymentOrder payment) {
    InFlightTransaction transaction = new InFlightTransaction(id, seller, type);
    transaction.payment = payment;
    return transaction;
  }
  public static InFlightTransaction createWithPayout(TransactionId id, SellerInfo seller, TransactionType type, PayoutOrder payout) {
    InFlightTransaction transaction = new InFlightTransaction(id, seller, type);
    transaction.payout = payout;
    return transaction;
  }
  public TransactionId getId() {
    return id;
  }
  public SellerInfo getSeller() {
    return seller;
  }
  public TransactionType getType() {
    return type;
  }
  public PayoutOrder getPayout() {
    return payout;
  }
  public PaymentOrder getPayment() {
    return payment;
  }
}
