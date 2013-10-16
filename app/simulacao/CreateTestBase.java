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

import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;

import repository.UtilRepository;

import com.mongodb.Mongo;

import enums.EmperiumConstants;

public class CreateTestBase {
	public static void main(String[] args) throws Exception {
		Morphia morphia = UtilRepository.getMorphia();
		Datastore ds = morphia.createDatastore(new Mongo(), "empire");
				
		ds.save(generateCity());
		ds.save(new Gamer());
	}
	
	public static City generateCity() throws Exception {
		City city = new City();
		city.setName("teste");
		
		CityMap map = new CityMap();
		
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
				} else if(cols[j].equals("p")) {
					constru = new PolicePost();
					constru.setMapPositionId(count);
				} else if(cols[j].equals("f")) {
					constru = new FiremanPost();
					constru.setMapPositionId(count);
				} else if(cols[j].equals("F")) {
					constru = farm;
					if(farm.getMapPositionId() <= 0)
						constru.setMapPositionId(count);
				} else if(cols[j].equals("r")) {
					constru = new Road();
					constru.setMapPositionId(count);
				}
				
				if(constru != null) {
					constru.getMapSlots().add(new MapPositionPoint(j, i));
					construs.add(constru);
					map.getConstructions()[j][i] = constru.getMapPositionId();
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
