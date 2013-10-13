package simulacao;

import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashSet;

import model.City;
import model.city.CityMap;
import model.city.Construction;
import model.city.Farm;
import model.city.FiremanPost;
import model.city.House;
import model.city.PolicePost;
import model.city.Road;

import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;

import com.mongodb.Mongo;

import enums.Constants;

public class CreateTestBase {
	public static void main(String[] args) throws Exception {
//		DB db = new Mongo().getDB("empire");

		Morphia morphia = new Morphia();
		morphia.map(Construction.class).map(City.class).map(CityMap.class);
		Datastore ds = morphia.createDatastore(new Mongo(), "empire");
		
		ds.save(generateCity());
	}
	
	static class Teste {
		int _id;
		int test;
		String name;
		//List<String> nomes;
		public int get_id() {
			return _id;
		}
		public void set_id(int _id) {
			this._id = _id;
		}
		public int getTest() {
			return test;
		}
		public void setTest(int test) {
			this.test = test;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
	}
	
	public static City generateCity() throws Exception {
		City city = new City();
		city.setName("teste");
		
		CityMap map = new CityMap();
		
		map.setConstructions(new long[Constants.CITY_MAX_ROWS][Constants.CITY_MAX_COLS]);
		
		File fl = new File("map.txt");
		char[] cbuf = new char[(int) fl.length()];
		
		FileReader flr = new FileReader(fl);
		flr.read(cbuf);
		flr.close();
		
		String data = new String(cbuf);
		String lines[] = data.split(";");
		int count = 0;
		
		Farm farm = new Farm();
		HashSet<Construction> construs = new HashSet<Construction>();
		
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
					construs.add(constru);
					map.getConstructions()[i][j] = constru.getMapPositionId();
				}
			}
		}
		
		city.setMap(map);
		city.setConstructions(new ArrayList<Construction>(construs));
		return city;
	}
}
