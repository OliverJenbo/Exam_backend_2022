package facades;

import dtos.RaceDTO;
import entities.Driver;
import entities.Race;
import org.junit.jupiter.api.*;
import utils.EMF_Creator;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RaceFacadeTest {

    private static EntityManagerFactory emf;
    private static RaceFacade FACADE;
    private static Race r1, r2;

    public RaceFacadeTest() {
    }

    @BeforeAll
    public static void setUpClass() {
        emf = EMF_Creator.createEntityManagerFactoryForTest();
        FACADE = RaceFacade.getRaceFacade(emf);
    }

    @AfterAll
    public static void tearDownClass() {
    }

    @BeforeEach
    public void setUp() {
        EntityManager em = emf.createEntityManager();

        r1 = new Race("newName1", "newDate1", 99.99, "newLocation1");
        r2 = new Race("newName2", "newDate2", 99.99, "newLocation2");

        try {
            em.getTransaction().begin();
            em.persist(r1);
            em.persist(r2);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    @AfterEach
    public void tearDown() {
        EntityManager em = emf.createEntityManager();

        try {
            em.getTransaction().begin();
            em.createNamedQuery("Race.deleteAllRows").executeUpdate();
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    @Test
    void createRace() {
        //Create a new item - i4
        Race r3 = new Race("newName3", "newDate3", 99.99, "newLocation3");
        RaceDTO createdRace = new RaceDTO(r3);
        FACADE.createRace(createdRace);
        List<RaceDTO> items = FACADE.getAllRaces();

        /** Test expects 3 Races and will check the actual size through int actual**/
        int expected = 3;
        int actual = items.size();

        assertEquals(expected, actual);
    }
/** Test will test how many races there are, through races.size();**/
    @Test
    void getAllRaces() {
        List<RaceDTO> races = FACADE.getAllRaces();

        int expected = 2;
        int actual = races.size();

        assertEquals(expected, actual);
    }
}