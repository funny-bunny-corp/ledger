package com.paymentic.adapter.http.apis.api;

import com.paymentic.adapter.http.apis.model.BooksInner;
import com.paymentic.adapter.http.apis.model.RecordsInner;
import com.paymentic.adapter.http.apis.model.RequestShelfCreation;
import com.paymentic.adapter.http.apis.model.Shelf;
import com.paymentic.adapter.http.apis.model.ShelfCreated;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import java.util.List;


@Path("/shelves")
@jakarta.annotation.Generated(value = "org.openapitools.codegen.languages.JavaJAXRSSpecServerCodegen", date = "2023-12-09T10:59:29.842184-03:00[America/Sao_Paulo]")
public interface ShelvesApi {

    @POST
    @Consumes({ "application/json" })
    @Produces({ "application/json" })
    ShelfCreated createShelf(@Valid @NotNull RequestShelfCreation requestShelfCreation);

    @GET
    @Path("/{id}/books")
    @Produces({ "application/json" })
    List<BooksInner> getBooks(@PathParam("id") String id);

    @GET
    @Path("/{id}/books/{bookId}/records")
    @Produces({ "application/json" })
    List<RecordsInner> getRecords(@PathParam("id") String id,@PathParam("bookId") String bookId);

    @GET
    @Path("/{id}")
    @Produces({ "application/json" })
    Shelf getShelf(@PathParam("id") String id);

}
