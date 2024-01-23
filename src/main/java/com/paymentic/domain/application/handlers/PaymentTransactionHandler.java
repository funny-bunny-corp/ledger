package com.paymentic.domain.application.handlers;

import com.paymentic.domain.JournalEntry;
import com.paymentic.domain.Metadata;
import com.paymentic.domain.Shelf;
import com.paymentic.domain.TransactionType;
import com.paymentic.domain.events.InFlightTransaction;
import com.paymentic.domain.events.PaymentJournalEntryRegistered;
import com.paymentic.domain.ids.JournalEntryId;
import com.paymentic.domain.ids.ShelfId;
import com.paymentic.domain.repositories.ShelfRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Event;
import java.util.UUID;

@ApplicationScoped
public class PaymentTransactionHandler implements TransactionHandler{
  private final Event<PaymentJournalEntryRegistered> paymentTrigger;
  private final ShelfRepository shelfRepository;
  public PaymentTransactionHandler(Event<PaymentJournalEntryRegistered> paymentTrigger,
      ShelfRepository shelfRepository) {
    this.paymentTrigger = paymentTrigger;
    this.shelfRepository = shelfRepository;
  }
  @Override
  public void handle(InFlightTransaction event, Shelf shelf) {
    var paymentEntry = JournalEntry.newJournalEntry(TransactionType.PAYMENT, UUID.randomUUID().toString(),
        Metadata.newMetadataWithPaymentOrder(event.getPayment()),shelf);
    shelf.addEntry(paymentEntry);
    this.shelfRepository.save(shelf);
    this.paymentTrigger.fire(new PaymentJournalEntryRegistered(event.getPayment().getBuyer(),event.getSeller(),new JournalEntryId(paymentEntry.getId().toString()),new ShelfId(shelf.getId()),event.getPayment().getAmount(),event.getPayment().getCurrency(),event.getPayment()
        .getPayment(),shelf.version()));
  }
}
