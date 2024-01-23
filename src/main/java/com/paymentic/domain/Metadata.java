
package com.paymentic.domain;

public class Metadata {
  private PaymentOrder paymentOrder;
  private PayoutOrder payoutOrder;
  private Refund refund;
  public Metadata(){}
  private Metadata(PaymentOrder paymentOrder) {
    this.paymentOrder = paymentOrder;
  }
  private Metadata(PayoutOrder payoutOrder) {
    this.payoutOrder = payoutOrder;
  }
  private Metadata(Refund refund) {
    this.refund = refund;
  }
  public static Metadata newMetadataWithPaymentOrder(PaymentOrder paymentOrder){
    return new Metadata(paymentOrder);
  }
  public static Metadata newMetadataWithPayoutOrder(PayoutOrder payoutOrder){
    return new Metadata(payoutOrder);
  }
  public static Metadata newMetadataWithPayoutRefund(Refund refund){
    return new Metadata(refund);
  }
  public PaymentOrder getPaymentOrder() {
    return paymentOrder;
  }
  public PayoutOrder getPayoutOrder() {
    return payoutOrder;
  }

}
