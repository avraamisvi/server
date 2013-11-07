package model.city;

import model.City;
import model.Gamer;
import enums.ConstructionType;

public class Palace extends Construction{
	
	public Palace() {
		type = ConstructionType.Palace;
	}

	@Override
	protected void updateHandle(Gamer gamer, City city) {
		// TODO Auto-generated method stub
		
	}	
}
