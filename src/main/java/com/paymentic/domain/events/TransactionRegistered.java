package com.paymentic.domain.events;

import com.paymentic.domain.shared.BuyerInfo;
import com.paymentic.domain.shared.CheckoutId;
import com.paymentic.domain.shared.PaymentOrderId;
import com.paymentic.domain.shared.SellerInfo;
import com.paymentic.domain.shared.TransactionId;
import java.util.UUID;

public class TransactionRegistered {
  private TransactionId transaction;
  private String amount;
  private String currency;
  private CheckoutId checkout;
  private BuyerInfo buyer;
  private PaymentOrderId payment;
  private SellerInfo seller;
  public TransactionRegistered(){}
  public TransactionRegistered(TransactionId id, String amount, String currency, CheckoutId checkout, BuyerInfo buyer,
      PaymentOrderId paymentOrder,SellerInfo sellerInfo) {
    this.transaction = id;
    this.amount = amount;
    this.currency = currency;
    this.checkout = checkout;
    this.buyer = buyer;
    this.payment = paymentOrder;
    this.seller = sellerInfo;
  }
  public static TransactionRegistered newTransactionRegistered(TransactionId id, String amount, String currency, CheckoutId checkout, BuyerInfo buyer,
      PaymentOrderId paymentOrder,SellerInfo sellerInfo){
    return new TransactionRegistered(id,amount,currency,checkout,buyer,paymentOrder,sellerInfo);
  }
  public String getAmount() {
    return amount;
  }
  public String getCurrency() {
    return currency;
  }
  public CheckoutId getCheckout() {
    return checkout;
  }
  public BuyerInfo getBuyer() {
    return buyer;
  }
  public PaymentOrderId getPayment() {
    return payment;
  }
  public SellerInfo getSeller() {
    return seller;
  }
  public TransactionId getTransaction() {
    return transaction;
  }
}
