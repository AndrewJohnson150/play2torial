package models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "task_table")
public class Task {

    public Task() {
    }

    @Id
    @GeneratedValue
    @Column(unique=true)
    private Integer id;

    @play.data.validation.Constraints.Required
    @Column(name = "contents_col",unique=true)
    private String contents;

    public Integer getId()  {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }
}