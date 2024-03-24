package outout.integration.controllers;


import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;
import outout.model.Restaurant;

import java.util.HashMap;

import static io.restassured.RestAssured.given;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.DEFINED_PORT;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@SpringBootTest(webEnvironment = DEFINED_PORT)
@TestPropertySource(properties = {"spring.main.allow-bean-definition-overriding=true", "server.servlet.context-path=/"})
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class SuggestionControllerTest {

    private String token;

    @LocalServerPort
    private int port = 8080;

    @BeforeEach
    public void setUp() {
        //create account to be used in the tests
        HashMap<String, String> body = new HashMap<>();
        body.put("username", "SuggestionUser");
        body.put("password", "testme12345678");
        //Act and Assert
        //add media type to the request
        given().contentType("application/json")
                .when().body(body)
                .post("/account/create")
                .then()
                .statusCode(200);
        //authorize the account
        token = given().contentType("application/json")
                .when().body(body)
                .post("/authenticate")
                .then()
                .statusCode(200)
                .extract()
                .path("token");
        //set the token to be used in the tests

        RestAssured.port = port;
    }



    @Test
    public void should_get_suggestions() {
        //het suggestions set the token
        String t = token;
        given().header("X-AUTH-TOKEN", token)
                .when().get("/suggestion")
                .then()
                .statusCode(200);
    }

    @Test
    public void should_set_suggestion() {
        //set suggestion set the token
        String t = token;
        Restaurant restaurant = new Restaurant();
        restaurant.setName("test restaurant");
        HashMap<String, String> body = new HashMap<>();
        body.put("restaurant", restaurant.getName());
        given().header("X-AUTH-TOKEN", token)
                .contentType("application/json")
                .when().body(body)
                .post("/suggestion")
                .then()
                .statusCode(200);
    }

    @Test
    public void should_not_set_suggestion() {
        //set suggestion set the token
        String t = token;
        Restaurant restaurant = new Restaurant();
        restaurant.setName("test restaurant");
        HashMap<String, String> body = new HashMap<>();
        body.put("restaurant", restaurant.getName());
        given().header("X-AUTH-TOKEN", "invalidToken")
                .contentType("application/json")
                .when().body(body)
                .post("/suggestion")
                .then()
                .statusCode(403);
    }
    
    @Test
    public void shouldnt_set_suggestion_more_than_one_request() {
        //set suggestion set the token
        String t = token;
        Restaurant restaurant = new Restaurant();
        restaurant.setName("test restaurant");
        HashMap<String, String> body = new HashMap<>();
        body.put("restaurant", restaurant.getName());
        given().header("X-AUTH-TOKEN", token)
                .contentType("application/json")
                .when().body(body)
                .post("/suggestion")
                .then()
                .statusCode(200);
        given().header("X-AUTH-TOKEN", token)
                .contentType("application/json")
                .when().body(body)
                .post("/suggestion")
                .then()
                .statusCode(400);
        
    }

}
