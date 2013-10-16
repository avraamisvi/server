package repository;

import java.lang.reflect.Field;
import java.util.List;

import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Id;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

import model.City;
import model.city.CityMap;
import model.city.Construction;



public class CityRepository extends Repository<City>{

	public CityRepository(String dbname) throws Exception {
		super(dbname, "city");
	}

	@Override
	public City createItem(DBObject dbObj) {
		
		City city = new City();
		city.setId((ObjectId) dbObj.get("_id"));
		
		return city;
	}

	@Override
	public DBObject createDBObject(City dto) {
		DBObject dbObject = new BasicDBObject();
		
		if(dto.getId() != null) {
			dbObject.put("_id", dto.getId());
		}
				
//		dbObject.put("GamerId", dto.getGamerId()                );
//		dbObject.put("Name", dto.getName()                   );
//		dbObject.put("MaxPopulation", dto.getMaxPopulation()          );
//		dbObject.put("Population", dto.getPopulation()             );
//		dbObject.put("beans", dto.getPopulationExceded()      );
//		dbObject.put("beans", dto.getTreasureContribution()   );
//		dbObject.put("beans", dto.getTreasureExpenses()       );
//		dbObject.put("beans", dto.getIndustryTax()            );
//		dbObject.put("beans", dto.getCitizenTax()             );
//		dbObject.put("beans", dto.getUneployed()              );
//		dbObject.put("beans", dto.getJobs()                   );
//		dbObject.put("beans", dto.getEntertainmentPrice()     );
//		dbObject.put("beans", dto.getWinePrice()              );
//		dbObject.put("beans", dto.getFurniturePrice()         );
//		dbObject.put("beans", dto.getMeatPrice()              );
//		dbObject.put("beans", dto.getBeansPrice()             );
//		dbObject.put("beans", dto.getRicePrice()              );
//		dbObject.put("beans", dto.getClothsPrice()	          );
//		dbObject.put("beans", dto.getEntertainment()          );
//		dbObject.put("beans", dto.getWine()                   );
//		dbObject.put("beans", dto.getFurniture()              );
//		dbObject.put("beans", dto.getMeat()                   );
//		dbObject.put("beans", dto.getBeans()                  );
//		dbObject.put("beans", dto.getRice()                   );
//		dbObject.put("beans", dto.getCloths()	              );
//		dbObject.put("beans", dto.getProductionEntertainment());
//		dbObject.put("beans", dto.getProductionWine()         );
//		dbObject.put("beans", dto.getProductionFurniture()    );
//		dbObject.put("beans", dto.getProductionMeat()         );
//		dbObject.put("beans", dto.getProductionBeans()        );
//		dbObject.put("beans", dto.getProductionRice()         );
//		dbObject.put("beans", dto.getProductionCloths()		  );
//		dbObject.put("beans", dto.getDemandEntertainment()    );
//		dbObject.put("beans", dto.getDemandWine()             );
//		dbObject.put("beans", dto.getDemandFurniture()        );
//		dbObject.put("beans", dto.getDemandMeat()             );
//		dbObject.put("beans", dto.getDemandBeans()            );
//		dbObject.put("beans", dto.getDemandRice()             );
//		dbObject.put("beans", dto.getDemandCloths()           );
//		dbObject.put("beans", dto.getSalaryAA()               );
//		dbObject.put("beans", dto.getSalaryA()                );
//		dbObject.put("beans", dto.getSalaryBA()               );
//		dbObject.put("beans", dto.getSalaryB()                );
//		dbObject.put("beans", dto.getSalaryC()                );
//		dbObject.put("beans", dto.getSalaryD()                );
		
		getDBOCityMap(dto.getMap());	
		List<Construction> constructions;
		
//		dbObject.put("beans", dto.getBeans());
		
		return dbObject;
	}
	
	private DBObject getDBOCityMap(CityMap cityMap) {
		DBObject obj = new BasicDBObject();
		return obj;
	}
	
	private DBObject getDBOCityMap(Construction construction) {
		DBObject obj = new BasicDBObject();
		return obj;
	}
	
}
