package services;

import models.User;

import java.util.List;

public interface TaskPersistenceService {
    void saveUser(User t);

    List<User> fetchAllUsers();

    List<User> fetchUserByID(Integer idSearch);

}