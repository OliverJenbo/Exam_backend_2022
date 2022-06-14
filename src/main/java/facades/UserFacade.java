package facades;

import entities.Role;
import entities.User;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import security.errorhandling.AuthenticationException;

import java.util.ArrayList;
import java.util.List;

public class UserFacade {

    private static EntityManagerFactory emf;
    private static UserFacade instance;

    private UserFacade() {
    }

    public static UserFacade getUserFacade(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new UserFacade();
        }
        return instance;
    }
/** Initialize users **/
    public List<User> initUsers() throws Exception {
        EntityManager em = emf.createEntityManager();

        User user = new User("user", "test1");
        User admin = new User("admin", "test1");
        User both = new User("user_admin", "test1");

        Role userRole = new Role("user");
        Role adminRole = new Role("admin");

        user.addRole(userRole);
        admin.addRole(adminRole);
        both.addRole(userRole);
        both.addRole(adminRole);

        em.getTransaction().begin();

        em.persist(userRole);
        em.persist(adminRole);

        em.persist(user);
        em.persist(admin);
        em.persist(both);

        em.getTransaction().commit();

        List<User> users = new ArrayList<>();
        return users;
    }

/** Find already created users **/
    public User getVeryfiedUser(String username, String password) throws AuthenticationException {
        EntityManager em = emf.createEntityManager();
        User user;
        try {
            user = em.find(User.class, username);
            if (user == null || !user.verifyPassword(password)) {
                throw new AuthenticationException("Invalid username or password");
            }
        } finally {
            em.close();
        }
        return user;
    }
/** Create new user **/
public User CreateNewUser(User user) throws AuthenticationException {
    EntityManager em = emf.createEntityManager();

    try {
        Role userRole = new Role("user");
        em.getTransaction().begin();

        user.setUserName(user.getUserName());
        user.addRole(userRole);
        //user.setUserPass(user.getUserPass());
        em.persist(user);
        em.getTransaction().commit();
    } finally {
        em.close();
    }
    return user;
}
/** Initialize the Database **/
    public List<User> initDB() throws Exception {
        EntityManager em = emf.createEntityManager();
        if (em.find(User.class, "user") == null) {


            User user = new User("user", "test123");
            User admin = new User("admin", "test123");
            User both = new User("user_admin", "test123");

            em.getTransaction().begin();
            Role userRole = new Role("user");
            Role adminRole = new Role("admin");
            user.addRole(userRole);
            admin.addRole(adminRole);
            both.addRole(userRole);
            both.addRole(adminRole);
            em.persist(userRole);
            em.persist(adminRole);
            em.persist(user);
            em.persist(admin);
            em.persist(both);
            em.getTransaction().commit();
            List<User> userlist = new ArrayList<>();
            userlist.add(user);
            userlist.add(admin);
            userlist.add(both);
            return userlist;
        } else throw new Exception("Init has already happened!");
    }
}
