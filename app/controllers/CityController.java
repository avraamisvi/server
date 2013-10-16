package controllers;

import java.util.List;

import model.City;

import org.bson.types.ObjectId;
import org.mongodb.morphia.Morphia;

import play.Logger;
import play.libs.F.Callback;
import play.libs.F.Callback0;
import play.mvc.Controller;
import play.mvc.WebSocket;
import repository.CityMorphiaRepository;
import repository.UtilRepository;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.Mongo;

import enums.EmperiumConstants;

public class CityController extends Controller{

	
	public static WebSocket<JsonNode> map() {
		  return new WebSocket<JsonNode>() {
			  
		    public void onReady(WebSocket.In<JsonNode> in, final WebSocket.Out<JsonNode> out) {
		    	
		        // For each event received on the socket,
		        in.onMessage(new Callback<JsonNode>() {
					
					@Override
					public void invoke(JsonNode message) throws Throwable {						
						try {
							
							String name = message.get("name").asText();
							if(name.equalsIgnoreCase(EmperiumConstants.SHOW_MAP_MESSAGE_NAME)) {
								getCityMap(message, out);
							}
						} catch (Exception e) {
							e.printStackTrace();
							
							ObjectMapper mapper = new ObjectMapper();
							out.write(mapper.valueToTree("erro"));
						}	
						
					}
				});
		        
		        // When the socket is closed.
		        in.onClose(new Callback0() {
					
					@Override
					public void invoke() throws Throwable {
						// TODO Auto-generated method stub
						
					}
				});		    	
		    	
		    }
		  };
	}

	private static void getCityMap(JsonNode message, final WebSocket.Out<JsonNode> out) throws Exception {
		Logger.info("SHOWMAP");
		
//		ObjectId id = new ObjectId(message.get("id").asText());
		
		Class.forName("model.city.House");
		
		Mongo mgc = UtilRepository.getMongoClient();
		Morphia mor = UtilRepository.getMorphia();
		
		CityMorphiaRepository repo = new CityMorphiaRepository(mgc, mor);
		
		List<ObjectId>ids = repo.findIds();
		City city = null;
		for(ObjectId id : ids) {
			 city = repo.get(id);
		}
		
		Logger.debug("FOUND CITY:" + city.getId());
		
		ObjectMapper mapper = new ObjectMapper();
		out.write(mapper.valueToTree(city));
		
		/*
		 * 
		 * //		ObjectId id = new ObjectId(message.get("id").asText());
		
		Mongo mgc = UtilRepository.getMongoClient();
//		Morphia mor = UtilRepository.getMorphia();
//		
//		CityMorphiaRepository repo = new CityMorphiaRepository(mgc, mor);
//		
//		List<ObjectId>ids = repo.findIds();
//		City city = null;
//		for(ObjectId id : ids) {
//			 city = repo.get(id);
//		}
//		Logger.debug("FOUND CITY:" + city.getId());
		
		DBCursor cur = mgc.getDB("empire").getCollection("City").find();
		DBObject dbo = cur.next();
		
		if(dbo != null) {		
			ObjectMapper mapper = new ObjectMapper();
			out.write(mapper.valueToTree(dbo));
		}
		 *
		 */
	}
}
