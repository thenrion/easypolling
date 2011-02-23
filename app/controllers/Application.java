package controllers;

import com.ctg.easypolling.chart.ChartProvider;
import com.ctg.easypolling.chart.ChartProviderFactory;
import models.Poll;
import models.User;
import play.mvc.Before;
import play.mvc.Controller;
import play.mvc.With;

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
    
    public static void subscribe() {
        render();
    }
    
    public static void createpoll() {
        render();
    }

    public static void showPoll(Long id) {
        Poll poll = Poll.findById(id);


    }
    
}