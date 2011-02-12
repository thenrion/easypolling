package controllers;

import models.User;
import play.mvc.*;
@With(Secure.class)
public class Manage extends Controller {

	@Before
	public static void init(){
		if(Security.isConnected()) {
			User user = User.find("byEmail", Security.connected()).first();
            renderArgs.put("user", user.fullname);
        }
	}
	
    public static void index() {
        render();
    }
    
    public static void createPoll(){
    	render();
    }

}
