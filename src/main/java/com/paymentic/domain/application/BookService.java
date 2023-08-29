package com.paymentic.domain.application;

import com.paymentic.domain.Book;
import com.paymentic.domain.repositories.BookRepository;
import jakarta.enterprise.context.ApplicationScoped;
import java.util.UUID;

@ApplicationScoped
public class BookService {
  private final BookRepository bookRepository;
  public BookService(BookRepository bookRepository) {
    this.bookRepository = bookRepository;
  }
  public Book register(Book book){
    return null;
  }
  public Book get(UUID id){
    return this.bookRepository.findById(id);
  }

}
