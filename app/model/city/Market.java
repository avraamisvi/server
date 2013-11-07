package model.city;

import model.City;
import model.Gamer;
import enums.ConstructionType;
import enums.EmperiumConstants;
import enums.OwnerType;

public class Market  extends Construction {

	int production;
	int produced;
	long gain;
	
	public Market() {
		type = ConstructionType.Market;
		ownerType = OwnerType.enterprise;
		conservationCost = EmperiumConstants.INDUSTRY_CONSERVATION_COST;
		avaliableJobs = 3;		
		production = 8;
		working=true;
		timeToBuild = EmperiumConstants.INDUSTRY_SECONDS_TO_BUILD;
	}
	
	public int getLevel(Gamer gamer) {
		return 1;
	}
	
	private int getMaxProduction(Gamer gamer) {
//		int level = getLevel(gamer);
//		
//		if(level == 1) {
//			return 8;
//		} else if(level == 2) {
//			return 120;
//		} else if(level == 3) {
//			return 360;
//		}
//		
		return 8;
	}
	
	@Override
	protected void updateHandle(Gamer gamer, City city) {
		int force = employed.size()*100/avaliableJobs;
		produced = (force*getMaxProduction(gamer))/100;
		
		int clay = gamer.getClay();
		
		if(clay > 0 && clay < produced) {
			produced = clay;
		} else if(clay <= 0) {
			produced = 0;
			return;
		}		
				
		city.increaseCeramics(produced);
		
	}
	
	protected void calculateSavingsTaxes(Gamer gamer, City city) {		
		
		gain = produced * EmperiumConstants.CERAMIC_PRICE; 
		
		long tax = (city.getIndustryTax()*gain)/100;
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

	public int getProduction() {
		return production;
	}

	public void setProduction(int production) {
		this.production = production;
	}

	public int getProduced() {
		return produced;
	}

	public void setProduced(int produced) {
		this.produced = produced;
	}
	
	@Override
	public void printDebug() {
		super.printDebug();
		System.out.println("Gain:" + gain);
	}

}
