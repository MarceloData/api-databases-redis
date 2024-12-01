package org.acme.rest;

import org.acme.service.CityService;

import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("cities")
public class CityResource {

    @Inject
    CityService cityService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response findTenCities(@QueryParam("identifier") String identifier){
        try {
            var result = cityService.findTenCities(identifier);
            return Response.ok(result).build();
        } catch (Exception e) {
        }
        return null;
    }
}
