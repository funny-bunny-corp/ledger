package com.paymentic.domain.repositories;

import com.paymentic.domain.Shelf;
import com.paymentic.domain.ids.OwnerId;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import io.quarkus.panache.common.Parameters;
import jakarta.enterprise.context.ApplicationScoped;
import java.util.UUID;
import org.apache.kafka.common.protocol.types.Field.Str;

@ApplicationScoped
public class ShelfRepository implements PanacheRepository<Shelf> {
  public Shelf byOwner(OwnerId ownerId){
    return find("owner.id = :ownerId",Parameters.with("ownerId", ownerId.getId())).firstResult();
  }
  public Shelf get(String id){
    return find("id = :id",Parameters.with("id", UUID.fromString(id))).firstResult();
  }

}
