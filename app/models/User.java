package models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "users")
public class User {

    private static final String EOL = System.getProperties().getProperty("line.separator");
    private static final String TAB = "   ";

    public User() {
    }

    @Id
    @GeneratedValue
    @Column(unique=true)
    private Integer id;

    @play.data.validation.Constraints.Required
    @Column(name = "username",unique=true)
    private String username;

    public Integer getId()  {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append("User [").append(EOL);
        sb.append(TAB).append("id=").append(id).append(EOL);
        sb.append(TAB).append("username='").append(username).append("'").append(EOL);
        sb.append("]").append(EOL);
        return sb.toString();
    }
}