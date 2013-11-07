package repository;

import model.City;

import org.bson.types.ObjectId;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.dao.BasicDAO;



public class CityMorphiaRepository extends BasicDAO<City, ObjectId>{

	public CityMorphiaRepository(Datastore store) {
//		super(mongo, morphia, "empire");
		super(store);
	}
	
}
