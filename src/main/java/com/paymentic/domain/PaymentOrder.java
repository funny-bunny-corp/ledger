package com.paymentic.domain;

import com.paymentic.domain.shared.BuyerInfo;
import com.paymentic.domain.shared.CheckoutId;
import com.paymentic.domain.shared.PaymentOrderId;
import com.paymentic.domain.shared.SellerInfo;
import com.paymentic.domain.shared.TransactionId;

public class PaymentOrder {
  private TransactionId transaction;
  private SellerInfo seller;
  private PaymentOrderId payment;
  private CheckoutId checkoutId;
  private String amount;
  private String currency;
  private BuyerInfo buyer;
  public PaymentOrder(){}
  public PaymentOrder(TransactionId transaction, SellerInfo seller, PaymentOrderId payment,
      CheckoutId checkoutId, String amount, String currency,BuyerInfo buyer) {
    this.transaction = transaction;
    this.seller = seller;
    this.payment = payment;
    this.checkoutId = checkoutId;
    this.amount = amount;
    this.currency = currency;
    this.buyer = buyer;
  }
  public static PaymentOrder newPaymentOrder(TransactionId transaction, SellerInfo seller, PaymentOrderId payment,
      CheckoutId checkoutId, String amount, String currency,BuyerInfo buyer){
    return new PaymentOrder(transaction,seller,payment,checkoutId,amount,currency,buyer);
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
  public CheckoutId getCheckoutId() {
    return checkoutId;
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
