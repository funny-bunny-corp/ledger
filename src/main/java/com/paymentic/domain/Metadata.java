package com.paymentic.domain;

public class Metadata {
  private PaymentOrder paymentOrder;
  public Metadata(){}
  private Metadata(PaymentOrder paymentOrder) {
    this.paymentOrder = paymentOrder;
  }
  public static Metadata newMetadataWithPaymentOrder(PaymentOrder paymentOrder){
    return new Metadata(paymentOrder);
  }
  public PaymentOrder getPaymentOrder() {
    return paymentOrder;
  }
}
