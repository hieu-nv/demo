package app.demo.e2e;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static org.hamcrest.CoreMatchers.is;

import org.junit.jupiter.api.Test;

class DemoTest {

  @Test
  void testGet() {
    when()
        .get("https://dummyjson.com/products/1")
        .then()
        .log()
        .all()
        .assertThat()
        .header("X-Ratelimit-Limit", is("120"))
        .body("id", is(1))
        .body("title", is("iPhone 9"));
  }

  @Test
  void testPost() {
    given()
        .header("Content-Type", "application/json")
        .body("{ \"title\": \"A\" }")
        .and()
        .when()
        .post("https://dummyjson.com/products/add")
        .then()
        .log()
        .all()
        .assertThat()
        .body("id", is(101))
        .body("title", is("A"));
  }
}
