package model.city;

import model.City;
import model.Gamer;
import enums.ConstructionType;
import enums.EmperiumConstants;
import enums.OwnerType;

public class FiremanPost extends Construction{
	
	public FiremanPost() {
		type = ConstructionType.FiremanPost;
		ownerType = OwnerType.national;
		conservationCost = EmperiumConstants.FIREMAN_CONSERVATION_COST;
		avaliableJobs = 4;
		working=true;
		timeToBuild = EmperiumConstants.FIREMAN_SECONDS_TO_BUILD;
	}

	@Override
	protected void updateHandle(Gamer gamer, City city) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public boolean acceptEmploee(Gamer gamer, City city, Citizen citizen) {
		
		switch(citizen.familyClass) {
			case D:
				return true;
			case C:
				return true;				
			default:
		}
		
		return false;
	}	
}
