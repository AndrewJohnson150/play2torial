package services;

import models.Task;

import java.util.List;

public interface TaskPersistenceService {
    void saveTask(Task t);

    List<Task> fetchAllTasks();

    List<Task> fetchTaskByID(Integer idSearch);
}