package com.paymentic.adapter.http.apis.api;



import com.paymentic.adapter.http.apis.model.RequestShelfCreation;
import com.paymentic.adapter.http.apis.model.Shelf;
import com.paymentic.adapter.http.apis.model.ShelfCreated;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;


import java.io.InputStream;
import java.util.Map;
import java.util.List;
import jakarta.validation.constraints.*;
import jakarta.validation.Valid;

@Path("/shelves")
@jakarta.annotation.Generated(value = "org.openapitools.codegen.languages.JavaJAXRSSpecServerCodegen", date = "2023-11-23T08:33:25.220758-03:00[America/Sao_Paulo]")
public interface ShelvesApi {

    @POST
    @Consumes({ "application/json" })
    @Produces({ "application/json" })
    ShelfCreated createShelf(@Valid @NotNull RequestShelfCreation requestShelfCreation);


    @GET
    @Path("/{id}")
    @Produces({ "application/json" })
    Shelf getBook(@PathParam("id") String id);

}
