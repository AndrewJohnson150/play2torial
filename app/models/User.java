package models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * This is the primary model for our application. It stores ID and Username.
 */
@Entity
@Table(name = "users")
public class User {

    //These are used for the ToString() method
    private static final String EOL = System.getProperties().getProperty("line.separator");
    private static final String TAB = "   ";

    @Id
    @GeneratedValue
    @Column(unique=true)
    private Integer id;

    @play.data.validation.Constraints.Required
    @Column(name = "username",unique=true)
    private String username;

    /**
     * The no-arg constructor that the framework needs.
     */
    public User() {
    }

    /**
     * This is a convenience constructor that allows us to set username at the same time as we create the User
     * @param username - the username.
     */
    public User(String username) {
        this();
        setUsername(username);
    }

    /**
     * Gets the Integer id
     * @return the id of the user (Integer)
     */
    public Integer getId()  {
        return id;
    }

    /**
     * Sets the id of the User. Should only really be accessed by the framework.
     * @param id An Integer, the id to set.
     */
    public void setId(Integer id) {
        this.id = id;
    }

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

    /**
     * for use in the log, prints out the information of the user on several lines. Is formatted for readiability.
     * Example:
     * User [
     * id=3
     * username='aaa'
     * ]
     * @return A nicely formatted String which identifies this User
     */
    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append("User [").append(EOL);
        sb.append(TAB).append("id=").append(id).append(EOL);
        sb.append(TAB).append("username='").append(username).append("'").append(EOL);
        sb.append("]").append(EOL);
        return sb.toString();
    }
}