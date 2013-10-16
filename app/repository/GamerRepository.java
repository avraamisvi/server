package repository;

import model.Gamer;

import org.bson.types.ObjectId;
import org.mongodb.morphia.Morphia;
import org.mongodb.morphia.dao.BasicDAO;

import com.mongodb.Mongo;

public class GamerRepository extends BasicDAO<Gamer, ObjectId>{

	public GamerRepository(Mongo mongo, Morphia morphia) {
		super(mongo, morphia, "empire");
	}
	
}
