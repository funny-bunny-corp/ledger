package com.paymentic.domain.repositories;

import com.paymentic.domain.Shelf;
import com.paymentic.domain.ids.OwnerId;

public interface ShelfRepository {
  Shelf byOwner(OwnerId ownerId);
  Shelf get(String id);
  void save(Shelf shelf);

}
