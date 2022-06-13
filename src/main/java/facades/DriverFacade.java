package facades;

import dtos.DriversDTO;
import entities.Driver;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import java.util.List;

public class DriverFacade {

    private static DriverFacade instance;
    private static EntityManagerFactory emf;


    private DriverFacade() {
    }

    public static DriverFacade getDriverFacade(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new DriverFacade();
        }
        return instance;
    }

/** GET DRIVERS ASSIGNED TO A RACE BY RACE ID**/
    public DriversDTO getDriversByRace(int id) {
        EntityManager em = emf.createEntityManager();

        try {
            TypedQuery<Driver> query = em.createQuery("SELECT d FROM Driver d JOIN d.cars c, c.raceList r WHERE r.id = :id", Driver.class);
            query.setParameter("id", id);
            List<Driver> result = query.getResultList();
            return new DriversDTO(result);
        } finally {
            em.close();
        }
    }


}
