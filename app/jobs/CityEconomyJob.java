package jobs;

import java.util.List;
import java.util.concurrent.TimeUnit;

import model.City;
import model.Gamer;
import model.city.CityMap;
import model.city.Construction;

import org.bson.types.ObjectId;
import org.mongodb.morphia.Morphia;

import play.Logger;
import play.libs.Akka;
import repository.CityMorphiaRepository;
import repository.GamerRepository;
import repository.UtilRepository;
import scala.concurrent.duration.Duration;
import services.CityLogicService;
import akka.actor.Cancellable;
import akka.actor.UntypedActor;

import com.mongodb.Mongo;

public class CityEconomyJob extends UntypedActor {
	
	private final Cancellable tick = Akka.system().scheduler().schedule(
	        Duration.create(0, TimeUnit.MILLISECONDS), //Initial delay 0 milliseconds
	        Duration.create(1500, TimeUnit.MILLISECONDS),     //Frequency 30 minutes
	        getSelf(),
	        "tick",
	        Akka.system().dispatcher(),
	        null
	);

	@Override
	public void onReceive(Object arg0) throws Exception {
		try {
			Logger.info("RODANDO O JOB");
			Morphia morphia = UtilRepository.getMorphia();		
			Mongo mongo = new Mongo();
			
			GamerRepository gamerRepo = new GamerRepository(mongo, morphia);
			
			List<ObjectId> gamerIds  = gamerRepo.findIds();
			
			for(ObjectId gId : gamerIds) {
					
				Gamer gamer = gamerRepo.get(gId);
				
				CityLogicService logic = new CityLogicService(gamer);
				int count = 0;
				
				
				CityMorphiaRepository repo = new CityMorphiaRepository(mongo, morphia);
				
				try {
					gamer.resetExpenses();
					gamer.resetGoods();
					
					List<ObjectId>ids = repo.findIds();
					for(ObjectId id : ids) {
						City city = repo.get(id);
						logic.processEconomyTaxes(city);
						repo.save(city);
					}
					
//					System.out.println("Test: " + count);
//					System.out.println("Gamer Treasure: " + gamer.getTreasure());
//					System.out.println("Gamer Expenses: " + gamer.getTotalExpenses());
					
					count++;
				} catch (Exception e) {
					e.printStackTrace();
				}
				
				gamerRepo.save(gamer);
			}
		} catch(Exception e) {
			
		}
	}
}
