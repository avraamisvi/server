package model.city;

import model.City;
import model.Gamer;
import enums.ConstructionType;
import enums.EmperiumConstants;
import enums.OwnerType;

public class PolicePost extends Construction{
	
	public PolicePost() {
		type = ConstructionType.PolicePost;
		conservationCost = EmperiumConstants.POLICE_CONSERVATION_COST;
		ownerType = OwnerType.national;
		avaliableJobs = 4;
		working=true;
		timeToBuild = EmperiumConstants.POLICE_SECONDS_TO_BUILD;
	}

	@Override
	protected void updateHandle(Gamer gamer, City city) {
		System.out.println("Police HandleUpdate");
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
