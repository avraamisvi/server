package model.city;

import model.City;
import model.Gamer;
import enums.ConstructionType;
import enums.EmperiumConstants;
import enums.OwnerType;

public class School extends Construction{
	
	int produced;
	int production;
	int totalProduced = 0;
	int maxProduction = 10;
	
	public School() {
		type = ConstructionType.School;
		ownerType = OwnerType.enterprise;
		conservationCost = EmperiumConstants.SCHOOL_CONSERVATION_COST;
		avaliableJobs = 3;		
		production = 2;
		working=true;
		timeToBuild = EmperiumConstants.SCHOOL_SECONDS_TO_BUILD;		
	}
	
	@Override
	protected void updateHandle(Gamer gamer, City city) {
		int force = employed.size()*100/avaliableJobs;
		produced = (force*2)/100;		
		totalProduced+=produced;
		
		if(totalProduced > maxProduction) {
			gamer.increaseKnowledge(2);
			totalProduced = 0;
		}
	}
	
	protected void calculateSavingsTaxes(Gamer gamer, City city) {		
		
		long gain = produced * EmperiumConstants.KNOWLEDGE_PRICE; 
		
		long tax = (city.getEducationalTax()*gain)/100;
		gain = gain-tax;
		
		savings+=gain;
		
		city.increaseTreasure(tax);
		gamer.increaseTreasure(tax);		
	}
	
	@Override
	public boolean acceptEmploee(Gamer gamer, City city, Citizen citizen) {
		switch(citizen.familyClass) {
			case BA:
				return true;
			default:
		}
		
		return false;
	}		
}
