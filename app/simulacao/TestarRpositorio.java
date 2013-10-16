package simulacao;

import model.City;
import repository.CityRepository;
import repository.Repository.Cursor;

public class TestarRpositorio {
	public static void main(String[] args) throws Exception {
		CityRepository repo = new CityRepository("empire");
		
		repo.save(new City());
		
		Cursor<City> cursor = repo.find();
		
		System.out.println(cursor.count());
		
		City city = cursor.next();
		System.out.println(city.getId());
		
	}
	
	public static interface MyInterface {
		void execute();
	}	
}
