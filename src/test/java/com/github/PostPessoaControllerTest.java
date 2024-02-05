package com.github;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;

import com.github.entity.Pessoa;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

@QuarkusTest
public class PostPessoaControllerTest {

    //POST /pessoas
    @Test
    void testPostPessoas() {
        Pessoa pessoa = new Pessoa();
        pessoa.setDepartamento(2);
        pessoa.setNome("Paulo");


        given()
            .contentType(ContentType.JSON)
            .body(pessoa)
        .when()
            .post("/pessoas")
        .then()
            .statusCode(204);
    }
}
