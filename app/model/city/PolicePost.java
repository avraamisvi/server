package model.city;

import enums.ConstructionType;
import enums.OwnerType;
import model.City;
import model.Gamer;

public class PolicePost extends Construction{
	
	public PolicePost() {
		type = ConstructionType.PolicePost;
		conservationCost = 10;
		ownerType = OwnerType.national;
		generalCost = 1200;
		avaliableJobs = 4;
	}

	@Override
	protected void updateHandle(Gamer gamer, City city) {
		
	}
}
