package com.paymentic.domain.application;

import static java.util.function.Function.*;
import static java.util.stream.Collectors.toMap;

import com.paymentic.domain.JournalEntry;
import com.paymentic.domain.Metadata;
import com.paymentic.domain.Shelf;
import com.paymentic.domain.TransactionType;
import com.paymentic.domain.application.handlers.PaymentTransactionHandler;
import com.paymentic.domain.application.handlers.RefundTransactionHandler;
import com.paymentic.domain.application.handlers.TransactionHandler;
import com.paymentic.domain.events.InFlightTransaction;
import com.paymentic.domain.events.JournalEntryUnregistered;
import com.paymentic.domain.events.PaymentJournalEntryRegistered;
import com.paymentic.domain.events.PayoutJournalEntryRegistered;
import com.paymentic.domain.events.RefundJournalEntryRegistered;
import com.paymentic.domain.events.ShelfRegistered;
import com.paymentic.domain.ids.JournalEntryId;
import com.paymentic.domain.ids.OwnerId;
import com.paymentic.domain.ids.ShelfId;
import com.paymentic.domain.repositories.ShelfRepository;
import io.quarkus.arc.All;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Event;
import jakarta.enterprise.event.Observes;
import jakarta.enterprise.inject.Instance;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.function.Function;

@ApplicationScoped
public class ShelfService {
  private final ShelfRepository shelfRepository;
  private final Event<ShelfRegistered> shelfTrigger;
  private final Event<JournalEntryUnregistered> journalUnregistered;
  private final PaymentTransactionHandler paymentTransactionHandler;
  private final RefundTransactionHandler refundTransactionHandler;
  private final PaymentTransactionHandler payoutTransactionHandler;
  Map<TransactionType, TransactionHandler> handlers;
  public ShelfService(
      ShelfRepository shelfRepository, Event<ShelfRegistered> shelfTrigger,
      Event<JournalEntryUnregistered> journalUnregistered,
      PaymentTransactionHandler paymentTransactionHandler,
      RefundTransactionHandler refundTransactionHandler,
      PaymentTransactionHandler payoutTransactionHandler) {
    this.shelfRepository = shelfRepository;
    this.shelfTrigger = shelfTrigger;
    this.journalUnregistered = journalUnregistered;
    this.paymentTransactionHandler = paymentTransactionHandler;
    this.refundTransactionHandler = refundTransactionHandler;
    this.payoutTransactionHandler = payoutTransactionHandler;
  }
  @PostConstruct
  void setup(){
    this.handlers = Map.of(TransactionType.PAYMENT,this.paymentTransactionHandler,
        TransactionType.REFUND,this.refundTransactionHandler,
        TransactionType.PAYOUT,this.payoutTransactionHandler);
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
      this.handlers.get(event.getType()).handle(event,shelf);
    }
  }

}
