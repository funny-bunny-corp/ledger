package com.paymentic.domain.application;

import com.paymentic.domain.JournalEntry;
import com.paymentic.domain.Metadata;
import com.paymentic.domain.PaymentOrder;
import com.paymentic.domain.Shelf;
import com.paymentic.domain.TransactionType;
import com.paymentic.domain.events.JournalEntryRegistered;
import com.paymentic.domain.events.ShelfRegistered;
import com.paymentic.domain.events.TransactionRegistered;
import com.paymentic.domain.ids.JournalEntryId;
import com.paymentic.domain.ids.OwnerId;
import com.paymentic.domain.ids.ShelfId;
import com.paymentic.domain.repositories.ShelfRepository;
import com.paymentic.domain.shared.PaymentOrderId;
import com.paymentic.domain.shared.TransactionId;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Event;
import jakarta.enterprise.event.Observes;
import jakarta.transaction.Transactional;
import java.util.UUID;

@ApplicationScoped
public class ShelfService {
  private final Event<JournalEntryRegistered> trigger;
  private final ShelfRepository shelfRepository;
  private final Event<ShelfRegistered> shelfTrigger;
  public ShelfService(Event<JournalEntryRegistered> trigger,
      ShelfRepository shelfRepository, Event<ShelfRegistered> shelfTrigger) {
    this.trigger = trigger;
    this.shelfRepository = shelfRepository;
    this.shelfTrigger = shelfTrigger;
  }
  @Transactional
  public void register(Shelf shelf){
    this.shelfRepository.persist(shelf);
    this.shelfTrigger.fire(new ShelfRegistered(new ShelfId(shelf.getId())));
  }
  public Shelf get(String id){
    return this.shelfRepository.get(id);
  }
  @Transactional
  public void recordJournal(@Observes TransactionRegistered event){
    var paymentOrder = PaymentOrder.newPaymentOrder(event.getTransaction(),event.getSeller(),new PaymentOrderId(event.getPayment().getId()),event.getCheckout(),event.getAmount(),event.getCurrency());
    Shelf shelf = this.shelfRepository.byOwner(new OwnerId(event.getSeller().getSellerId()));
    var journal = JournalEntry.newJournalEntry(TransactionType.PAYMENT, UUID.randomUUID().toString(),
        Metadata.newMetadataWithPaymentOrder(paymentOrder),shelf);
    shelf.addEntry(journal);
    this.shelfRepository.persist(shelf);
    this.trigger.fire(new JournalEntryRegistered(event.getBuyer(),event.getSeller(),new JournalEntryId(journal.getId().toString()),new ShelfId(shelf.getId()),event.getAmount(),event.getCurrency(),paymentOrder.getPayment(),shelf.version()));
  }

}
