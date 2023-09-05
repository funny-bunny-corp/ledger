package com.paymentic.adapter.kafka;

import com.paymentic.domain.events.PaymentBookEntryRegistered;
import com.paymentic.domain.events.TransactionBooked;
import io.smallrye.reactive.messaging.ce.OutgoingCloudEventMetadata;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;
import jakarta.enterprise.event.TransactionPhase;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;
import org.eclipse.microprofile.reactive.messaging.Message;

@ApplicationScoped
public class BookRegisteredProcessor {
  private final Emitter<TransactionBooked> emitter;
  public BookRegisteredProcessor(@Channel("transaction-booked")Emitter<TransactionBooked> emitter) {
    this.emitter = emitter;
  }
  public void notifyBooking(@Observes(during = TransactionPhase.AFTER_COMPLETION) PaymentBookEntryRegistered bookEntryRegistered) {
    this.emitter.send(Message.of(new TransactionBooked(bookEntryRegistered.paymentOrderId())).addMetadata(OutgoingCloudEventMetadata.builder().build()));
  }

}
