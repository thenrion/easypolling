package models;

import play.data.validation.Required;
import play.db.jpa.Model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.sql.Date;
import java.util.List;

@Entity
public class Poll extends Model {

    @Required
    public String question;

    @Required
    public Boolean multipleChoice;

    public Date dateCreation;

    @ManyToOne
    public User author;

    public String comment;

    @OneToMany(mappedBy = "poll", cascade = CascadeType.ALL)
    public List<Proposition> propositions;

    @OneToMany(mappedBy = "poll", cascade = CascadeType.ALL)
    public List<Answer> answers;


    public Poll(String question, Boolean multipleChoice, Date dateCreation, User author, String comment) {
        this.question = question;
        this.multipleChoice = multipleChoice;
        this.dateCreation = dateCreation;
        this.author = author;
        this.comment = comment;
    }
}
