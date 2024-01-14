package com.paymentic.domain.application;

import com.paymentic.domain.JournalEntry;
import com.paymentic.domain.Metadata;
import com.paymentic.domain.Shelf;
import com.paymentic.domain.TransactionType;
import com.paymentic.domain.events.InFlightTransaction;
import com.paymentic.domain.events.JournalEntryUnregistered;
import com.paymentic.domain.events.PaymentJournalEntryRegistered;
import com.paymentic.domain.events.PayoutJournalEntryRegistered;
import com.paymentic.domain.events.ShelfRegistered;
import com.paymentic.domain.ids.JournalEntryId;
import com.paymentic.domain.ids.OwnerId;
import com.paymentic.domain.ids.ShelfId;
import com.paymentic.domain.repositories.ShelfRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Event;
import jakarta.enterprise.event.Observes;
import jakarta.transaction.Transactional;
import java.util.Objects;
import java.util.UUID;

@ApplicationScoped
public class ShelfService {
  private final Event<PaymentJournalEntryRegistered> paymentTrigger;

  private final Event<PayoutJournalEntryRegistered> payoutTrigger;
  private final ShelfRepository shelfRepository;
  private final Event<ShelfRegistered> shelfTrigger;
  private final Event<JournalEntryUnregistered> journalUnregistered;
  public ShelfService(Event<PaymentJournalEntryRegistered> paymentTrigger,
      ShelfRepository shelfRepository, Event<ShelfRegistered> shelfTrigger,
      Event<JournalEntryUnregistered> journalUnregistered,
      Event<PayoutJournalEntryRegistered> payoutTrigger) {
    this.paymentTrigger = paymentTrigger;
    this.shelfRepository = shelfRepository;
    this.shelfTrigger = shelfTrigger;
    this.journalUnregistered = journalUnregistered;
    this.payoutTrigger = payoutTrigger;
  }
  @Transactional
  public void register(Shelf shelf){
    this.shelfRepository.save(shelf);
    this.shelfTrigger.fire(new ShelfRegistered(new ShelfId(shelf.getId())));
  }
  public Shelf get(String id){
    return this.shelfRepository.get(id);
  }
  @Transactional
  public void recordJournal(@Observes InFlightTransaction event){
    Shelf shelf = this.shelfRepository.byOwner(new OwnerId(event.getSeller().getSellerId()));
    if (Objects.isNull(shelf)){
      this.journalUnregistered.fire(new JournalEntryUnregistered(event.getPayment().getTransaction(),event.getPayment().getAmount(),event.getPayment().getCurrency(),event.getPayment().getCheckoutId(),
          event.getPayment().getBuyer(),event.getPayment().getPayment(),event.getSeller()));
    }else{
      switch (event.getType()){
        case PAYMENT -> {
          var paymentEntry = JournalEntry.newJournalEntry(TransactionType.PAYMENT, UUID.randomUUID().toString(),
              Metadata.newMetadataWithPaymentOrder(event.getPayment()),shelf);
          shelf.addEntry(paymentEntry);
          this.shelfRepository.save(shelf);
          this.paymentTrigger.fire(new PaymentJournalEntryRegistered(event.getPayment().getBuyer(),event.getSeller(),new JournalEntryId(paymentEntry.getId().toString()),new ShelfId(shelf.getId()),event.getPayment().getAmount(),event.getPayment().getCurrency(),event.getPayment()
              .getPayment(),shelf.version()));
        }case PAYOUT -> {
          var payoutEntry = JournalEntry.newJournalEntry(TransactionType.PAYOUT, UUID.randomUUID().toString(),
              Metadata.newMetadataWithPayoutOrder(event.getPayout()),shelf);
          shelf.addEntry(payoutEntry);
          this.shelfRepository.save(shelf);
          this.payoutTrigger.fire(new PayoutJournalEntryRegistered(event.getSeller(),new JournalEntryId(payoutEntry.getId().toString()),new ShelfId(shelf.getId()),event.getPayout().getAmount(),event.getPayout().getCurrency(),shelf.version()));
        }
      }
    }
  }

}
