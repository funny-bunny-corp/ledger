package com.paymentic.domain.events;



import com.paymentic.domain.shared.BuyerInfo;
import com.paymentic.domain.shared.CheckoutId;
import com.paymentic.domain.shared.PaymentOrderId;
import java.util.UUID;

public class TransactionRegistered {
  private UUID id;
  private String amount;
  private String currency;
  private CheckoutId checkout;
  private BuyerInfo buyer;
  private PaymentOrderId paymentOrder;
  public TransactionRegistered(){}
  public TransactionRegistered(UUID id, String amount, String currency, CheckoutId checkout, BuyerInfo buyer,
      PaymentOrderId paymentOrder) {
    this.id = id;
    this.amount = amount;
    this.currency = currency;
    this.checkout = checkout;
    this.buyer = buyer;
    this.paymentOrder = paymentOrder;
  }
  public static TransactionRegistered newTransactionRegistered(UUID id, String amount, String currency, CheckoutId checkout, BuyerInfo buyer,
      PaymentOrderId paymentOrder){
    return new TransactionRegistered(id,amount,currency,checkout,buyer,paymentOrder);
  }
  public UUID getId() {
    return id;
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
  public PaymentOrderId getPaymentOrder() {
    return paymentOrder;
  }

}
