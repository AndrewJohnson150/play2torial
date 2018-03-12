package services;

import models.Task;


import java.util.List;

import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

@Named
public class TaskPersistenceServiceImpl implements TaskPersistenceServices {

    @PersistenceContext
    private EntityManager em;

    @Transactional
    @override
    public void saveTask(Task task) {
        JPA.em().persist(task);
    }

    @Override
    public List<Task> fetchAllTasks() {
        return em.createQuery("from Task", Task.class).getResultList();
    }

    @override
    public Task fetchTaskByID(Integer idSearch) {
        java.util.List<models.Task> tasks = em
                .createQuery("from Task t WHERE t.id = :id", Task.class)
                .setParameter("id", idSearch)
                .getResultList(); //using result list because
        models.Task t = null;
        if (tasks.size()==1) {
            t = tasks.get(0);
        }
        else if (tasks.size() > 1)
        {
            log.error("non unique ID found");
        }
        return t;
    }
}