
import akka.actor.Props;
import jobs.CityEconomyJob;
import play.*;
import play.libs.Akka;

public class Global extends GlobalSettings {
	  @Override
	  public void onStart(Application app) {
	  	Logger.info("INICIANDO O PLAY");
    	//CityEconomyJob.IniciarSchedule();
	  	
//	  	Akka.system().actorOf(Props.create(CityEconomyJob.class), "string");
	  }  
	  
	  @Override
	  public void onStop(Application app) {
	    Logger.info("Application shutdown...");
	  } 
}
