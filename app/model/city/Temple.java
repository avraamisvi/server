package model.city;

import enums.ConstructionType;
import enums.ReligionType;
import model.City;
import model.Gamer;

public class Temple extends Construction{
	
	public Temple() {
		type = ConstructionType.Temple;
	}
	public ReligionType religionType;
	@Override
	protected void updateHandle(Gamer gamer, City city) {
		// TODO Auto-generated method stub
		
	} 
}
