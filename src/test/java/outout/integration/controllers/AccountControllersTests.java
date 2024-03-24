package outout.integration.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;

import static io.restassured.RestAssured.given;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import io.restassured.RestAssured;
import java.util.HashMap;

@SpringBootTest(webEnvironment = RANDOM_PORT)
@TestPropertySource(properties = {"spring.main.allow-bean-definition-overriding=true", "server.servlet.context-path=/"})
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class AccountControllersTests {

    @LocalServerPort
    private int port;

    @BeforeEach
    public void setUp() {
        RestAssured.port = port;
    }

    @Test
    public void givenUnknowUri_Should_Return_Unauthorized() {
        given().get("/unknown")
                .then()
                .statusCode(403);
    }

    @Test
    public void givenAccountCreateRequest_Should_Return_Created() {
        //Arrange
        //Json body dictionary
        HashMap<String, String> body = new HashMap<>();
        body.put("username", "testme");
        body.put("password", "testme12345678");
        //Act and Assert
        //add media type to the request
        given().contentType("application/json")
                .when().body(body)
                .post("/account/create")
                .then()
                .statusCode(200);
    }

    @Test
    public void givenAccountCreateRequest_Should_Return_ValidationError() {
        //Arrange
        //Json body dictionary
        HashMap<String, String> body = new HashMap<>();
        body.put("username", "testme");
        body.put("password", "tooshort");
        //Act and Assert
        //add media type to the request
        given().contentType("application/json")
                .when().body(body)
                .post("/account/create")
                .then()
                .statusCode(400);
    }

    @Test
    public void givenAccountCreateRequest_Should_Return_BadRequest_when_user_already_exists() {
        //Arrange
        //Json body dictionary
        HashMap<String, String> body = new HashMap<>();
        body.put("username", "testme");
        body.put("password", "testme12345678");
        //Act and Assert
        //add media type to the request
        given().contentType("application/json")
                .when().body(body)
                .post("/account/create")
                .then()
                .statusCode(200);
        given().contentType("application/json")
                .when().body(body)
                .post("/account/create")
                .then()
                .statusCode(400);
    }

    @Test
    public void givenAccountCreated_Should_Authenticate() {
        //Arrange
        //Json body dictionary
        HashMap<String, String> body = new HashMap<>();
        body.put("username", "testme");
        body.put("password", "testme12345678");
        //Act and Assert
        //add media type to the request
        given().contentType("application/json")
                .when().body(body)
                .post("/account/create")
                .then()
                .statusCode(200);
        given().contentType("application/json")
                .when().body(body)
                .post("/authenticate")
                .then()
                .statusCode(200);
    }

    @Test
    public void givenAccountCreated_Should_Not_Authenticate() {
        //Arrange
        //Json body dictionary
        HashMap<String, String> body = new HashMap<>();
        body.put("username", "testme");
        body.put("password", "testme12345678");
        //Act and Assert
        //add media type to the request
        given().contentType("application/json")
                .when().body(body)
                .post("/account/create")
                .then()
                .statusCode(200);
        body.put("password", "wrongpassword");
        given().contentType("application/json")
                .when().body(body)
                .post("/authenticate")
                .then()
                .statusCode(401);
    }

}
