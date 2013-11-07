package repository;

import model.Gamer;

import org.bson.types.ObjectId;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.dao.BasicDAO;

public class GamerRepository extends BasicDAO<Gamer, ObjectId>{

	public GamerRepository(Datastore store) {
		super(store);
	}
	
}
