package com.paymentic.domain.repositories;

import com.paymentic.domain.BookEntry;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class BookEntryRepository implements PanacheRepository<BookEntry> { }
