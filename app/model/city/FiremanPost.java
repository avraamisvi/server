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
		avaliableJobs = 4;
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
