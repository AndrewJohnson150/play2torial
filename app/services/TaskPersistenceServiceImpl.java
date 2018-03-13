package services;

import models.Task;


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
    public void saveTask(Task task) {
        em.persist(task);
    }

    @Override
    public List<Task> fetchAllTasks() {
        return em.createQuery("from Task", Task.class).getResultList();
    }

    @Override
    public List<Task> fetchTaskByID(Integer idSearch) {
        return em
                .createQuery("from Task t WHERE t.id = :id", Task.class)
                .setParameter("id", idSearch)
                .getResultList(); //using result list because
    }
}