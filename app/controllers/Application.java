package controllers;

import models.User;
import models.UserForm;

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

    //This tag lets spring know we need this object injected.
    @Inject
    private TaskPersistenceService taskPersist;

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

        if (form.hasErrors()) {
            log.info("User had error(s)");
            return badRequest(index.render("Invalid form, try again.", form));
        }
        User u = new User();
        u.setUsername(form.get().getUsername());

        log.info("Adding a User: " + u);

        taskPersist.saveUser(u);
        return redirect(routes.Application.index());
    }

    /**
     * @return the users in JSON format.
     */
    public Result getUsers() {
        List<models.User> users = taskPersist.fetchAllUsers();
        return ok(play.libs.Json.toJson(users));
    }

    /**
     * This method searchs for a user by ID.
     * @param searchID the ID of the user to search for.
     * @return  The User with ID of searchID. Will return null if multiple users are found or no users are found.
     */
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
