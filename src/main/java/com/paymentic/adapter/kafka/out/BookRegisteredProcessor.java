package com.paymentic.adapter.kafka.out;

import com.paymentic.domain.events.PaymentBookEntryRegistered;
import com.paymentic.domain.events.TransactionBooked;
import com.paymentic.infra.ce.CExtensions.Audience;
import com.paymentic.infra.ce.CExtensions.EventContext;
import com.paymentic.infra.ce.ExtensionsBuilder;
import io.smallrye.reactive.messaging.ce.OutgoingCloudEventMetadata;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;
import jakarta.enterprise.event.TransactionPhase;
import java.time.LocalDateTime;
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
    var metadata = OutgoingCloudEventMetadata.builder()
        .withExtensions(new ExtensionsBuilder().audience(Audience.EXTERNAL_BOUNDED_CONTEXT).eventContext(
            EventContext.DOMAIN).build())
        .build();
    this.emitter.send(Message.of(new TransactionBooked(bookEntryRegistered.paymentOrderId(),
        LocalDateTime.now())).addMetadata(metadata));
  }

}
