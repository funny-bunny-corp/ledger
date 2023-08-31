package com.paymentic.domain.application;

import com.paymentic.domain.BookId;
import com.paymentic.domain.JournalEntry;
import com.paymentic.domain.JournalEntryId;
import com.paymentic.domain.OwnerId;
import com.paymentic.domain.PaymentOrder;
import com.paymentic.domain.TransactionType;
import com.paymentic.domain.events.JournalEntryRegistered;
import com.paymentic.domain.events.TransactionRegistered;
import com.paymentic.domain.repositories.BookRepository;
import com.paymentic.domain.repositories.JournalEntryRepository;
import com.paymentic.domain.shared.PaymentOrderId;
import com.paymentic.domain.shared.TransactionId;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Event;
import jakarta.enterprise.event.Observes;
import jakarta.transaction.Transactional;
import java.util.UUID;

@ApplicationScoped
public class JournalEntryService {
  private final BookRepository bookRepository;
  private final JournalEntryRepository journalEntryRepository;
  private final Event<JournalEntryRegistered> trigger;
  public JournalEntryService(BookRepository bookRepository,
      JournalEntryRepository journalEntryRepository,
      Event<JournalEntryRegistered> trigger) {
    this.bookRepository = bookRepository;
    this.journalEntryRepository = journalEntryRepository;
    this.trigger = trigger;
  }
  @Transactional
  public void listen(@Observes TransactionRegistered event){
    var book = this.bookRepository.findByOwner(new OwnerId(event.getSellerInfo().getSellerId()));
    var paymentOrder = PaymentOrder.newPaymentOrder(new TransactionId(event.getId()),event.getSellerInfo(),new PaymentOrderId(event.getPaymentOrder().getId()),event.getCheckout(),event.getAmount(),event.getCurrency());
    var journal = JournalEntry.newJournalEntry(TransactionType.PAYMENT, UUID.randomUUID().toString(),paymentOrder,new BookId(book.getId().toString()));
    this.journalEntryRepository.persist(journal);
    this.trigger.fire(new JournalEntryRegistered(new BookId(book.getId().toString()),new JournalEntryId(journal.getId().toString()),event.getAmount(),event.getCurrency()));
  }

}
