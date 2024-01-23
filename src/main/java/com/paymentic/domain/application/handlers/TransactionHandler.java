package com.paymentic.domain.application.handlers;

import com.paymentic.domain.Shelf;
import com.paymentic.domain.events.InFlightTransaction;

@FunctionalInterface
public interface TransactionHandler {
  void handle(InFlightTransaction event, Shelf shelf);

}
