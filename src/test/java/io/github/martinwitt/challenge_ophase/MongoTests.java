package io.github.martinwitt.challenge_ophase;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertTrue;

import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.keycloak.client.KeycloakTestClient;
import java.util.List;
import org.hamcrest.core.StringContains;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

@QuarkusTest
public class MongoTests {

    private static final String MONGO_DB_NAME = "challenge_ophase";
    private static final String aliceID = "eb4123a3-b722-4798-9af5-8957f823657a";

    KeycloakTestClient keycloakTestClient = new KeycloakTestClient();

    @BeforeAll
    public static void init() {
        User.deleteAll();
        Challenge challenge = new Challenge("aaa", "aaa", "cccc", List.of("lol"), List.of(), false, "bbb");
        User user = new User(aliceID, List.of(challenge), challenge);
        user.persist();
    }

    @Test
    public void testMongo() {
        var result = User.findAll().<User>list();
        System.out.println(result);
        assertTrue(result.size() > 0);
    }

    @Test
    public void testMongo2() {
        var result = User.getWithUserIDId(aliceID);
        System.out.println(result);
        assertTrue(result != null);
    }

    @Test
    public void testMongo3() {
        given().auth()
                .oauth2(keycloakTestClient.getAccessToken("alice"))
                .when()
                .get("/challenges")
                .then()
                .body(StringContains.containsString("aaa"))
                .statusCode(200);
    }
}
