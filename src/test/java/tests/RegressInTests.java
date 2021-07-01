package tests;

import extensions.Screenshots;
import io.restassured.RestAssured;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.parallel.ResourceLock;
import java.util.Properties;
import static filters.CustomLogFilter.customLogFilter;
import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.parallel.ResourceAccessMode.READ;
import static org.junit.jupiter.api.parallel.Resources.SYSTEM_PROPERTIES;


@ExtendWith({Screenshots.class})
public class RegressInTests {

    private Properties backup;

    @BeforeAll
    static void setup() {
        RestAssured.baseURI = "https://reqres.in";
    }

    @BeforeEach
    void backup() {
        backup = new Properties();
        backup.putAll(System.getProperties());
    }

    @AfterEach
    void restore() {
        System.setProperties(backup);
    }

    @Test
    @ResourceLock(value = SYSTEM_PROPERTIES, mode = READ)
    void singleResourceSuccessfulTest() {
        given()
                .filter(customLogFilter().withCustomTemplates())
                .when()
                .log().uri()
                .log().body()
                .get("/api/unknown/2")
                .then()
                .log().body()
                .statusCode(200)
                .body("data.id", is(2))
                .body("data.name", is("fuchsia rose"));
    }

    @Test
    @ResourceLock(value = SYSTEM_PROPERTIES, mode = READ)
    void singleResourceFailedTest() {
        given()
                .filter(customLogFilter().withCustomTemplates())
                .when()
                .log().uri()
                .log().body()
                .get("/api/unknown/23")
                .then()
                .log().body()
                .statusCode(400);
    }

    @Test
    @ResourceLock(value = SYSTEM_PROPERTIES, mode = READ)
    void registrationSuccessfulTest() {
        given()
                .filter(customLogFilter().withCustomTemplates())
                .contentType(JSON)
                .body("{ \"email\": \"eve.holt@reqres.in\", " +
                        "\"password\": \"pistol\" }")
                .when()
                .log().uri()
                .log().body()
                .post("/api/register")
                .then()
                .log().body()
                .statusCode(200)
                .body("id", is(4))
                .body("token", is("QpwL5tke4Pnpja7X4"));
    }

    @Test
    @ResourceLock(value = SYSTEM_PROPERTIES, mode = READ)
    void loginSuccessfulTest() {
        given()
                .filter(customLogFilter().withCustomTemplates())
                .contentType(JSON)
                .body("{ \"email\": \"eve.holt@reqres.in\", " +
                        "\"password\": \"cityslicka\" }")
                .when()
                .log().uri()
                .log().body()
                .post("/api/login")
                .then()
                .log().body()
                .statusCode(200)
                .body("token", is("QpwL5tke4Pnpja7X4"));
    }

    @Test
    void updateUserSuccessfulTest() {
        given()
                .filter(customLogFilter().withCustomTemplates())
                .contentType(JSON)
                .body("{ \"name\": \"morpheus\", " +
                        "\"job\": \"zion resident\" }")
                .when()
                .log().uri()
                .log().body()
                .put("/api/users/2")
                .then()
                .log().body()
                .statusCode(200)
                .body("name", is("morpheus"))
                .body("job", is("zion no resident"));
    }
}
