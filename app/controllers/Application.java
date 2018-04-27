package controllers;

import jpa.User;
import models.UserForm;

import services.UserPersistenceService;

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

    //This tag lets spring know we need this object injected.
    @Inject
    private UserPersistenceService userPersist;

    private static final String EOL = System.getProperties().getProperty("line.separator");
    private static final Logger log = LoggerFactory.getLogger(Application.class);

    /**
     * calls index.render with two parameters, 1) a message, and 2) The User Form
     * @return the Result of calling Index
     */
    public Result index() {
        return ok(index.render("hello, world", play.data.Form.form(UserForm.class)));
    }

    /**
     * adds a user, and checks if the form had any errors. Uses UserForm to ensure user cannot enter their own ID or try
     * something funny with ID. Also logs at the info level that a user was created.
     * @return The Result
     */
    public Result addUser() {
        play.data.Form<UserForm> form = play.data.Form.form(UserForm.class).bindFromRequest();
        //check for unique!!!
        if (form.hasErrors()) {
            log.info("User had error(s)");
            return badRequest(index.render("Invalid form, try again.", form));
        }
        String username = form.get().getUsername().trim();
        if (username.length() < 3) {
            form.reject("username", "Field must be a minimum of 3 characters without leading or trailing whitespace");
            return badRequest(index.render("Invalid form, try again.", form));
        }
        User inDB = userPersist.fetchUserByUsername(username);
        if (inDB!=null) {
            form.reject("username", "Field must be unique");
            return badRequest(index.render("Invalid form, try again.", form));
        }
        User u = new User(username);

        userPersist.saveUser(u);

        log.info("Adding a User: " + u);

        return redirect(routes.Application.index());
    }

    /**
     * @return the users in JSON format.
     */
    public Result getUsers() {
        List<User> users = userPersist.fetchAllUsers();
        return ok(play.libs.Json.toJson(users));
    }
}
