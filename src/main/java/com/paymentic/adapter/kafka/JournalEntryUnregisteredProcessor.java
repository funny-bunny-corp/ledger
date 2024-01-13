package com.paymentic.adapter.kafka;

import com.paymentic.domain.events.JournalEntryUnregistered;
import com.paymentic.domain.events.PaymentBookEntryRegistered;
import com.paymentic.domain.events.PaymentBookFailed;
import com.paymentic.domain.events.TransactionBooked;
import io.smallrye.reactive.messaging.ce.OutgoingCloudEventMetadata;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;
import jakarta.enterprise.event.TransactionPhase;
import java.time.LocalDateTime;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;
import org.eclipse.microprofile.reactive.messaging.Message;

@ApplicationScoped
public class JournalEntryUnregisteredProcessor {
  private final Emitter<PaymentBookFailed> emitter;

  public JournalEntryUnregisteredProcessor(@Channel("transaction-book-failed")Emitter<PaymentBookFailed> emitter) {
    this.emitter = emitter;
  }
  public void notifyUnregistered(@Observes(during = TransactionPhase.AFTER_COMPLETION) JournalEntryUnregistered journalEntryUnregistered) {
    var failedBook = new PaymentBookFailed(journalEntryUnregistered.transaction(),journalEntryUnregistered.amount(),journalEntryUnregistered.currency(),journalEntryUnregistered.checkout(),journalEntryUnregistered.buyer(),
        journalEntryUnregistered.payment(),journalEntryUnregistered.seller(), LocalDateTime.now(),"Onboarding not ready to process bookings");
    this.emitter.send(failedBook);
  }

}
