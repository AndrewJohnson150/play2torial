package models;

import play.data.validation.Constraints.Required;
import play.data.validation.Constraints.MaxLength;
import play.data.validation.Constraints.MinLength;
import play.data.validation.Constraints.Pattern;

/**
 * This class is solely for allowing the creation of a user without risk of someone specifying ID. This ensures that an
 * outside source cannot edit/modify ID.
 */
public class UserForm {

    @Required
    @MaxLength(value = 20)
    @MinLength(value = 3)
    //@Pattern(value = "^\\S+$", message = "Cannot contain spaces")
    private String username;

    /**
     * Gets the username as a String
     * @return the username (String)
     */
    public String getUsername() {
        return username;
    }

    /**
     * sets the username of the User
     * @param username The string to set the username to.
     */
    public void setUsername(String username) {
        this.username = username;
    }
}