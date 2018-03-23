package controllers;

import models.User;

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
        return ok(index.render("hello, world", play.data.Form.form(models.User.class)));
    }

    public Result addUser() {
        play.data.Form<User> form = play.data.Form.form(User.class).bindFromRequest();
        log.info("Adding a User: " + form);
        if (form.hasErrors()) {
            log.info("User had error(s)");
            return badRequest(index.render("Invalid form, try again.", form));
        }

        models.User user = form.get();
        taskPersist.saveUser(user);
        return redirect(routes.Application.index());
    }

    public Result getUsers() {
        List<models.User> users = taskPersist.fetchAllUsers();
        return ok(play.libs.Json.toJson(users));
    }

    public User getUserById(Integer searchID) {
        List<User> users = taskPersist.fetchUserByID(searchID);
        User t = null;
        if (users.size()==1) {
            t = users.get(0);
        }
        else if (users.size() > 1)
        {
            log.error("non unique ID found");
        }
        return t;

    }
}
