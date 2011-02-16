package models;

import play.db.jpa.Model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.util.Date;
import java.util.List;

@Entity
public class Answer extends Model {

    @ManyToOne
    public User polled;

    @ManyToOne
    public Proposition proposition;

    @ManyToOne
    public Poll poll;

    public Boolean selected;

    public Date dateCreation;

    public Date dateModification;


    public Answer(User polled, Proposition proposition, Poll poll, Boolean selected, Date dateCreation, Date dateModification) {
        this.polled = polled;
        this.proposition = proposition;
        this.poll = poll;
        this.selected = selected;
        this.dateCreation = dateCreation;
        this.dateModification = dateModification;
    }

    /** retrieve all answers corresponding to the given poll
     * @param poll a Poll
     * @return a Collection of Answer
     */
	public static List<Answer> findAnswersOfPoll(Poll poll)
	{
        return Answer.find("poll = ? and selected = ?", poll, Boolean.TRUE).fetch();
	}

}
