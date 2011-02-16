package models;

import play.data.validation.Required;
import play.db.jpa.Model;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import java.util.Date;

/**
 *
 */
@Entity
public class Proposition extends Model {

    @Required
    public String value;

    public Date dateCreation;

    @ManyToOne
    public Poll poll;


    public Proposition(String value, Date dateCreation, Poll poll) {
        this.value = value;
        this.dateCreation = dateCreation;
        this.poll = poll;
    }
}
