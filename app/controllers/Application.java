package controllers;



import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import play.db.jpa.Transactional;
import play.db.jpa.JPA;
import models.Task;

import play.*;
import play.mvc.*;

import views.html.*;

public class Application extends Controller {

    private static final Logger log = LoggerFactory.getLogger(Application.class);

    public static Result index() {
        return ok(index.render("hello, world", play.data.Form.form(models.Task.class)));
    }

    @Transactional
    public static Result addTask() {
        log.info("Adding a task");
        play.data.Form<models.Task> form = play.data.Form.form(models.Task.class).bindFromRequest();
        if (form.hasErrors()) {
            return badRequest(index.render("This shit is broke", form));
        }

        models.Task task = form.get();
        play.db.jpa.JPA.em().persist(task);
        return redirect(routes.Application.index());
    }

    @Transactional
    public static Result getTasks() {
        java.util.List<models.Task> tasks = JPA.em().createQuery("from Task", Task.class).getResultList();
        return ok(play.libs.Json.toJson(tasks));
    }

    @Transactional
    public static models.Task getTaskById(Integer searchID) {
        java.util.List<models.Task> tasks = JPA.em()
                .createQuery("from Task t WHERE t.id = :id", Task.class)
                .setParameter("id", searchID)
                .getResultList(); //using result list because
        models.Task t = null;
        if (tasks.size()==1) {
            t = tasks.get(0);
        }
        else if (tasks.size() > 1)
        {
            log.info("non unique ID found");
        }
        return t;
    }
}
