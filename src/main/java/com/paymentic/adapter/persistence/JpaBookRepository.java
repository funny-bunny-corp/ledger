package com.paymentic.adapter.persistence;

import com.paymentic.domain.Book;
import com.paymentic.domain.BookType;
import com.paymentic.domain.ids.BookId;
import com.paymentic.domain.ids.ShelfId;
import com.paymentic.domain.repositories.BookRepository;
import com.paymentic.domain.repositories.data.PaymentBooks;
import com.paymentic.domain.repositories.data.PayoutBooks;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import io.quarkus.panache.common.Parameters;
import jakarta.enterprise.context.ApplicationScoped;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@ApplicationScoped
public class JpaBookRepository implements PanacheRepository<Book>,
    BookRepository {
  public Book findById(UUID id){
    return find("id", Parameters.with("id", id)).firstResult();
  }
  public PaymentBooks findBooksForPayment(ShelfId shelfId){
    var paymentBook = this.findByShelfIdAndType(shelfId,BookType.PAYMENTS);
    var pendingBook = this.findByShelfIdAndType(shelfId,BookType.PENDING_BALANCE);
    return new PaymentBooks(paymentBook,pendingBook);
  }
  public PayoutBooks findBooksForPayout(ShelfId shelfId){
    var payoutBook = this.findByShelfIdAndType(shelfId,BookType.PAYOUT);
    var pendingBook = this.findByShelfIdAndType(shelfId,BookType.PENDING_BALANCE);
    return new PayoutBooks(payoutBook,pendingBook);
  }
  public List<Book> booksByShelf(ShelfId shelfId){
    return find("shelf.id = :shelfId",Parameters.with("shelfId", shelfId.getId())).stream().toList();
  }
  private Book findByShelfIdAndType(ShelfId shelfId, BookType type){
    return find("shelf.id = :shelfId and type = :type",Parameters.with("shelfId", shelfId.getId()).and("type",type)).firstResult();
  }

  public Book bookByShelfAndId(ShelfId shelfId, BookId bookId){
    return find("shelf.id = :shelfId and id = :id",Parameters.with("shelfId", shelfId.getId()).and("id",UUID.fromString(bookId.getId()))).firstResult();
  }
  @Override
  public void save(Book book) {
    persist(book);
  }
  @Override
  public void save(Book... books) {
    persist(Arrays.stream(books).toList());
  }

}
