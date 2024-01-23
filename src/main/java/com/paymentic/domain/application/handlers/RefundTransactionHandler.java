package com.paymentic.domain.application.handlers;

import com.paymentic.domain.JournalEntry;
import com.paymentic.domain.Metadata;
import com.paymentic.domain.Shelf;
import com.paymentic.domain.TransactionType;
import com.paymentic.domain.events.InFlightTransaction;
import com.paymentic.domain.events.RefundJournalEntryRegistered;
import com.paymentic.domain.ids.JournalEntryId;
import com.paymentic.domain.ids.ShelfId;
import com.paymentic.domain.repositories.ShelfRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Event;
import java.util.UUID;

@ApplicationScoped
public class RefundTransactionHandler implements TransactionHandler{
  private final Event<RefundJournalEntryRegistered> refundTrigger;
  private final ShelfRepository shelfRepository;
  public RefundTransactionHandler(Event<RefundJournalEntryRegistered> refundTrigger,
      ShelfRepository shelfRepository) {
    this.refundTrigger = refundTrigger;
    this.shelfRepository = shelfRepository;
  }
  @Override
  public void handle(InFlightTransaction event, Shelf shelf) {
    var refundEntry = JournalEntry.newJournalEntry(TransactionType.REFUND, UUID.randomUUID().toString(),
        Metadata.newMetadataWithPayoutRefund(event.getRefund()),shelf);
    shelf.addEntry(refundEntry);
    this.shelfRepository.save(shelf);
    this.refundTrigger.fire(new RefundJournalEntryRegistered(event.getSeller(),new JournalEntryId(refundEntry.getId().toString()),new ShelfId(shelf.getId()),event.getPayout().getAmount(),event.getPayout().getCurrency(),shelf.version()));
  }
}
