package services;

import models.Task;

import play.db.jpa.JPA;

import java.util.List;

public class TaskPersistenceServiceImpl implements TaskPersistenceServices {

    @override
    public void saveTask(Task task) {
        JPA.em().persist(task);
    }

    @Override
    public List<Task> fetchAllTasks() {
        return JPA.em().createQuery("from Task", Task.class).getResultList();
    }

    @override
    public Task fetchTaskByID(Integer idSearch) {
        java.util.List<models.Task> tasks = JPA.em()
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