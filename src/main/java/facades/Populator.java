/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facades;

import entities.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import utils.EMF_Creator;

import java.time.LocalDate;

public class Populator {
    public static void populate() {
        EntityManagerFactory emf = EMF_Creator.createEntityManagerFactory();
        EntityManager em = emf.createEntityManager();

        User user = new User("user", "123");
        User admin = new User("admin", "123");
        User both = new User("user_admin", "123");

        Role userRole = new Role("user");
        Role adminRole = new Role("admin");

        user.addRole(userRole);
        admin.addRole(adminRole);
        both.addRole(userRole);
        both.addRole(adminRole);

        Driver d1 = new Driver("Oliver", 1999, "male");
        Driver d2 = new Driver("Kristian", 1996, "male");
        Driver d3 = new Driver("Henrikke", 1973, "female");
        Driver d4 = new Driver("Danny", 1973, "male");
        Driver d5 = new Driver("Jeanett", 1973, "female");
        Driver d6 = new Driver("Madeline", 2004, "female");
        Driver d7 = new Driver("Amalie", 2003, "female");

        Car c1 = new Car("OvloV v2", "Volvo", "V9", 2020);
        Car c2 = new Car("irarreF v2", "ferrari", "1.5", 2022);
        Car c3 = new Car("WMB v2", "BMW", "V6", 2019);
        Car c4 = new Car("toegueP v2", "Peugeot", "V2", 2019);
        Car c5 = new Car("alseT v2", "Tesla", "V4", 2019);
        Car c6 = new Car("WMB v3", "BMW", "V5.1", 2019);
        Car c7 = new Car("iduA v2", "Audi", "V10", 2019);
        Car c8 = new Car("neortiC v2", "Citroen", "V6", 2019);

        Race r1 = new Race("Copenhagen Grand Prix", "01-05-2022", 10.50, "Copenhagen");
        Race r2 = new Race("Stokholm Race Royale", "15-06-2022", 55.00, "Stokholm");
        Race r3 = new Race("Denmark around", "08-08-2022", 25.10, "Denmark");

/** Assigning drivers to cars **/
        c1.setDriver(d1);
        c1.setDriver(d2);
        c2.setDriver(d3);
        c3.setDriver(d3);
        c4.setDriver(d4);
        c5.setDriver(d5);
        c6.setDriver(d6);
        c7.setDriver(d6);
        c8.setDriver(d7);

/** Race merge with Car **/

        c1.addRaceToCar(r1);
        c2.addRaceToCar(r1);
        c3.addRaceToCar(r1);
        c4.addRaceToCar(r1);
        c5.addRaceToCar(r2);
        c6.addRaceToCar(r2);
        c7.addRaceToCar(r2);
        c8.addRaceToCar(r3);

        em.getTransaction().begin();
/** Role persistances */
        em.persist(userRole);
        em.persist(adminRole);
/** Race persistances **/
        em.persist(r1);
        em.persist(r2);
        em.persist(r3);
/** user persistances **/
        em.persist(user);
        em.persist(admin);
        em.persist(both);
/** Driver Persistances **/
        em.persist(d1);
        em.persist(d2);
        em.persist(d3);
        em.persist(d4);
        em.persist(d5);
        em.persist(d6);
        em.persist(d7);
/** Car persistances **/
        em.persist(c1);
        em.persist(c2);
        em.persist(c3);
        em.persist(c4);
        em.persist(c5);
        em.persist(c6);
        em.persist(c7);
        em.persist(c8);

        em.getTransaction().commit();
    }

    public static void initItems(){

    }

    public static void main(String[] args) {
        populate();
    }
}
