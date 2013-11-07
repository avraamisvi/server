package model.city;

import model.City;
import model.Gamer;
import enums.ConstructionType;
import enums.EmperiumConstants;
import enums.GoodsTypeProduction;
import enums.OwnerType;

public class Farm extends Construction {

	GoodsTypeProduction typeProduction;
	int production;
	int produced;
	long gain;
	
	public Farm() {
		type = ConstructionType.Farm;
		ownerType = OwnerType.enterprise;
		conservationCost = EmperiumConstants.FARM_CONSERVATION_COST;
		avaliableJobs = 8;		
		typeProduction = GoodsTypeProduction.rice;
		production = 36;
		working=true;
		timeToBuild = EmperiumConstants.FARM_SECONDS_TO_BUILD;
	}
	
	private int getMaxProduction(Gamer gamer) {
		int level = gamer.getFarmLevel();
		
		if(level == 1) {
			return 36;
		} else if(level == 2) {
			return 120;
		} else if(level == 3) {
			return 360;
		}
		
		return 36;
	}	
	
	@Override
	protected void updateHandle(Gamer gamer, City city) {
		int force = employed.size()*100/avaliableJobs;
		produced = (force*getMaxProduction(gamer))/100;
		
		switch (typeProduction) {
			case rice:
				city.increaseRice(produced);
				break;
				
			case beans:
				city.increaseBeans(produced);
				break;
				
			case meat:
				city.increaseMeat(produced);
				break;
				
			case cotton:
				city.increaseCotton(gamer, produced);
				break;
				
			case grapes:
				city.increaseCotton(gamer, produced);
				break;
				
			default:
				break;
		}
		
	}
	
	protected void calculateSavingsTaxes(Gamer gamer, City city) {		
		
		gain = 0;
		
		switch (typeProduction) {
			case rice:
				gain = produced * city.getRicePrice();
				break;
				
			case beans:
				gain = produced * city.getBeansPrice();
				break;
				
			case meat:
				gain = produced * city.getMeatPrice();
				break;
				
			case cotton:
				gain = produced * city.getCottonPrice();
				break;
				
			case grapes:
				gain = produced * city.getGrapesPrice();
				break;
				
			default:
				break;
		}
		
		long tax = (city.getIndustryTax()*gain)/100;
		gain = gain-tax;
		
		savings+=gain;
		
		city.increaseTreasure(tax);
		gamer.increaseTreasure(tax);		
	}
	
	@Override
	public boolean acceptEmploee(Gamer gamer, City city, Citizen citizen) {
		
		if(typeProduction.equals(GoodsTypeProduction.rice)) {
			switch(citizen.familyClass) {
				case D:
					return true;
				default:
			}
		} else {
			switch(citizen.familyClass) {
				case D:
					return true;
				case C:
					return true;
				default:
			}
		}
		
		return false;
	}

	public GoodsTypeProduction getTypeProduction() {
		return typeProduction;
	}

	public void setTypeProduction(GoodsTypeProduction typeProduction) {
		this.typeProduction = typeProduction;
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
