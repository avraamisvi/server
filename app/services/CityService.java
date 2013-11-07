package services;

import java.util.List;

import model.City;
import model.Gamer;
import model.city.Airport;
import model.city.ArcherCamp;
import model.city.Bank;
import model.city.Barracks;
import model.city.CityMap;
import model.city.ClothsFabric;
import model.city.Construction;
import model.city.Farm;
import model.city.FiremanPost;
import model.city.FurnitureFabric;
import model.city.House;
import model.city.Infirmary;
import model.city.KnightsCamp;
import model.city.LumberJack;
import model.city.MachineryFactory;
import model.city.Mansion;
import model.city.MapPositionPoint;
import model.city.Market;
import model.city.MinePool;
import model.city.Palace;
import model.city.PolicePost;
import model.city.Road;
import model.city.School;
import model.city.Shipyard;
import model.city.University;
import model.city.WineCellar;
import model.messages.CreateWarriorReplyMessage;

import org.bson.types.ObjectId;

import repository.CityMorphiaRepository;
import repository.GamerRepository;
import repository.UtilRepository;
import controllers.messages.CreateMessage;
import enums.ConstructionType;
import enums.EmperiumConstants;
import enums.WarriorsConstants;
import exceptions.InvalidPlaceException;

public class CityService {

	/**
	 * Create a construction
	 * 
	 * @param msg
	 * @return
	 * @throws Exception
	 */
	public static long createConstruction(CreateMessage msg) throws Exception {
		
		int price = 0;
		
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
			case Road:
				constru = new Road();
				rows = 1;
				cols = 1;				
				break;
			case Cloths:
				constru = new ClothsFabric();
				rows = 2;
				cols = 2;
				break;
			case Mine:
				constru = new MinePool();
				((MinePool)constru).setMineType(msg.getMineType());
				rows = 2;
				cols = 2;
				break;
			case WineCellar:
				constru = new WineCellar();
				rows = 2;
				cols = 2;
				break;	
			case Furniture:
				constru = new FurnitureFabric();
				rows = 2;
				cols = 2;
				break;
				
			case Bank:
				constru = new Bank();
				rows = 2;
				cols = 2;
				break;
				
			case LumberJack:
				constru = new LumberJack();
				rows = 2;
				cols = 2;
				break;
				
			case Barracks:
				constru = new Barracks();
				rows = 2;
				cols = 3;
				break;
				
			case Mansion:
				constru = new Mansion();
				rows = 2;
				cols = 2;
				break;
				
			case University:
				constru = new University();
				rows = 2;
				cols = 3;
				break;
				
			case Palace:
				constru = new Palace();
				rows = 3;
				cols = 3;
				break;
				
			case Market:
				constru = new Market();
				rows = 2;
				cols = 2;
				break;
				
			case ArcherCamp:
				constru = new ArcherCamp();
				rows = 2;
				cols = 2;				
				break;
				
			case Infirmary:
				constru = new Infirmary();
				rows = 2;
				cols = 2;				
				break;
				
			case Knights:
				constru = new KnightsCamp();
				rows = 3;
				cols = 2;
				break;
				
			case Machinery:
				constru = new MachineryFactory();
				rows = 2;
				cols = 2;				
				break;
				
			case School:
				constru = new School();
				rows = 2;
				cols = 2;				
				break;
				
			case Shipyard:
				constru = new Shipyard();
				rows = 2;
				cols = 2;
				break;
				
			case Airport:
				constru = new Airport();
				rows = 3;
				cols = 3;
				break;				
				
			default:
				break;
		}
		
		price = calculatePrice(city.getMap(), row, col, rows, cols);
							
		if(price < 0) {
			throw new InvalidPlaceException();
		}
		
		if(constru != null && city != null) {
			
			GamerRepository gamerRepo = new GamerRepository(UtilRepository.getDataStore());
			Gamer gamer = gamerRepo.get(city.getGamerId());
			
			long ret = gamer.decreaseTreasure(price);
			
			if(ret < 0) {
				return (int) ret;
			}
			
			constru.setMapPositionId(mapid);
			addSlots(constru.getMapSlots(), rows, cols, row, col);
			city.getMap().getConstructions()[row][col] = constru.getMapPositionId();//FIXME			
			city.getConstructions().add(constru);
			saveCity(city);
			saveGamer(gamer);
		}
		
		return price;
	}
	
	public static Gamer saveGamer(Gamer gamer) throws Exception {
		
		GamerRepository repo = new GamerRepository(UtilRepository.getDataStore());
		
		repo.save(gamer);
		
		return gamer;
	}

	/**
	 * It calculates the price to construct in the position based on the soil type
	 * @param map
	 * @param row
	 * @param col
	 * @param rows
	 * @param cols
	 * @return
	 */
	public static int calculatePrice(CityMap map, int row, int col, int rows, int cols) {
		int price = 0;
		int maxr = row+rows;
		int maxc = col+cols;
		
		for(int r = row; r < maxr; r++) {
			for(int c = col; c < maxc; c++) {
				if(map.getSoil()[r][c] == 'v') {
					return -1;
				} else if(map.getSoil()[r][c] == 's') {
					price += EmperiumConstants.SAND_SOIL_PRICE;
				} else if(map.getSoil()[r][c] == 'r') {
					price += EmperiumConstants.ROCK_SOIL_PRICE;
				} else if(map.getSoil()[r][c] == 'c') {
					price += EmperiumConstants.CLAY_SOIL_PRICE;
				} else if(map.getSoil()[r][c] == 'w') {
					return -1;
				}
			}
		}
		
		return price;
	}
	
	/**
	 * Get an id for a position on the map.
	 * @param col
	 * @param row
	 * @return
	 */
	private static int getMapPositionId(int col, int row) {
		int ret = 0;
		for(int i = 0; i < row; i++) {
			for(int j = 0; j < col; j++) {
				ret++;
			}
		}
		
		return ret;
	}	
	
	
	public static boolean haveSlot (City city, int type) {
		switch(type) {
		case WarriorsConstants.SWORDMAN:
		case WarriorsConstants.SPEARMAN:
		case WarriorsConstants.SLING:
		case WarriorsConstants.RIFLEMAN:
			return city.getConstructions().getBarracks().haveSlot();
		case WarriorsConstants.KNIGHT:
			return city.getConstructions().getKnightsCamp().haveSlot();			
		case WarriorsConstants.ARCHER:
			return city.getConstructions().getArcherCamp().haveSlot();			
		case WarriorsConstants.DOCTOR:
			return city.getConstructions().getInfirmary().haveSlot();
		case WarriorsConstants.CATAPULT:
		case WarriorsConstants.CANNON:
			return city.getConstructions().getMachineryFactory().haveSlot();
		case WarriorsConstants.BOAT:
		case WarriorsConstants.CARAVEL:
		case WarriorsConstants.SHIP:
			return city.getConstructions().getShipyard().haveSlot();
		case WarriorsConstants.AIRSHIP:
			return city.getConstructions().getAirport().haveSlot();
		}
		
		return false;
	}
	
	public static CreateWarriorReplyMessage canMakeWarrior(int type, Gamer gamer, City city) {
		
		CreateWarriorReplyMessage ret = new CreateWarriorReplyMessage();
		ret.city = city;
		ret.gamer = gamer;
		
		boolean slot = haveSlot(city, type);
		
		if (!slot) {
			ret.created = false;
			ret.full = true;
		} else {

			switch (type) {
			case WarriorsConstants.SWORDMAN:

				if (gamer.getIron() >= 5) {
					if (gamer.getSulfur() >= 5) {
						gamer.decreaseIron(5);
						gamer.decreaseSulfur(5);
						ret.created = true;
					}
				}
				if (!ret.created) {
					ret.iron = gamer.getIron() - 5;
					ret.sulfur = gamer.getSulfur() - 5;
				}

				break;
			case WarriorsConstants.SPEARMAN:
				if (gamer.getIron() >= 15) {
					if (gamer.getWood() >= 5) {
						gamer.decreaseIron(15);
						gamer.decreaseWood(5);
						ret.created = true;
					}
				}
				if (!ret.created) {
					ret.iron = gamer.getIron() - 15;
					ret.wood = gamer.getWood() - 5;
				}
				break;
			case WarriorsConstants.SLING:
				if (gamer.getRock() >= 10) {
					if (gamer.getCotton() >= 10) {
						gamer.decreaseRock(10);
						gamer.decreaseCotton(5);
						ret.created = true;
					}
				}
				if (!ret.created) {
					ret.rock = gamer.getRock() - 10;
					ret.cotton = gamer.getCotton() - 10;
				}
				break;
			case WarriorsConstants.RIFLEMAN:
				if (gamer.getIron() >= 60) {
					if (gamer.getCotton() >= 35) {
						if (gamer.getSulfur() >= 40) {
							if (gamer.getWood() >= 50) {
								gamer.decreaseIron(60);
								gamer.decreaseSulfur(50);
								gamer.decreaseCotton(35);
								gamer.decreaseWood(50);
								ret.created = true;
							}
						}
					}
				}

				if (!ret.created) {
					ret.iron = gamer.getIron() - 60;
					ret.sulfur = gamer.getSulfur() - 50;
					ret.cotton = gamer.getCotton() - 35;
					ret.wood = gamer.getWood() - 50;
				}
				break;
			case WarriorsConstants.KNIGHT:
				if (gamer.getIron() >= 20) {
					if (gamer.getCotton() >= 20) {
						if (gamer.getWood() >= 10) {
							gamer.decreaseIron(20);
							gamer.decreaseCotton(20);
							gamer.decreaseWood(10);
							ret.created = true;
						}
					}
				}

				if (!ret.created) {
					ret.iron = gamer.getIron() - 20;
					ret.cotton = gamer.getCotton() - 20;
					ret.wood = gamer.getWood() - 10;
				}
				break;
			case WarriorsConstants.ARCHER:
				if (gamer.getIron() >= 20) {
					if (gamer.getWood() >= 20) {
						gamer.decreaseIron(20);
						gamer.decreaseWood(20);
						ret.created = true;
					}
				}

				if (!ret.created) {
					ret.iron = gamer.getIron() - 20;
					ret.wood = gamer.getWood() - 20;
				}
				break;
			case WarriorsConstants.DOCTOR:
				if (gamer.getKnowledge() >= 5) {
					if (gamer.getCotton() >= 35) {
						if (gamer.getWood() >= 50) {
							gamer.decreaseKnowledge(5);
							gamer.decreaseCotton(20);
							gamer.decreaseWood(20);
							ret.created = true;
						}
					}
				}

				if (!ret.created) {
					ret.knowledge = gamer.getKnowledge() - 5;
					ret.cotton = gamer.getCotton() - 20;
					ret.wood = gamer.getWood() - 20;
				}
				break;
			case WarriorsConstants.CATAPULT:
				if (gamer.getIron() >= 30) {
					if (gamer.getSulfur() >= 20) {
						if (gamer.getWood() >= 35) {
							gamer.decreaseIron(30);
							gamer.decreaseSulfur(20);
							gamer.decreaseWood(35);
							ret.created = true;
						}
					}
				}

				if (!ret.created) {
					ret.iron = gamer.getIron() - 30;
					ret.sulfur = gamer.getSulfur() - 20;
					ret.wood = gamer.getWood() - 35;
				}
				break;
			case WarriorsConstants.CANNON:
				if (gamer.getIron() >= 100) {
					if (gamer.getSulfur() >= 90) {
						if (gamer.getWood() >= 60) {
							gamer.decreaseIron(100);
							gamer.decreaseSulfur(90);
							gamer.decreaseWood(60);
							ret.created = true;
						}
					}
				}

				if (!ret.created) {
					ret.iron = gamer.getIron() - 100;
					ret.sulfur = gamer.getSulfur() - 90;
					ret.wood = gamer.getWood() - 60;
				}
				break;
			case WarriorsConstants.BOAT:
				if (gamer.getWood() >= 60) {
					gamer.decreaseIron(100);
					gamer.decreaseSulfur(90);
					gamer.decreaseWood(60);
					ret.created = true;
				}

				if (!ret.created) {
					ret.wood = gamer.getWood() - 60;
				}
				break;
			case WarriorsConstants.CARAVEL:
				if (gamer.getCotton() >= 30) {
					if (gamer.getWood() >= 60) {
						gamer.decreaseIron(30);
						gamer.decreaseWood(60);
						ret.created = true;
					}
				}

				if (!ret.created) {
					ret.cotton = gamer.getCotton() - 30;
					ret.wood = gamer.getWood() - 60;
				}
				break;
			case WarriorsConstants.SHIP:
				if (gamer.getIron() >= 80) {
					if (gamer.getSulfur() >= 90) {
						if (gamer.getWood() >= 60) {
							gamer.decreaseIron(80);
							gamer.decreaseSulfur(90);
							gamer.decreaseWood(60);
							ret.created = true;
						}
					}
				}

				if (!ret.created) {
					ret.iron = gamer.getIron() - 80;
					ret.sulfur = gamer.getSulfur() - 90;
					ret.wood = gamer.getWood() - 60;
				}
				break;
			case WarriorsConstants.AIRSHIP:
				if (gamer.getIron() >= 100) {
					if (gamer.getSulfur() >= 95) {
						if (gamer.getCotton() >= 60) {
							gamer.decreaseIron(100);
							gamer.decreaseSulfur(95);
							gamer.decreaseCotton(60);
							ret.created = true;
						}
					}
				}

				if (!ret.created) {
					ret.iron = gamer.getIron() - 100;
					ret.sulfur = gamer.getSulfur() - 95;
					ret.wood = gamer.getCotton() - 60;
				}
				break;
			}
		}
		return ret;
	}
	
	/**
	 * 
	 * Creates a new warrior.
	 * 
	 * @param type
	 * @param city
	 * @throws Exception 
	 */
	public static CreateWarriorReplyMessage createWarrior(int type, int amount, Gamer gamer, City city) throws Exception {
		CreateWarriorReplyMessage ret = null;
		
		ret = canMakeWarrior(type, gamer, city);
		
		if(ret.created) {
			switch(type) {
			case WarriorsConstants.SWORDMAN:
				city.getConstructions().getBarracks().newSwordMans();
				ret.construction = city.getConstructions().getBarracks();
				break;
			case WarriorsConstants.SPEARMAN:
				city.getConstructions().getBarracks().newSpearMans();
				ret.construction = city.getConstructions().getBarracks();
				break;
			case WarriorsConstants.SLING:
				city.getConstructions().getBarracks().newSlingMans();
				ret.construction = city.getConstructions().getBarracks();
				break;
			case WarriorsConstants.RIFLEMAN:
				city.getConstructions().getBarracks().newRiflemans();
				ret.construction = city.getConstructions().getBarracks();
				break;
			case WarriorsConstants.KNIGHT:
				city.getConstructions().getKnightsCamp().newKnight();
				ret.construction = city.getConstructions().getKnightsCamp();
				break;
			case WarriorsConstants.ARCHER:
				city.getConstructions().getArcherCamp().newArcher();
				ret.construction = city.getConstructions().getArcherCamp();
				break;
			case WarriorsConstants.DOCTOR:
				city.getConstructions().getInfirmary().newDoctor();
				ret.construction = city.getConstructions().getInfirmary();
				break;
			case WarriorsConstants.CATAPULT:
				city.getConstructions().getMachineryFactory().newCatapult();
				ret.construction = city.getConstructions().getMachineryFactory();
				break;			
			case WarriorsConstants.CANNON:
				city.getConstructions().getMachineryFactory().newCannon();
				ret.construction = city.getConstructions().getMachineryFactory();
				break;			
			case WarriorsConstants.BOAT:
				city.getConstructions().getShipyard().newBoat();
				ret.construction = city.getConstructions().getShipyard();
				break;						
			case WarriorsConstants.CARAVEL:
				city.getConstructions().getShipyard().newCaravel();
				ret.construction = city.getConstructions().getShipyard();
				break;						
			case WarriorsConstants.SHIP:		
				city.getConstructions().getShipyard().newShip();
				ret.construction = city.getConstructions().getShipyard();
				break;			
			case WarriorsConstants.AIRSHIP:
				city.getConstructions().getAirport().newAirShip();
				break;			
			}
			
			updateGameInfo(gamer, city);			
			saveCity(city);
			saveGamer(gamer);
		}
		return ret;
	}
	
	private static void updateGameInfo(Gamer gamer, City city) {
		//TODO
	}
	
	private static City saveCity(City city) throws Exception {
		
		CityMorphiaRepository repo = new CityMorphiaRepository(UtilRepository.getDataStore());
		
		repo.save(city);
		
		return city;
	}
	
	/**
	 * Add the positions on the map.
	 * 
	 * @param maps
	 * @param rows
	 * @param cols
	 * @param row
	 * @param col
	 */
	private static void addSlots(List<MapPositionPoint> maps, int rows, int cols, int row, int col) {
		
		int maxr = (row+rows);
		int maxc = (col+cols);
		
		for(int j=row; j < maxr; j++) {
			for(int i=col; i < maxc; i++) {
				maps.add(new MapPositionPoint(j, i));
			}
		}
	}	
	
	public static City getCity(ObjectId id) throws Exception {
		
		CityMorphiaRepository repo = new CityMorphiaRepository(UtilRepository.getDataStore());
		
		City city = null;
		city = repo.get(id);
		
		return city;
	}	
}
