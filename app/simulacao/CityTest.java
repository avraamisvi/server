package simulacao;

import java.util.List;

import model.City;
import model.Gamer;
import model.city.CityMap;
import model.city.Construction;

import org.bson.types.ObjectId;
import org.mongodb.morphia.Morphia;
import org.mongodb.morphia.query.UpdateOperations;

import repository.CityMorphiaRepository;
import services.CityLogicService;

import com.mongodb.Mongo;

public class CityTest {
	public static void main(String[] args) throws Exception {
		
		boolean exit = false;
		Gamer gamer = new Gamer();
		City city;// = new City();
		CityLogicService logic = new CityLogicService(gamer);
		int count = 0;
		
		Morphia morphia = new Morphia();
		morphia.map(Construction.class).map(City.class).map(CityMap.class);
		Mongo mongo = new Mongo();
		//Datastore ds = morphia.createDatastore(, "empire");
		
		CityMorphiaRepository repo = new CityMorphiaRepository(mongo, morphia);
		
		while(!exit) {
			try {
				Thread.sleep(1500);
				
				gamer.resetExpenses();
				gamer.resetGoods();
				
				List<ObjectId>ids = repo.findIds();
				for(ObjectId id : ids) {
					city = repo.get(id);
					logic.processEconomyTaxes(city);
					repo.save(city);
				}
				
				System.out.println("Test: " + count);
				System.out.println("Gamer Treasure: " + gamer.getTreasure());
				System.out.println("Gamer Expenses: " + gamer.getTotalExpenses());
				
				count++;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
