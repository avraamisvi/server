package model.city;

import model.City;
import model.Gamer;
import enums.ConstructionType;
import enums.OwnerType;

public class Road extends Construction{
	public Road() {
		type = ConstructionType.Road;
		ownerType = OwnerType.national;
		generalCost = 0;
		conservationCost = 2;
		timeToBuild = 0;
	}

	@Override
	protected void updateHandle(Gamer gamer, City city) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void printDebug() {
//		super.printDebug();
	}
	
	@Override
	public void updateJobs(Gamer gamer, City city) {
		// TODO Auto-generated method stub
	}
}
