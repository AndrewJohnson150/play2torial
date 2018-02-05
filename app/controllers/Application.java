package controllers;



import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import play.*;
import play.mvc.*;

import views.html.*;

public class Application extends Controller {

    private static final Logger log = LoggerFactory.getLogger(Application.class);

    public static Result index() {
        return ok(index.render("hello, world", play.data.Form.form(models.Task.class)));
    }

    public static Result addTask() {
        log.info("Adding a task");
        play.data.Form<models.Task> form = play.data.Form.form(models.Task.class).bindFromRequest();
        if (form.hasErrors()) {
            return badRequest(index.render("hello, world", form));
        }
        else {
            models.Task task = form.get();
            task.save();
            return redirect(routes.Application.index());
        }
    }

    public static void clearTasks() {
        java.util.List<models.Task> tasks = new play.db.ebean.Model.Finder(String.class, models.Task.class).all();
        tasks = null;
    }

    public static Result getTasks() {
        java.util.List<models.Task> tasks = new play.db.ebean.Model.Finder(String.class, models.Task.class).all();
        return ok(play.libs.Json.toJson(tasks));
    }
}
