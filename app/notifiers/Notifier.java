package notifiers;

import models.User;
import play.mvc.Mailer;

import javax.mail.internet.InternetAddress;

public class Notifier extends Mailer {

    public static boolean welcome(User user) throws Exception {
        setFrom(new InternetAddress("admin@easypolling.com", "Administrator"));
        setReplyTo(new InternetAddress("help@easypolling.com", "Help"));
        setSubject("Welcome %s", user.fullname);
        addRecipient(user.email, new InternetAddress("new-users@easypolling.com", "New users notice"));
        return sendAndWait(user);
    }
    
}

