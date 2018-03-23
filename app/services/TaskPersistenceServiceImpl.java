package services;

import models.User;


import java.util.List;

import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;


@Named
public class TaskPersistenceServiceImpl implements TaskPersistenceService {

    @PersistenceContext
    private EntityManager em;

    @Transactional
    @Override
    public void saveUser(User user) {
        em.persist(user);
    }

    @Override
    public List<User> fetchAllUsers() {
        return em.createQuery("from User", User.class).getResultList();
    }

    @Override
    public List<User> fetchUserByID(Integer idSearch) {
        return em
                .createQuery("from User t WHERE t.id = :id", User.class)
                .setParameter("id", idSearch)
                .getResultList(); //using result list because
    }
}