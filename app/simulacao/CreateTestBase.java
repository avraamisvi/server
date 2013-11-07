package simulacao;

import java.io.File;
import java.io.FileReader;

import model.City;
import model.Gamer;
import model.city.CityMap;
import model.city.Construction;
import model.city.Constructions;
import model.city.Farm;
import model.city.FiremanPost;
import model.city.House;
import model.city.MapPositionPoint;
import model.city.PolicePost;
import model.city.Road;

import org.bson.types.ObjectId;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;

import repository.UtilRepository;

import com.mongodb.Mongo;

import enums.EmperiumConstants;
import enums.SoilType;

public class CreateTestBase {
	public static void main(String[] args) throws Exception {
		Morphia morphia = UtilRepository.getMorphia();
		Datastore ds = morphia.createDatastore(new Mongo(), "empire");
		
		Gamer gamer = new Gamer();
		City city = generateCity();
		city.setGamerId((ObjectId) ds.save(gamer).getId());
		ds.save(city);
	}
	
	public static CityMap createMap() throws Exception {
		CityMap map = new CityMap();
		map.setConstructions(new long[EmperiumConstants.CITY_MAX_ROWS][EmperiumConstants.CITY_MAX_COLS]);
		map.setSoil(new char[EmperiumConstants.CITY_MAX_ROWS][EmperiumConstants.CITY_MAX_COLS]);
		
		File fl = new File("ground_map.txt");
		char[] cbuf = new char[(int) fl.length()];
		
		FileReader flr = new FileReader(fl);
		flr.read(cbuf);
		flr.close();
		
		String data = new String(cbuf);
		String lines[] = data.split(";");		
		
		for(int i=0; i < lines.length; i++) {
			String line = lines[i];
			line = line.replace("\n", "");
			String []cols = line.split(",");			
			for(int j=0; j < cols.length; j++) {
				if(cols[j].equals("v")) {
					map.getSoil()[i][j] = 'v';
				} else if(cols[j].equals("s")) {
					map.getSoil()[i][j] = 's';
				} else if(cols[j].equals("r")) {
					map.getSoil()[i][j] = 'r';
				} else if(cols[j].equals("c")) {
					map.getSoil()[i][j] = 'c';
				} else if(cols[j].equals("w")) {
					map.getSoil()[i][j] = 'w';
				}
			}
		}
		
		return map;
	}
	
	public static City generateCity() throws Exception {
		City city = new City();
		city.setName("teste");
		
		CityMap map = createMap();
		
		map.setConstructions(new long[EmperiumConstants.CITY_MAX_ROWS][EmperiumConstants.CITY_MAX_COLS]);
		
		File fl = new File("map.txt");
		char[] cbuf = new char[(int) fl.length()];
		
		FileReader flr = new FileReader(fl);
		flr.read(cbuf);
		flr.close();
		
		String data = new String(cbuf);
		String lines[] = data.split(";");
		int count = 0;
		
		Farm farm = new Farm();
//		HashSet<Construction> construs = new HashSet<Construction>();
		
		Constructions construs = new Constructions();
		
		for(int i=0; i < lines.length; i++) {
			String line = lines[i];
			String []cols = line.split(",");			
			for(int j=0; j < cols.length; j++) {
				count++;
				Construction constru = null;
				if(cols[j].equals("h")) {
					constru = new House();
					constru.setMapPositionId(count);
					construs.add(constru);
				} else if(cols[j].equals("p")) {
					constru = new PolicePost();
					constru.setMapPositionId(count);
					construs.add(constru);
				} else if(cols[j].equals("f")) {
					constru = new FiremanPost();
					constru.setMapPositionId(count);
					construs.add(constru);
				} else if(cols[j].equals("F")) {
					constru = farm;
					if(farm.getMapPositionId() <= 0) {
						constru.setMapPositionId(count);
						construs.add(constru);
					}
				} else if(cols[j].equals("r")) {
					constru = new Road();
					constru.setMapPositionId(count);
					construs.add(constru);
				}
				
				if(constru != null) {
					constru.getMapSlots().add(new MapPositionPoint(i, j));
					map.getConstructions()[i][j] = constru.getMapPositionId();
				}
			}
		}
		
		city.setMap(map);
		city.setConstructions(construs);
		return city;
	}
	
	public static interface MyInterface {
		void execute();
	}
}
