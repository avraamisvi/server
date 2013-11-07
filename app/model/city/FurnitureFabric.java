package model.city;

import model.City;
import model.Gamer;
import enums.ConstructionType;
import enums.EmperiumConstants;
import enums.GoodsTypeProduction;
import enums.OwnerType;

public class FurnitureFabric extends Construction {
	
	GoodsTypeProduction typeProduction;
	int production;
	int produced;
	long gain;
	
	public FurnitureFabric() {
		type = ConstructionType.Furniture;
		ownerType = OwnerType.enterprise;
		conservationCost = EmperiumConstants.FURNITURE_CONSERVATION_COST;
		avaliableJobs = 4;
		typeProduction = GoodsTypeProduction.furniture;
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
		
		return 8;
	}	
	
	@Override
	protected void updateHandle(Gamer gamer, City city) {
		int force = employed.size()*100/avaliableJobs;
		produced = (force*getMaxProduction(gamer))/100;
		
		int wood = gamer.getWood();
		
		if(wood > 0 && wood < produced) {
			produced = wood;
		} else if(wood <= 0) {
			produced = 0;
			return;
		}
		
		city.decreaseWood(gamer, produced);
		city.increaseCloths(produced);
		
	}
	
	protected void calculateSavingsTaxes(Gamer gamer, City city) {		
		
		gain = 0;
		
		gain = produced * city.getFurniturePrice();
		
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
		
		if(gamer.getResearchs().getIndustryLevel() > 0) {
			switch(citizen.familyClass) {
				case C:
					return true;
				case B:
					return true;
				case D:
					return true;
				default:
			}
		} else {
			switch(citizen.familyClass) {
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
