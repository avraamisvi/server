package controllers;

import java.util.List;

import model.City;
import model.Gamer;
import model.city.CityMap;
import model.city.ClothsFabric;
import model.city.Construction;
import model.city.Farm;
import model.city.FiremanPost;
import model.city.House;
import model.city.MapPositionPoint;
import model.city.PolicePost;

import org.bson.types.ObjectId;
import org.mongodb.morphia.Morphia;

import play.Logger;
import play.libs.F.Callback;
import play.libs.F.Callback0;
import play.mvc.Controller;
import play.mvc.WebSocket;
import repository.CityMorphiaRepository;
import repository.UtilRepository;
import services.CityLogicService;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.Mongo;

import controllers.messages.CreateMessage;
import enums.ConstructionType;
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
							} else if(name.equalsIgnoreCase(EmperiumConstants.END_TURN_MESSAGE_NAME)) {
								endTurn(message, out);
							} else if(name.equalsIgnoreCase(EmperiumConstants.CREATE_MESSAGE_NAME)) {
								createConstruction(message, out);
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

	//TODO put this in "services"
	private static City getCity(ObjectId id) throws Exception {
		Mongo mgc = UtilRepository.getMongoClient();
		Morphia mor = UtilRepository.getMorphia();
		
		CityMorphiaRepository repo = new CityMorphiaRepository(mgc, mor);
		
		City city = null;
		city = repo.get(id);
		
		return city;
	}
	
	//TODO put this in "services"
	private static City saveCity(City city) throws Exception {
		Mongo mgc = UtilRepository.getMongoClient();
		Morphia mor = UtilRepository.getMorphia();
		
		CityMorphiaRepository repo = new CityMorphiaRepository(mgc, mor);
		
		repo.save(city);
		
		return city;
	}	
	
	//TODO por enquanto
	private static int getMapPositionId(int col, int row) {
		int ret = 0;
		for(int i = 0; i < row; i++) {
			for(int j = 0; j < col; j++) {
				ret++;
			}
		}
		
		return ret;
	}
	
	//TODO verificar se esta invertido o col e row no mapa
	public static void createConstruction(JsonNode message, final WebSocket.Out<JsonNode> out) throws Exception {
		
		ObjectMapper mapper = new ObjectMapper();		
		CreateMessage msg = mapper.treeToValue(message, CreateMessage.class);
		
		int row = msg.getPosition()[0];
		int col = msg.getPosition()[1];
		Construction constru = null;
		ConstructionType type = msg.getType();
		City city = getCity(new ObjectId(msg.getId()));
		int mapid = getMapPositionId(col, row);
		
		int rows = 0, cols = 0;
		
		switch (type) {
			case House:
				constru = new House();
				rows = 1;
				cols = 1;				
				break;
			case Farm:
				constru = new Farm();
				((Farm)constru).setTypeProduction(msg.getProdType());
				rows = 3;
				cols = 2;				
				break;
			case FiremanPost:
				constru = new FiremanPost();
				rows = 1;
				cols = 1;				
				break;
			case PolicePost:
				constru = new PolicePost();
				rows = 1;
				cols = 1;				
				break;
			case Cloths:
				constru = new ClothsFabric();
				rows = 2;
				cols = 2;
				break;
			default:
				break;
		}
		
		if(constru != null && city != null) {
			
			constru.setMapPositionId(mapid);
//			constru.getMapSlots().add(new MapPositionPoint(row, col));
			
			addSlots(constru.getMapSlots(), rows, cols, row, col);
			
			city.getMap().getConstructions()[row][col] = constru.getMapPositionId();			
			
			city.getConstructions().add(constru);
			saveCity(city);
			getCityMap(message, out);
		}
	}
	
	private static void addSlots(List<MapPositionPoint> maps, int rows, int cols, int row, int col) {
		for(int j=row; j < (row+rows); j++) {
			for(int i=col; i < (col+cols); i++) {
				maps.add(new MapPositionPoint(j, i));
			}
		}
	}
	
	//TODO for test only
	private static void endTurn(JsonNode message, final WebSocket.Out<JsonNode> out) throws Exception {
		Gamer gamer = new Gamer();
		City city;// = new City();
		CityLogicService logic = new CityLogicService(gamer);
		
		Morphia morphia = new Morphia();
		morphia.map(Construction.class).map(City.class).map(CityMap.class);
		Mongo mongo = new Mongo();
		
		CityMorphiaRepository repo = new CityMorphiaRepository(mongo, morphia);
		
			try {
				gamer.resetExpenses();
				gamer.resetGoods();
				
				List<ObjectId>ids = repo.findIds();
				for(ObjectId id : ids) {
					city = repo.get(id);
					logic.processEconomyTaxes(gamer, city);
					repo.save(city);
				}
				
				getCityMap(message, out);
				
			} catch (Exception e) {
				e.printStackTrace();
			}
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
