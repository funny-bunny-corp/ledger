package com.paymentic.adapter.http;

import com.paymentic.domain.OwnerId;
import com.paymentic.domain.application.BookService;
import jakarta.ws.rs.Path;
import java.util.UUID;
import org.openapitools.api.BooksApi;
import org.openapitools.model.Book;
import org.openapitools.model.BookCreated;
import org.openapitools.model.RequestBookCreation;

@Path("/books")
public class BookResource implements BooksApi {
  private final BookService bookService;
  public BookResource(BookService bookService) {
    this.bookService = bookService;
  }
  @Override
  public BookCreated createBook(RequestBookCreation requestBookCreation) {
    var newBook = com.paymentic.domain.Book.newBook(new OwnerId(requestBookCreation.getOwner().getId()));
    var bookCreated = this.bookService.register(newBook);
    return new BookCreated().id(bookCreated.getId().toString());
  }
  @Override
  public Book getBook(String id) {
    var book = this.bookService.get(UUID.fromString(id));

    return null;
  }

}
