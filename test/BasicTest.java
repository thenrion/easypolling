import org.junit.*;
import java.util.*;
import play.test.*;
import models.*;

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

}
