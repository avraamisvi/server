package model.city;

import model.City;
import model.Gamer;
import enums.ConstructionType;

public class Mansion extends Construction{
	
	public Mansion() {
		type = ConstructionType.Mansion;
	}
	
	@Override
	protected void updateHandle(Gamer gamer, City city) {
		// TODO Auto-generated method stub
		
	}	
}
