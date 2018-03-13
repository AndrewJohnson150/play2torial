package controllers;

import models.Task;

import services.TaskPersistenceService;

import views.html.index;

import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Named
public class Application extends Controller {

    @Inject
    private TaskPersistenceService taskPersist;

    private static final String EOL = System.getProperties().getProperty("line.separator");
    private static final Logger log = LoggerFactory.getLogger(Application.class);

    public Result index() {
        return ok(index.render("hello, world", play.data.Form.form(models.Task.class)));
    }

    public Result addTask() {
        play.data.Form<models.Task> form = play.data.Form.form(models.Task.class).bindFromRequest();
        log.info("Adding a task: " + form);
        if (form.hasErrors()) {
            log.info("Task had error(s)");
            return badRequest(index.render("Invalid form, try again.", form));
        }

        models.Task task = form.get();
        taskPersist.saveTask(task);
        return redirect(routes.Application.index());
    }

    public Result getTasks() {
        List<models.Task> tasks = taskPersist.fetchAllTasks();
        return ok(play.libs.Json.toJson(tasks));
    }

    public Task getTaskById(Integer searchID) {
        List<Task> tasks = taskPersist.fetchTaskByID(searchID);
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
