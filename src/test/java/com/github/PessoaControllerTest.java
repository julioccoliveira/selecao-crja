package org.acme;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;

import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTest;


@QuarkusTest
class PessoaControllerTest {

    // GET /pessoas
    @Test
    void testGetPessoas() {
        given()
        .when()
            .get("/pessoas")
        .then()
            .statusCode(200)
            .body(equalTo("[]"));
    }
}