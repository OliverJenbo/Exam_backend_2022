package facades;

import dtos.CarDTO;
import dtos.CarsDTO;
import dtos.DriverDTO;
import dtos.RaceDTO;
import entities.Car;
import entities.Driver;
import entities.Race;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import java.util.List;

public class RaceFacade {

    private static RaceFacade instance;
    private static EntityManagerFactory emf;

    private RaceFacade() {
    }

    public static RaceFacade getRaceFacade(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new RaceFacade();
        }
        return instance;
    }

    private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    /** CREATE RACE **/

    public RaceDTO createRace(RaceDTO raceDTO) {
        EntityManager em = emf.createEntityManager();
        Race race = new Race(raceDTO.getName(), raceDTO.getDate(), raceDTO.getTime(), raceDTO.getLocation());

        try {
            em.getTransaction().begin();
            em.persist(race);
            em.getTransaction().commit();

            return new RaceDTO(race);
        } finally {
            em.close();
        }
    }

    /** GET ALL RACES **/

    public List<RaceDTO> getAllRaces() {
        EntityManager em = getEntityManager();

        try {
            em.getTransaction().begin();
            TypedQuery<Race> query = em.createQuery("SELECT r FROM Race r", Race.class);
            List<Race> result = query.getResultList();
            em.getTransaction().commit();

            return RaceDTO.getDtos(result);
        } finally {
            em.close();
        }
    }

    /** GET DRIVERS BY RACE **/
    public List<DriverDTO> getDriversByRace(int id) {
        EntityManager em = getEntityManager();

        try {
            TypedQuery<Driver> query = em.createQuery("SELECT d FROM Driver d JOIN Race r WHERE r.id = :id", Driver.class);
            query.setParameter("id", id);
            List<Driver> result = query.getResultList();
            return DriverDTO.getDtos(result);
        } finally {
            em.close();
        }
    }


}
