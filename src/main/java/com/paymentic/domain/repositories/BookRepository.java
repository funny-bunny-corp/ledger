package com.paymentic.domain.repositories;

import com.paymentic.domain.Book;
import com.paymentic.domain.ids.BookId;
import com.paymentic.domain.ids.ShelfId;
import com.paymentic.domain.repositories.data.PaymentBooks;
import com.paymentic.domain.repositories.data.PayoutBooks;
import java.util.List;
import java.util.UUID;

public interface BookRepository {
  Book findById(UUID id);
  PaymentBooks findBooksForPayment(ShelfId shelfId);
  PayoutBooks findBooksForPayout(ShelfId shelfId);
  List<Book> booksByShelf(ShelfId shelfId);
  Book bookByShelfAndId(ShelfId shelfId, BookId bookId);
  void save(Book book);
  void save(Book ...books);

}
