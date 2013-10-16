package repository;

import model.City;

import org.bson.types.ObjectId;
import org.mongodb.morphia.Morphia;
import org.mongodb.morphia.dao.BasicDAO;

import com.mongodb.Mongo;



public class CityMorphiaRepository extends BasicDAO<City, ObjectId>{

	public CityMorphiaRepository(Mongo mongo, Morphia morphia) {
		super(mongo, morphia, "empire");
	}
	
}
