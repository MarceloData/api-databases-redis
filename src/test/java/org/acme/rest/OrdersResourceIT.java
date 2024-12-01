package org.acme.rest;

import static org.hamcrest.CoreMatchers.is;
import org.junit.jupiter.api.Test;

import io.quarkus.test.common.http.TestHTTPEndpoint;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;

import static io.restassured.RestAssured.given;

@QuarkusTest
@TestHTTPEndpoint(OrdersResource.class)
public class OrdersResourceIT {

    @Test
    void testHelloEndpoint() {
        given().contentType(ContentType.JSON)
          .when().get("/{customerNumber}",103)
          .then()
             .statusCode(200)
             .body("size()", is(3));
    }
}
