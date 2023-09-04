package com.paymentic.adapter.kafka;

import com.paymentic.domain.events.PaymentBookEntryRegistered;
import com.paymentic.domain.events.TransactionBooked;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;

@ApplicationScoped
public class BookRegisteredProcessor {
  private final Emitter<TransactionBooked> emitter;
  public BookRegisteredProcessor(@Channel("transaction-booked")Emitter<TransactionBooked> emitter) {
    this.emitter = emitter;
  }
  public void notifyBooking(@Observes PaymentBookEntryRegistered bookEntryRegistered) {
    this.emitter.send(new TransactionBooked(bookEntryRegistered.paymentOrderId()));
  }

}
