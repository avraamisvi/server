package model.city;

import enums.ConstructionType;
import enums.OwnerType;
import model.City;
import model.Gamer;

public class Road extends Construction{
	public Road() {
		type = ConstructionType.Road;
		ownerType = OwnerType.national;
		generalCost = 0;
		conservationCost = 2;
	}

	@Override
	protected void updateHandle(Gamer gamer, City city) {
		// TODO Auto-generated method stub
		
	}
}
