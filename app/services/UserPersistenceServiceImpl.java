package services;

import jpa.User;


import java.util.List;

import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import play.mvc.Result;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@Named
public class UserPersistenceServiceImpl implements UserPersistenceService {

    //For logging
    private static final Logger log = LoggerFactory.getLogger(UserPersistenceServiceImpl.class);

    //Allows us to persist data.
    @PersistenceContext
    private EntityManager em;

    /**
     * simply persists the user that is passed. Validates the passed user.
     * @param user A user to persist to the DB
     */
    @Transactional
    @Override
    public boolean saveUser(User user) {
        if (user==null || user.getUsername() == null)
            return false;
        //trim off whitespace
        user.setUsername(user.getUsername().trim());
        if (user.getUsername().length()<3 ||
                user.getUsername().length()>20 ||
                user.getId() != null)
            return false;

        em.persist(user);
        return true;
    }

    /**
     * Uses a SQL command to fetch a list of all users in the DB
     * @return List of users
     */
    @Override
    public List<User> fetchAllUsers() {

        return em.createQuery("FROM User ORDER BY id ASC", User.class).getResultList();
    }

    /**
     * Fetches a user from the database by specific username. If there are multiple occurences of the same username, the method
     * will log an error saying so, and return null. If no User is found, the method will return null.
     * @param idSearch the username to fetch by.
     * @return The User with the username of the passed parameter.
     */
    @Override
    public User fetchUserByUsername(String idSearch) {
        List<User> users =  em
                .createQuery("FROM User t WHERE t.username = :name", User.class)
                .setParameter("name", idSearch)
                .getResultList(); //using result list because

        User t = null;
        //The expected behaviour
        if (users.size()==1) {
            t = users.get(0);
        }
        else if (users.size() > 1) //This shouldn't ever happen.
        {
            log.error("non unique user found");
        }
        return t;
    }
}