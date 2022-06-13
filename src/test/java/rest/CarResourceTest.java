package rest;

import dtos.CarDTO;
import entities.Car;
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
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.junit.jupiter.api.Assertions.*;

@Disabled
class CarResourceTest {

    private static final int SERVER_PORT = 7777;
    private static final String SERVER_URL = "http://localhost/api";
    private static Car c1, c2, c3;
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

        c1 = new Car("Car1", "Brand1", "Make1", 1999);
        c2 = new Car("Car2", "Brand2", "Make2", 2009);
        c3 = new Car("Car3", "Brand3", "Make3", 3009);

        r1 = new Race("Name1", "Date1", 12, "Jupiter");
        r2 = new Race("Name2", "Date2", 14, "Mercury");


        r1.addCarToRace(c1);
        r1.addCarToRace(c2);
        r1.addCarToRace(c3);

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


    @Test
    void getCarsByRace() {
        List<CarDTO> cars;

        cars = given()
                .contentType("application/json")
                .accept(ContentType.JSON)
                .pathParam("id", r1.getId())
                .when()
                .get("/car/getCarsByRace/{id}").then()
                .statusCode(200)
                .extract()
                .body()
                .jsonPath()
                .getList("cars", CarDTO.class);

        assertEquals(3, cars.size());
    }

    @Test
    void getCars() {
            List<CarDTO> cars;

            cars = given()
                    .contentType("application/json")
                    .accept(ContentType.JSON)
                    .get("/car/all").then()
                    .extract()
                    .body()
                    .jsonPath()
                    .getList("cars", CarDTO.class);

            assertEquals(3, cars.size());
    }

    @Test
    void connectCarToRace() {
        given()
                .contentType("application/json")
                .pathParam("id", c3.getId())
                .body(r2.getId())
                .when()
                .put("/car/connectCarToRace/{id}")
                .then()
                .statusCode(200);
    }
}