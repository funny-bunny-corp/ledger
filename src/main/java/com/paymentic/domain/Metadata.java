package com.paymentic.domain;

public class Metadata {
  private PaymentOrder paymentOrder;
  private PayoutOrder payoutOrder;
  public Metadata(){}
  private Metadata(PaymentOrder paymentOrder) {
    this.paymentOrder = paymentOrder;
  }
  private Metadata(PayoutOrder payoutOrder) {
    this.payoutOrder = payoutOrder;
  }
  public static Metadata newMetadataWithPaymentOrder(PaymentOrder paymentOrder){
    return new Metadata(paymentOrder);
  }
  public static Metadata newMetadataWithPayoutOrder(PayoutOrder payoutOrder){
    return new Metadata(payoutOrder);
  }
  public PaymentOrder getPaymentOrder() {
    return paymentOrder;
  }
  public PayoutOrder getPayoutOrder() {
    return payoutOrder;
  }

}
