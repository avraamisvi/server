package model.city;

import model.City;
import model.Gamer;
import enums.ConstructionType;
import enums.EmperiumConstants;
import enums.GoodsTypeProduction;
import enums.OwnerType;

public class WineCellar extends Construction {
	GoodsTypeProduction typeProduction;
	int production;
	int produced;
	long gain;
	
	public WineCellar() {
		type = ConstructionType.WineCellar;
		ownerType = OwnerType.enterprise;
		conservationCost = EmperiumConstants.FURNITURE_CONSERVATION_COST;
		avaliableJobs = 4;
		typeProduction = GoodsTypeProduction.wine;
		production = 48;
		working=true;
		timeToBuild = EmperiumConstants.INDUSTRY_SECONDS_TO_BUILD;
	}
	
	private int getMaxProduction(Gamer gamer) {
		int level = gamer.getIndustryLevel();
		
		if(level == 1) {
			return 48;
		} else if(level == 2) {
			return 120;
		} else if(level == 3) {
			return 360;
		}
		
		return 48;
	}	
	
	@Override
	protected void updateHandle(Gamer gamer, City city) {
		int force = employed.size()*100/avaliableJobs;
		produced = (force*getMaxProduction(gamer))/100;
		
		int grapes = gamer.getGrapes();
		
		if(grapes > 0 && grapes < produced) {
			produced = grapes;
		} else if(grapes <= 0) {
			produced = 0;
			return;
		}
		
		city.decreaseGrapes(gamer, produced);//consume resource
		city.increaseWine(produced);
		
	}
	
	protected void calculateSavingsTaxes(Gamer gamer, City city) {		
		
		gain = 0;
		
		gain = produced * city.getWinePrice();
		
		long tax = (city.getIndustryTax()*gain)/100;
		gain = gain-tax;
		
		savings+=gain;
		
		city.increaseTreasure(tax);
		gamer.increaseTreasure(tax);		
	}
	
	@Override
	public boolean acceptEmploee(Gamer gamer, City city, Citizen citizen) {
		
		if(gamer.getCotton() <= 0) {
			return false;
		}
		
		if(gamer.getResearchs().getIndustryLevel() > 1) {
			switch(citizen.familyClass) {
				case A:
					return true;
				case BA:
					return true;
				case B:
					return true;
				default:
			}
		} else {
			switch(citizen.familyClass) {
				case BA:
					return true;
				case B:
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
