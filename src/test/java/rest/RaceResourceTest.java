package rest;

import dtos.DriverDTO;
import dtos.RaceDTO;
import entities.Car;
import entities.Driver;
import entities.Race;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.parsing.Parser;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;
import org.junit.jupiter.api.*;
import utils.EMF_Creator;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.ws.rs.core.UriBuilder;
import java.net.URI;
import java.util.List;

import static io.restassured.RestAssured.given;
import static javax.management.Query.not;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.hasItem;
import static org.junit.jupiter.api.Assertions.*;

class RaceResourceTest {

    private static final int SERVER_PORT = 7777;
    private static final String SERVER_URL = "http://localhost/api";
    private static Car c1, c2, c3;
    private static Driver d1, d2, d3;
    private static Race r1, r2;

    static final URI BASE_URI = UriBuilder.fromUri(SERVER_URL).port(SERVER_PORT).build();
    private static HttpServer httpServer;
    private static EntityManagerFactory emf;

    static HttpServer startServer() {
        ResourceConfig rc = ResourceConfig.forApplication(new ApplicationConfig());
        return GrizzlyHttpServerFactory.createHttpServer(BASE_URI, rc);
    }

    @BeforeAll
    public static void setUpClass() {
        //This method must be called before you request the EntityManagerFactory
        EMF_Creator.startREST_TestWithDB();
        emf = EMF_Creator.createEntityManagerFactoryForTest();

        httpServer = startServer();
        //Setup RestAssured
        RestAssured.baseURI = SERVER_URL;
        RestAssured.port = SERVER_PORT;
        RestAssured.defaultParser = Parser.JSON;
    }

    @AfterAll
    public static void closeTestServer() {
        //System.in.read();
        //Don't forget this, if you called its counterpart in @BeforeAll
        EMF_Creator.startREST_TestWithDB();
        httpServer.shutdownNow();
    }

    @BeforeEach
    public void setUp() {
        EntityManager em = emf.createEntityManager();

        c1 = new Car("testName1", "testBrand1", "testMake1", 1, d1);
        c2 = new Car("testName2", "testBrand2", "testMake2", 2, d2);
        c3 = new Car("testName3", "testBrand3", "testMake3", 3, d3);

        r1 = new Race("testName1", "testDate1", 1, "testLocation1");
        r2 = new Race("testName2", "testDate2", 2, "testLocation2");

        d1 = new Driver("testName1", 1, "testGender1");
        d2 = new Driver("testName2", 2, "testGender2");
        d3 = new Driver("testName3", 3, "testGender3");

        c1.addRaceToCar(r1);
        c2.addRaceToCar(r1);
        c3.addRaceToCar(r1);

        try {
            em.getTransaction().begin();

            em.persist(r1);
            em.persist(r2);

            em.persist(c1);
            em.persist(c2);
            em.persist(c3);

            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    //This is how we hold on to the token after login, similar to that a client must store the token somewhere
    private static String securityToken;

    //Utility method to login and set the returned securityToken
    private static void login(String role, String password) {
        String json = String.format("{username: \"%s\", password: \"%s\"}", role, password);
        securityToken = given()
                .contentType("application/json")
                .body(json)
                .when().post("/login")
                .then()
                .extract().path("token");
        //System.out.println("TOKEN ---> " + securityToken);
    }

    private void logOut() {
        securityToken = null;
    }

    @Disabled
    @Test
    void createRace() {
        Race race = new Race("testName1", "testDate1", 1.0, "testLocation1");

        given()
                .contentType("application/json")
                .body(new RaceDTO(race))
                .when()
                .post("/race/create")
                .then()
                .statusCode(200)
                .body("id", notNullValue())
                .body("name", equalTo("testName1"))
                .body("date", equalTo("testDate1"))
                .body("time", equalTo("1.0"))
                .body("location", equalTo("testLocation1"));
    }

    @Disabled
    @Test
    void getRaces() {
        List<RaceDTO> races;

        races = given()
                .contentType("application/json")
                .accept(ContentType.JSON)
                .get("/race/all").then()
                .extract()
                .body()
                .jsonPath()
                .getList("races", RaceDTO.class);

        //Locally the size is 2, but pushed it is 4
        assertEquals(2, races.size());
    }

    @Disabled
    @Test
    void deleteRace(){
        given()
                .contentType("application/json")
                .pathParam("id", r1.getId())
                .delete("/race/delete/{id}")
                .then()
                .assertThat()
                .statusCode(200);
    }
}