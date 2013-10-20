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
		avaliableJobs = 4;
	}

	@Override
	protected void updateHandle(Gamer gamer, City city) {
		
	}
	
	@Override
	public boolean acceptEmploee(Gamer gamer, City city, Citizen citizen) {
		
		switch(citizen.familyClass) {
			case D:
				return true;
			case C:
				return true;
			case B:
				return true;				
			default:
		}
		
		return false;
	}	
}
