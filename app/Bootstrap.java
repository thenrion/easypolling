import play.*;
import play.jobs.*;
import play.test.*;
 
import models.*;
 
@OnApplicationStart
public class Bootstrap extends Job {
 
    public void doJob(){
        // Check if the database is empty

//        Fixtures.deleteAll();

        if(User.count() == 0)
        {
            Fixtures.load("data.yml");
        }

        // To inspect the in memory database
        org.hsqldb.util.DatabaseManagerSwing.main(new String[] { "--url", "jdbc:hsqldb:mem:playembed", "--noexit" });
    }
 
}