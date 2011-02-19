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

	@Before
	public static void init(){
		if(Security.isConnected()) {
			User user = User.find("byEmail", Security.connected()).first();
            renderArgs.put("user", user.fullname);
        }
	}
	
    public static void index()
    {
        List<Poll> polls = Poll.findRecentPolls(RECENT_POLLS_DISPLAYED_COUNT);

        long totalPollCount = Poll.count();

		render(polls, totalPollCount);
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
    
    public static void subscribe() {
        System.out.println("in subscribe");
        render();
    }

    public static void register(@Required @Email String email, @Required @MinSize(5) String password, @Equals("password") String password2, @Required String fullname) {
        System.out.println("in register");
        if (validation.hasErrors()) {
            validation.keep();
            params.flash();
            flash.error("Please correct these errors !");
            Map map =  validation.errorsMap();
            Set set = map.entrySet();
            for(Object obj:set){
             System.out.println(obj);
            }
            subscribe();
        }
        User user = new User(email, password, fullname).save();
        System.out.println(user.toString());
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

    public static void confirmRegistration(String uuid) {
        User user = User.findByRegistrationUUID(uuid);
        notFoundIfNull(user);
        user.needConfirmation = null;
        user.save();
        flash.success("Welcome %s !", user.fullname);
        index();
    }

    public static void login() {
        System.out.println("in login");
        try {
            Secure.login();
        } catch (Throwable throwable) {
            throwable.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }
    
    public static void createpoll() {
        render();
    }
    
}