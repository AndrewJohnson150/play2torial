package controllers;

import models.Task;

import services.TaskPersistenceService;
import services.TaskPersistenceServiceImpl;

import views.html.index;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import play.db.jpa.Transactional;
import play.db.jpa.JPA;
import play.data.Form;
import views

import play.mvc.Controller;
import play.mvc.Result;

import java.util.List;


public class Application extends Controller {

    private static final TaskPersistenceService taskPersist = new TaskPersistenceServiceImpl();

    private static final String EOL = System.getProperties().getProperty("line.separator");
    private static final Logger log = LoggerFactory.getLogger(Application.class);

    public static Result index() {
        return ok(index.render("hello, world", play.data.Form.form(models.Task.class)));
    }

    @Transactional
    public static Result addTask() {
        play.data.Form<models.Task> form = play.data.Form.form(models.Task.class).bindFromRequest();
        log.info("Adding a task: " + form);
        if (form.hasErrors()) {
            log.info("Task had error(s)");
            return badRequest(index.render("Invalid form, try again.", form));
        }

        models.Task task = form.get();
        taskPersist.persist(task);
        return redirect(routes.Application.index());
    }

    @Transactional
    public static Result getTasks() {
        List<models.Task> tasks = taskPersist.fetchAllTasks();
        return ok(play.libs.Json.toJson(tasks));
    }

    @Transactional
    public static models.Task getTaskById(Integer searchID) {
        return taskPersist.fetchTaskByID(searchID);
    }
}
