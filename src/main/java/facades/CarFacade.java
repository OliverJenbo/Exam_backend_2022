package facades;

import dtos.CarDTO;
import dtos.CarsDTO;
import entities.Car;
import entities.Race;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import java.util.List;

public class CarFacade {

    private static CarFacade instance;
    private static EntityManagerFactory emf;

    //Private Constructor to ensure Singleton
    private CarFacade() {
    }

    public static CarFacade getCarFacade(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new CarFacade();
        }
        return instance;
    }

    private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

/** METHOD FOR CONNECTING A CAR TO A RACE BASED ON CAR ID AND RACE ID **/
    public CarDTO connectToRace(int carId, int raceId) {
        EntityManager em = emf.createEntityManager();
        Car car = em.find(Car.class, carId);
        Race race = em.find(Race.class, raceId);

        race.addCarToRace(car);
        try {
            em.getTransaction().begin();
            em.merge(car);
            em.getTransaction().commit();
            return new CarDTO(car);
        } finally {
            em.close();
        }
    }

/** METHOD FOR DELETING A CAR **/

    public CarDTO deleteCar(int id) {
        EntityManager em = getEntityManager();
        Car car = em.find(Car.class, id);

        try {
            em.getTransaction().begin();
            em.createNativeQuery("DELETE FROM Car_Race WHERE carList_id =?").setParameter(1, car.getId()).executeUpdate();
            em.remove(car);
            em.getTransaction().commit();

            return new CarDTO(car);
        } finally {
            em.close();
        }
    }

/** METHOD FOR EXTRACTING CARS ASSIGNED TO RACE BY ID**/
    public CarsDTO getCarsByRace(int id) {
        EntityManager em = emf.createEntityManager();
        try {
            TypedQuery<Car> query = em.createQuery("SELECT c FROM Car c JOIN c.raceList r WHERE r.id =:id", Car.class);
            query.setParameter("id", id);
            List<Car> result = query.getResultList();
            return new CarsDTO(result);
        } finally {
            em.close();
        }
    }

/** METHOD FOR EXTRACTING ALL CARS **/
    public List<CarDTO> getAllCars() {
        EntityManager em = getEntityManager();

        try {
            em.getTransaction().begin();
            TypedQuery<Car> query = em.createQuery("SELECT c FROM Car c", Car.class);
            List<Car> result = query.getResultList();
            em.getTransaction().commit();

            return CarDTO.getDtos(result);
        } finally {
            em.close();
        }
    }
}
