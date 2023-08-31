package com.paymentic.domain.repositories;

import com.paymentic.domain.JournalEntry;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import java.util.UUID;

@ApplicationScoped
public class JournalEntryRepository implements PanacheRepository<JournalEntry> { }
