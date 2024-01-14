package com.paymentic.domain;

import com.paymentic.domain.shared.SellerInfo;
import com.paymentic.domain.shared.TransactionId;

public class PayoutOrder {
  private TransactionId transaction;
  private SellerInfo seller;
  private String amount;
  private String currency;
  public PayoutOrder(){}
  public PayoutOrder(TransactionId transaction, SellerInfo seller,  String amount, String currency) {
    this.transaction = transaction;
    this.seller = seller;
    this.amount = amount;
    this.currency = currency;
  }
  public static PayoutOrder newPayoutOrder(TransactionId transaction, SellerInfo seller, String amount, String currency){
    return new PayoutOrder(transaction,seller,amount,currency);
  }
  public TransactionId getTransaction() {
    return transaction;
  }
  public SellerInfo getSeller() {
    return seller;
  }
  public String getAmount() {
    return amount;
  }
  public String getCurrency() {
    return currency;
  }
}
