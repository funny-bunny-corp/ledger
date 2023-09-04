package com.paymentic.domain.repositories.data;

import com.paymentic.domain.Book;
import com.paymentic.domain.BookEntry;

public record PaymentBooks(Book payment,Book pending) {

  public boolean addPayment(BookEntry payment){
    return this.payment.addEntry(payment);
  }
  public boolean addPending(BookEntry pending){
    return this.pending.addEntry(pending);
  }

}
