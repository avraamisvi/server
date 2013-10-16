package repository;

import java.lang.reflect.Field;
import java.util.List;

import org.bson.types.ObjectId;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.Mongo;
import com.mongodb.MongoClient;

public abstract class Repository <T>{
	
	private Mongo mongoClient;
	private String dbname;
	private String colName;
	
	public Repository(String dbname, String collection) throws Exception {
		mongoClient = UtilRepository.getMongoClient();
		this.dbname = dbname;
		this.colName = collection;
	}
	
	public Mongo getMongoClient() {
		return mongoClient;
	}
	
	public DB getDB() {
		return mongoClient.getDB(dbname);
	}
	
	public DBCollection getDBCollection() {
		return getDB().getCollection(colName);
	}
	
	public Cursor<T> find() {
		Query query = new Query(this.getDBCollection());
		return new Cursor<T>(query.find("{}", null), this);
	}
	
	public Cursor<T> find(String query) {
		Query qry = new Query(this.getDBCollection());
		return new Cursor<T>(qry.find(query, null), this);
	}	
	
	public T findById(ObjectId id) {
		BasicDBObject dbo = new BasicDBObject();
		dbo.append("_id", id);
		return createItem(getDBCollection().findOne(dbo));
	}	
	
	public T save(T dto) {
		DBObject obj = createDBObject(dto);
		if(obj.get("_id") == null) {
			getDBCollection().save(obj);
		} else {
			DBObject qry = new BasicDBObject();
			qry.put("_id", obj.get("_id"));
			getDBCollection().update(qry, obj, false, true);
		}
		return dto;
	}
	
//	public void fillDBO(DBObject dbo, Class<T> clazz) {
//		Field[] flds = clazz.getFields();
//		for(Field fd : flds) {
//			fd.setAccessible(true);
//			if(fd.getType().isInstance(List.class)) {
//				
//			} else if(fd.getType().isInstance(List.class)) {
//				
//			} else if(fd.getType().isInstance(.class)) {
//				
//			}
//			//dbObject.put(fd.getName(), fd.get(obj));
//		}		
//	}	
	
	public abstract T createItem(DBObject dbObj);
	public abstract DBObject createDBObject(T dto);
	
	public static class Cursor<J> {
		
		private DBCursor cursor;
		private Repository<J> repo;
		
		public Cursor(DBCursor cursor, Repository<J> repo) {
			this.cursor = cursor;
			this.repo = repo;
		}
		
		public J next() {
			return repo.createItem(cursor.next());
		}
		
		public int count() {
			return cursor.count();
		}
	}
}
