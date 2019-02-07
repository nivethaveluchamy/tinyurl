package com.practice.tinyurl;

import com.practice.tinyurl.model.CreateRequest;
import org.eclipse.jetty.http.HttpStatus;

import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class TinyUrlResource {
    private final TinyUrlService tinyUrlService;

    public TinyUrlResource(TinyUrlService tinyUrlService) {
        this.tinyUrlService = tinyUrlService;
    }


    @POST
    @Path("tinyUrl")
    public Response createTinyUrl(@NotNull CreateRequest createRequest) {
        if (createRequest.getUrl() == null) {
            throw new IllegalArgumentException("url cannot be null");
        }
        Long expire = createRequest.getExpireAt() == null ? 1577865599000l : createRequest.getExpireAt();
        String tinyUrl = this.tinyUrlService.createTinyUrl(createRequest.getUrl(), expire);
        if (tinyUrl == null) {
            return Response.status(HttpStatus.INTERNAL_SERVER_ERROR_500).build();
        }
        return Response.status(HttpStatus.OK_200).entity("http://localhost:8080/" + tinyUrl).build();
    }

    @GET
    @Path("{url}")
    public Response getLongUrl(@NotNull @PathParam("url") String tinyUrl) {
        String longUrl = this.tinyUrlService.getLongUrl(tinyUrl);
        if (longUrl == null) {
            return Response.status(HttpStatus.INTERNAL_SERVER_ERROR_500).build();
        }
        return Response.status(HttpStatus.OK_200).entity(longUrl).build();
    }
}
