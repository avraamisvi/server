package controllers;

import java.util.List;

import model.City;
import model.Gamer;
import model.city.CityMap;
import model.city.Construction;
import model.messages.CreateWarriorReplyMessage;
import model.messages.ErrorReplyMessage;
import model.messages.GamerCityDataMessage;
import model.messages.Message;

import org.bson.types.ObjectId;
import org.mongodb.morphia.Morphia;

import play.Logger;
import play.libs.F.Callback;
import play.libs.F.Callback0;
import play.mvc.Controller;
import play.mvc.WebSocket;
import repository.CityMorphiaRepository;
import repository.GamerRepository;
import repository.UtilRepository;
import services.CityLogicService;
import services.CityService;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.Mongo;

import controllers.messages.CreateMessage;
import controllers.messages.CreateWarriorMessage;
import enums.EmperiumConstants;
import exceptions.InvalidPlaceException;

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
							} else if(name.equalsIgnoreCase(EmperiumConstants.INCREASE_INDUSTRY_LEVEL)) {
								increaseIndustryLevel(message, out);
							} else if(name.equalsIgnoreCase(EmperiumConstants.CREATE_WARRIOR)) {
								createWarrior(message, out);
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

	//TODO verificar se esta invertido o col e row no mapa
	public static void createConstruction(JsonNode message, final WebSocket.Out<JsonNode> out) throws Exception {
		
		ObjectMapper mapper = new ObjectMapper();		
		CreateMessage msg = mapper.treeToValue(message, CreateMessage.class);
		
		try {
			long ret = CityService.createConstruction(msg);
			
			if(ret < 0) {
				sendError("LACK MONEY", ret, out);
			} else {
				getCityMap(message, out);
			}
		} catch(InvalidPlaceException ex) {
			sendError("Tryed to build on an invalid place.", "", out);
		}
	}
	
	public static void createWarrior(JsonNode message, final WebSocket.Out<JsonNode> out) throws Exception {
		
		ObjectMapper mapper = new ObjectMapper();		
		CreateWarriorMessage msg = mapper.treeToValue(message, CreateWarriorMessage.class);
		
		
		try {
			City city = CityService.getCity(new ObjectId(msg.getCityId()));
			Gamer gamer = new GamerRepository(UtilRepository.getDataStore()).get(city.getGamerId());
			CreateWarriorReplyMessage reply = CityService.createWarrior(msg.getType(), msg.getAmount(), gamer, city);
			
			mapper = new ObjectMapper();
			out.write(mapper.valueToTree(reply));
			
		} catch(Exception ex) {
			sendError(ex.getMessage(), "", out);
		}
	}
	
	public static void sendError(String error, Object data, final WebSocket.Out<JsonNode> out) {
		ErrorReplyMessage msg = new ErrorReplyMessage();
		msg.setError(error);
		msg.setData(data);
		ObjectMapper mapper = new ObjectMapper();
		out.write(mapper.valueToTree(msg));
	}
	
	//TODO for test only
	private static void endTurn(JsonNode message, final WebSocket.Out<JsonNode> out) throws Exception {
		
		Gamer gamer = null;
		GamerRepository gRepo = new GamerRepository(UtilRepository.getDataStore());
		
		List<ObjectId>ids = gRepo.findIds();
		for(ObjectId id : ids) {
			gamer = gRepo.get(id);
			break;
		}
		
		City city;// = new City();
		CityLogicService logic = new CityLogicService(gamer);
		
		Morphia morphia = new Morphia();
		morphia.map(Construction.class).map(City.class).map(CityMap.class);
		
		CityMorphiaRepository repo = new CityMorphiaRepository(UtilRepository.getDataStore());
		
			try {
				gamer.resetExpenses();
				gamer.resetGoods();
				
				ids = repo.findIds();
				for(ObjectId id : ids) {
					city = repo.get(id);
					logic.processEconomyTaxes(gamer, city);
					repo.save(city);
				}
				
				gRepo.save(gamer);
				getCityMap(message, out);
				
			} catch (Exception e) {
				e.printStackTrace();
			}
	}
	
	//TODO for test only
	private static void increaseIndustryLevel(JsonNode message, final WebSocket.Out<JsonNode> out) throws Exception {
		
		Gamer gamer = null;
		GamerRepository gRepo = new GamerRepository(UtilRepository.getDataStore());
		
		List<ObjectId>ids = gRepo.findIds();
		for(ObjectId id : ids) {
			gamer = gRepo.get(id);
			break;
		}
		
		try {			
			gamer.getResearchs().setIndustryLevel(gamer.getResearchs().getIndustryLevel()+1);
			gRepo.save(gamer);
			getCityMap(message, out);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private static void getCityMap(JsonNode message, final WebSocket.Out<JsonNode> out) throws Exception {
		
//		Mongo mgc = UtilRepository.getMongoClient();
//		Morphia mor = UtilRepository.getMorphia();
				
		CityMorphiaRepository repo = new CityMorphiaRepository(UtilRepository.getDataStore());
		GamerRepository gamerRepo = new GamerRepository(UtilRepository.getDataStore());
				
		List<ObjectId>ids = repo.findIds();
		City city = null;
		for(ObjectId id : ids) {
			 city = repo.get(id);
		}
		
		Gamer gamer = gamerRepo.get(city.getGamerId());
		
		Logger.debug("FOUND CITY:" + city.getId());
		
		GamerCityDataMessage msg = new GamerCityDataMessage();
		msg.setCity(city);
		msg.setGamer(gamer);
		
		ObjectMapper mapper = new ObjectMapper();
		out.write(mapper.valueToTree(msg));
	}
	
}
