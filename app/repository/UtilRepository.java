package repository;

import model.City;
import model.city.CityMap;
import model.city.Construction;
import model.city.House;

import org.mongodb.morphia.Morphia;

import com.mongodb.DBObject;
import com.mongodb.Mongo;
import com.mongodb.MongoClient;

public class UtilRepository {
	
	private static Mongo mongoClient;
	
	public static Morphia getMorphia() {
		
		Morphia morphia = new Morphia();
		morphia.map(Construction.class).map(City.class).
		map(CityMap.class).map(House.class);
		
		return morphia;
	}
	
	public static Mongo getMongoClient() throws Exception {
		if(mongoClient == null)
			mongoClient = new Mongo();
		
		return mongoClient;
	}
	
}
