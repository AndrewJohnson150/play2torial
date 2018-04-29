package services;

import jpa.User;

import java.util.List;

public interface UserPersistenceService {
    boolean saveUser(User t);

    /**
     * fetches all users from database, in a List>.
     * @return A List of all Users
     */
    List<User> fetchAllUsers();

    /**
     * Fetches a user from the database by username. If there are multiple occurences of the same username, the method
     * will log an error saying so, and return null. If no User is found, the method will return null.
     * @param idSearch the username to fetch by.
     * @return The User with the username of the passed parameter.
     */
    User fetchUserByUsername(String idSearch);

}