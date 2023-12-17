package com.paymentic.domain.application;

import com.paymentic.domain.Book;
import com.paymentic.domain.BookEntry;
import com.paymentic.domain.events.JournalEntryRegistered;
import com.paymentic.domain.events.PaymentBookEntryRegistered;
import com.paymentic.domain.events.ShelfRegistered;
import com.paymentic.domain.ids.ShelfId;
import com.paymentic.domain.repositories.BookRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Event;
import jakarta.enterprise.event.Observes;
import jakarta.transaction.Transactional;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@ApplicationScoped
public class BookService {
  private final BookRepository bookRepository;
  private final Event<PaymentBookEntryRegistered> trigger;
  public BookService(BookRepository bookRepository, Event<PaymentBookEntryRegistered> trigger) {
    this.bookRepository = bookRepository;
    this.trigger = trigger;
  }
  public Book get(UUID id){
    return this.bookRepository.findById(id);
  }
  public List<Book> byShelf(ShelfId shelfId){
    return this.bookRepository.booksByShelf(shelfId);
  }
  @Transactional
  public void recordBookEntries(@Observes JournalEntryRegistered journalEntryRegistered){
    var books = this.bookRepository.findBooksForPayment(journalEntryRegistered.shelfId());
    books.addPayment(BookEntry.paymentEntry(journalEntryRegistered.journalEntryId(), BigDecimal.valueOf(Double.parseDouble(journalEntryRegistered.amount())),
        journalEntryRegistered.currency(),books.payment()));
    books.addPending(BookEntry.pendingEntry(journalEntryRegistered.journalEntryId(), BigDecimal.valueOf(Double.parseDouble(journalEntryRegistered.amount())),journalEntryRegistered.currency(),books.pending()));
    this.bookRepository.persist(books.payment());
    this.bookRepository.persist(books.pending());
    this.trigger.fire(new PaymentBookEntryRegistered(journalEntryRegistered.journalEntryId(),journalEntryRegistered.paymentOrderId()));
  }
  @Transactional
  public void registerBooks(@Observes ShelfRegistered shelfRegistered){
    this.bookRepository.persist(
        Book.newPendingBook(UUID.randomUUID().toString(),new ShelfId(shelfRegistered.shelfId().getId())),
        Book.newPaymentBook(UUID.randomUUID().toString(),new ShelfId(shelfRegistered.shelfId().getId())),
        Book.newPayoutBook(UUID.randomUUID().toString(),new ShelfId(shelfRegistered.shelfId().getId())),
        Book.newRefundBook(UUID.randomUUID().toString(),new ShelfId(shelfRegistered.shelfId().getId()))
    );
  }

}
