package com.paymentic.domain;

import com.paymentic.domain.shared.BuyerInfo;
import com.paymentic.domain.shared.PaymentOrderId;
import com.paymentic.domain.shared.SellerInfo;
import com.paymentic.domain.shared.TransactionId;

public class Refund {
  private TransactionId transaction;
  private SellerInfo seller;
  private PaymentOrderId payment;
  private String amount;
  private String currency;
  private BuyerInfo buyer;
  public Refund(){}
  public Refund(TransactionId transaction, SellerInfo seller, PaymentOrderId payment,
      String amount, String currency,BuyerInfo buyer) {
    this.transaction = transaction;
    this.seller = seller;
    this.payment = payment;
    this.amount = amount;
    this.currency = currency;
    this.buyer = buyer;
  }
  public static Refund newRefund(TransactionId transaction, SellerInfo seller, PaymentOrderId payment,
       String amount, String currency,BuyerInfo buyer){
    return new Refund(transaction,seller,payment,amount,currency,buyer);
  }
  public TransactionId getTransaction() {
    return transaction;
  }
  public SellerInfo getSeller() {
    return seller;
  }
  public PaymentOrderId getPayment() {
    return payment;
  }
  public String getAmount() {
    return amount;
  }
  public String getCurrency() {
    return currency;
  }
  public BuyerInfo getBuyer() {
    return buyer;
  }

}
