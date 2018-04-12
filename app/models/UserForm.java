package models;

import play.data.validation.Constraints.Required;

/**
 * This class is solely for allowing the creation of a user without risk of someone specifying ID. This ensures that an
 * outside source cannot edit/modify ID.
 */
public class UserForm {

    @Required
    private String username;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}