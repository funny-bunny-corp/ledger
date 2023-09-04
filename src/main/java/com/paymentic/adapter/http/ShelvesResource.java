package com.paymentic.adapter.http;

import com.paymentic.domain.application.ShelfService;
import com.paymentic.domain.ids.OwnerId;
import jakarta.enterprise.context.ApplicationScoped;
import java.util.UUID;
import org.openapitools.api.ShelvesApi;
import org.openapitools.model.RequestShelfCreation;
import org.openapitools.model.Shelf;
import org.openapitools.model.ShelfCreated;

@ApplicationScoped
public class ShelvesResource implements ShelvesApi {
  private final ShelfService shelfService;
  public ShelvesResource(ShelfService shelfService) {
    this.shelfService = shelfService;
  }
  @Override
  public ShelfCreated createShelf(RequestShelfCreation requestShelfCreation) {
    var shelf = com.paymentic.domain.Shelf.newShelf(UUID.randomUUID().toString(),new OwnerId(requestShelfCreation.getOwner().getId()));
    this.shelfService.register(shelf);
    return new ShelfCreated().id(shelf.getId().toString());
  }
  @Override
  public Shelf getBook(String id) {
    var data = this.shelfService.get(id);
    return new Shelf().id(data.getId().toString());
  }

}
