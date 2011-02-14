import models.Answer;
import models.Poll;
import models.Proposition;
import models.User;

import org.junit.Test;

import play.test.UnitTest;

import java.sql.Date;

public class BasicTest extends UnitTest {

    @Test
    public void createAndRetrieveUser() {
        // Create a new user and save it
        new User("bob@ctg.com", "secret", "Bob").save();

        // Retrieve the user with e-mail address bob@gmail.com
        User bob = User.find("byEmail", "bob@ctg.com").first();

        // Test
        assertNotNull(bob);
        assertEquals("Bob", bob.fullname);

        //cleanup
        bob.delete();
    }

    @Test
    public void tryConnectAsUser() {
        // Test
        assertNotNull(User.connect("alexis.paris@ctg.com", "secret"));
        assertNull(User.connect("alexis.paris@ctg.com", "badpassword"));
        assertNull(User.connect("thomass.henrion@ctg.com", "secret"));
    }

    @Test
    public void createAndRetrievePoll() {
        // Retrieve a user
        User tom = User.find("byEmail", "thomas.henrion@ctg.com").first();

        // Create a new poll and save it
        new Poll("first poll", false, new Date(1), tom, "no comment").save();

        //retrieve the Poll by its question
        Poll poll = Poll.find("byQuestion", "first poll").first();

        // Test
        assertNotNull(poll);
        assertEquals("first poll", poll.question);

        //cleanup
        poll.delete();
    }

    @Test
    public void createAndRetrieveProposition() {
        // Retrieve a user
        User tom = User.find("byEmail", "thomas.henrion@ctg.com").first();

        // Create a new poll and save it
        Poll poll = new Poll("first poll", false, new Date(1), tom, "no comment").save();

        //Create a new proposition and save it
        new Proposition("first prop", new Date(1), poll).save();

        //retrieve the proposition by its value
        Proposition prop = Proposition.find("byValue", "first prop").first();

        // Test
        assertNotNull(prop);
        assertEquals("first prop", prop.value);

        //cleanup
        prop.delete();
        poll.delete();
    }

    @Test
    public void createAndRetrieveAnswer() {
        // Retrieve a user
        User tom = User.find("byEmail", "thomas.henrion@ctg.com").first();

        // Create a new poll and save it
        Poll poll = new Poll("first poll", false, new Date(1), tom, "no comment").save();

        //Create a new proposition and save it
        Proposition prop = new Proposition("first prop", new Date(1), poll).save();

        new Answer(tom, prop, poll, true, new Date(1), null).save();

        Answer answer = Answer.find("byPoll", poll).first();

        // Test
        assertNotNull(answer);
        assertEquals("first poll", answer.poll.question);

        //cleanup
        answer.delete();
        prop.delete();
        poll.delete();
    }

}
