package org.acme.rest;

import java.util.List;

import org.acme.models.mysql.Orders;
import org.acme.service.OrderService;

import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("orders")
public class OrdersResource {

    @Inject
    OrderService orderService;

    @GET
    @Path("/{customerNumber}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response listOrdersByClient(Integer customerNumber){
        try {
            List<Orders> result = orderService.findOrdersByClient(customerNumber);
            return Response.ok(result).build();
        } catch (Exception e) {
        }
        return null;
    }
}
