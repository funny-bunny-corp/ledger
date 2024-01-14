package com.paymentic.adapter.persistence;

import com.paymentic.domain.Shelf;
import com.paymentic.domain.ids.OwnerId;
import com.paymentic.domain.repositories.ShelfRepository;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import io.quarkus.panache.common.Parameters;
import jakarta.enterprise.context.ApplicationScoped;
import java.util.UUID;

@ApplicationScoped
public class JpaShelfRepository implements PanacheRepository<Shelf>, ShelfRepository {
  public Shelf byOwner(OwnerId ownerId){
    return find("owner.id = :ownerId",Parameters.with("ownerId", ownerId.getId())).firstResult();
  }
  public Shelf get(String id){
    return find("id = :id",Parameters.with("id", UUID.fromString(id))).firstResult();
  }
  @Override
  public void save(Shelf shelf) {
    persist(shelf);
  }

}
