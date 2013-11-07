package repository;

import model.City;
import model.city.CityMap;
import model.city.Construction;
import model.city.House;

import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;

import com.mongodb.Mongo;

public class UtilRepository {
	
	private static Mongo mongoClient;
	private static Morphia morphia;
	private static Datastore store;
	
	public static Morphia getMorphia() {
		
		if(morphia == null) {
			morphia = new Morphia();
			morphia.map(Construction.class).map(City.class).
			map(CityMap.class).map(House.class);
		}
		
		return morphia;
	}
	
	public static Mongo getMongoClient() throws Exception {
		if(mongoClient == null)
			mongoClient = new Mongo();
		
		return mongoClient;
	}
	
	public static Datastore getDataStore() throws Exception {
		if(store == null) {
			store = getMorphia().createDatastore(getMongoClient(), "empire");
		}
		
		return store;
	}
}
