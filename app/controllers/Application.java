package controllers;

import com.ctg.easypolling.chart.ChartProvider;
import com.ctg.easypolling.chart.ChartProviderFactory;
import models.Poll;
import models.User;
import notifiers.Notifier;
import play.Logger;
import play.data.validation.Email;
import play.data.validation.Equals;
import play.data.validation.MinSize;
import play.data.validation.Required;
import play.mvc.Before;
import play.mvc.Controller;
import play.mvc.With;

import java.util.Map;
import java.util.Set;
import java.net.URL;
import java.util.List;


public class Application extends Controller {

    /** count of recent polls displayed */
    private static final int RECENT_POLLS_DISPLAYED_COUNT = 3;

    // ~~~~~~~~~~~~ @Before interceptors

    @Before
    static void globals() {
        renderArgs.put("connected", connectedUser());
    }

//    @Before(only = {"xxx"})
//    static void checkSecure() {
//        if (connectedUser() == null) {
//            System.out.println("forbid");
//            forbidden();
//        }
//    }
	
    public static void index()
    {
        List<Poll> polls = Poll.findRecentPolls(RECENT_POLLS_DISPLAYED_COUNT);

        long totalPollCount = Poll.count();

        Poll selectedPoll = polls.get(0);

		render(polls, totalPollCount, selectedPoll);
    }

    public static void showDetailedPoll(long id)
    {

        Poll poll = Poll.findById(id);
        System.out.println(poll);
		render(poll);
    }


    public static void showChart(long pollId)
    {

        ChartProviderFactory factory = new ChartProviderFactory();
        ChartProvider chartProvider = factory.createProvider();

        URL chartUrl = chartProvider.createChart(Poll.<Poll>findById(pollId));

        render(chartUrl);
    }

    public static void polls() {
        render();
    }
    
    public static void createpoll() {
        render();
    }

    public static void showPoll(Long id) {
        Poll poll = Poll.findById(id);
    }

    public static void signup() {
        render();
    }

    public static void register(@Required @Email String email, @Required @MinSize(5) String password, @Equals("password") String password2, @Required String name) {
        if (validation.hasErrors()) {
            validation.keep();
            params.flash();
            flash.error("Please correct these errors !");
            signup();
        }
        //does the email already exists?
        if(User.findByEmail(email) != null) {
            flash.error("Oops ... (the email already exists)");
            signup();
        }

        User user = new User(email, password, name).save();
        try {
            if (Notifier.welcome(user)) {
                flash.success("Your account is created. Please check your emails ...");
                login();
            }
        } catch (Exception e) {
            Logger.error(e, "Mail error");
        }
        flash.error("Oops ... (the email cannot be sent)");
        login();
    }

    public static void login() {
        render();
    }

    public static void confirmRegistration(String uuid) {
        User user = User.findByRegistrationUUID(uuid);
        notFoundIfNull(user);
        user.needConfirmation = null;
        user.save();
        flash.success("Welcome %s !", user.fullname);
        login();
    }

    public static void authenticate(String email, String password) {
        User user = User.findByEmail(email);
        if (user == null || !user.checkPassword(password)) {
            flash.error("Bad email or bad password");
            flash.put("email", email);
            login();
        } else if (user.needConfirmation != null) {
            flash.error("This account is not confirmed");
            flash.put("notconfirmed", user.needConfirmation);
            flash.put("email", email);
            login();
        }
        connect(user);
        flash.success("Welcome back %s !", user.fullname);
        index();
    }

    public static void logout() {
        flash.success("You've been logged out");
        session.clear();
        index();
    }

    public static void resendConfirmation(String uuid) {
        User user = User.findByRegistrationUUID(uuid);
        notFoundIfNull(user);
        try {
            if (Notifier.welcome(user)) {
                flash.success("Please check your emails ...");
                flash.put("email", user.email);
                login();
            }
        } catch (Exception e) {
            Logger.error(e, "Mail error");
        }
        flash.error("Oops (the email cannot be sent)...");
        flash.put("email", user.email);
        login();
    }

    // ~~~~~~~~~~~~ Some utils

    static void connect(User user) {
        session.put("logged", user.id);
    }

    static User connectedUser() {
        String userId = session.get("logged");
        return userId == null ? null : (User) User.findById(Long.parseLong(userId));
    }
    
}