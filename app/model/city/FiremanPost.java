package model.city;

import enums.ConstructionType;
import enums.OwnerType;
import model.City;
import model.Gamer;

public class FiremanPost extends Construction{
	
	public FiremanPost() {
		type = ConstructionType.FiremanPost;
		ownerType = OwnerType.national;
		conservationCost = 10;
		generalCost = 1200;
		avaliableJobs = 4;
	}

	@Override
	protected void updateHandle(Gamer gamer, City city) {
		// TODO Auto-generated method stub
		
	}
}
