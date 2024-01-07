package com.paymentic.adapter.http;

import com.paymentic.adapter.http.apis.api.ShelvesApi;
import com.paymentic.adapter.http.apis.model.BooksInner;
import com.paymentic.adapter.http.apis.model.RecordsInner;
import com.paymentic.adapter.http.apis.model.RequestShelfCreation;
import com.paymentic.adapter.http.apis.model.Shelf;
import com.paymentic.adapter.http.apis.model.ShelfCreated;
import com.paymentic.adapter.http.apis.model.ShelfOwner;
import com.paymentic.domain.Book;
import com.paymentic.domain.application.BookService;
import com.paymentic.domain.application.ShelfService;
import com.paymentic.domain.ids.BookId;
import com.paymentic.domain.ids.OwnerId;
import com.paymentic.domain.ids.ShelfId;
import io.smallrye.common.annotation.RunOnVirtualThread;
import jakarta.enterprise.context.ApplicationScoped;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@ApplicationScoped
@RunOnVirtualThread
public class ShelvesResource implements ShelvesApi {
  private final ShelfService shelfService;
  private final BookService bookService;

  public ShelvesResource(ShelfService shelfService, BookService bookService) {
    this.shelfService = shelfService;
    this.bookService = bookService;
  }
  @Override
  public ShelfCreated createShelf(RequestShelfCreation requestShelfCreation) {
    var shelf = com.paymentic.domain.Shelf.newShelf(UUID.randomUUID().toString(),new OwnerId(requestShelfCreation.getOwner().getId()));
    this.shelfService.register(shelf);
    return new ShelfCreated().id(shelf.getId().toString());
  }

  @Override
  public List<BooksInner> getBooks(String id) {
    return this.bookService.byShelf(new ShelfId(UUID.fromString(id))).stream()
        .map(bk -> new BooksInner().id(bk.getId().toString()).type(bk.getType().toString()).version(bk.getVersion())).collect(
        Collectors.toList());
  }

  @Override
  public List<RecordsInner> getRecords(String id, String bookId) {
    var book = this.bookService.bookByShelfAndId(new ShelfId(UUID.fromString(id)),
        new BookId(bookId));
    return book.getEntries().stream().map(entry -> new RecordsInner()
        .id(entry.getId().toString())
        .at(entry.getAt().toLocalDate())
        .amount(entry.getAmount().doubleValue())
        .type(entry.getOperationType().toString())
        .currency(entry.getCurrency())).collect(Collectors.toList());
  }

  @Override
  public Shelf getShelf(String id) {
    var data = this.shelfService.get(id);
    return new Shelf().id(data.getId().toString()).owner(new ShelfOwner().id(data.getOwner().getId()));
  }

}
