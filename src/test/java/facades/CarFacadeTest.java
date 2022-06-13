package facades;

import com.sun.xml.bind.v2.TODO;
import dtos.CarDTO;
import entities.Car;
import entities.Driver;
import entities.Race;
import org.junit.jupiter.api.*;
import utils.EMF_Creator;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.hasItem;
import static org.junit.jupiter.api.Assertions.*;

class CarFacadeTest {

    private static EntityManagerFactory emf;
    private static CarFacade FACADE;
    private static Car c1, c2, c3;
    private static Race r1, r2;
    private static Driver d1;

    public CarFacadeTest() {
    }

    @BeforeAll
    public static void setUpClass() {
        emf = EMF_Creator.createEntityManagerFactoryForTest();
        FACADE = CarFacade.getCarFacade(emf);
    }

    @AfterAll
    public static void tearDownClass() {
    }

    @BeforeEach
    public void setUp() {
        EntityManager em = emf.createEntityManager();

        r1 = new Race("Test Race 1", "Date 2", 12.00, "Area 52");
        r2 = new Race("Test Race 2", "Date 2", 14.00, "The moon");

        c1 = new Car("Car1", "Brand 1", "Idk 1", 1902);
        c2 = new Car("Car2", "Brand 2", "Idk 2", 1988);
        c3 = new Car("Car3", "Brand 3", "Idk 3", 1908);

        c1.addRaceToCar(r1);
        c2.addRaceToCar(r1);
        c3.addRaceToCar(r1);

        d1 = new Driver("BobberLobber", 1005, "male");


        try {
            em.getTransaction().begin();
            em.persist(c1);
            em.persist(c2);
            em.persist(c3);

            em.persist(r1);
            em.persist(r2);

            em.persist(d1);
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
            em.createNamedQuery("Car.deleteAllRows").executeUpdate();
            em.createNamedQuery("Driver.deleteAllRows").executeUpdate();
            em.createNamedQuery("Race.deleteAllRows").executeUpdate();
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    @Disabled
    @Test
    void connectToRace() {
//:TODO Fix connectToRace Test
        //Connect c3 to race r2 using ids
        FACADE.connectToRace(c3.getId(), r2.getId());

        //Get the list of cars in r2
        List<CarDTO> newRace = FACADE.getCarsByRace(r2.getId()).getCars();

        CarDTO c3DTO = new CarDTO(c3);

        //Confirm that r2 now has c3
        assertThat(newRace, hasItem(c3DTO));
    }

    @Test
    void getCarsByRace() {
        List<CarDTO> cars = FACADE.getCarsByRace(r1.getId()).getCars();

        int expected = 3;
        int actual = cars.size();

        //Test if the amount of boats in the harbour h1 is correct.
        assertEquals(expected, actual);
    }

    @Disabled
    @Test
    public void deleteCar() {

        FACADE.deleteCar(c3.getId());

        int expected = 2;
        int actual = FACADE.getAllCars().size();

        //assertEquals(expected, actual);
    }
}