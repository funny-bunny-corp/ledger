package com.paymentic.domain.application.handlers;

import com.paymentic.domain.JournalEntry;
import com.paymentic.domain.Metadata;
import com.paymentic.domain.Shelf;
import com.paymentic.domain.TransactionType;
import com.paymentic.domain.events.InFlightTransaction;
import com.paymentic.domain.events.PayoutJournalEntryRegistered;
import com.paymentic.domain.ids.JournalEntryId;
import com.paymentic.domain.ids.ShelfId;
import com.paymentic.domain.repositories.ShelfRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Event;
import java.util.UUID;

@ApplicationScoped
public class PayoutTransactionHandler implements TransactionHandler{
  private final Event<PayoutJournalEntryRegistered> payoutTrigger;
  private final ShelfRepository shelfRepository;
  public PayoutTransactionHandler(Event<PayoutJournalEntryRegistered> payoutTrigger,
      ShelfRepository shelfRepository) {
    this.payoutTrigger = payoutTrigger;
    this.shelfRepository = shelfRepository;
  }
  @Override
  public void handle(InFlightTransaction event, Shelf shelf) {
    var payoutEntry = JournalEntry.newJournalEntry(TransactionType.PAYOUT, UUID.randomUUID().toString(),
        Metadata.newMetadataWithPayoutOrder(event.getPayout()),shelf);
    shelf.addEntry(payoutEntry);
    this.shelfRepository.save(shelf);
    this.payoutTrigger.fire(new PayoutJournalEntryRegistered(event.getSeller(),new JournalEntryId(payoutEntry.getId().toString()),new ShelfId(shelf.getId()),event.getPayout().getAmount(),event.getPayout().getCurrency(),shelf.version()));
  }
}
