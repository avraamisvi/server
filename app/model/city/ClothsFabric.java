package model.city;

import model.City;
import model.Gamer;
import enums.ConstructionType;
import enums.EmperiumConstants;
import enums.GoodsTypeProduction;
import enums.OwnerType;

public class ClothsFabric extends Construction{

	GoodsTypeProduction typeProduction;
	int production;
	int produced;
	long gain;
	
	public ClothsFabric() {
		type = ConstructionType.Cloths;
		ownerType = OwnerType.enterprise;
		conservationCost = EmperiumConstants.CLOTHS_CONSERVATION_COST;
		avaliableJobs = 4;		
		typeProduction = GoodsTypeProduction.cloths;
		production = 48;
		working=true;
	}
	
	@Override
	protected void updateHandle(Gamer gamer, City city) {
		int force = employed.size()*100/avaliableJobs;
		produced = (force*production)/100;
		city.increaseCloths(produced);
	}
	
	protected void calculateSavingsTaxes(Gamer gamer, City city) {		
		
		gain = 0;
		
		gain = produced * city.getClothsPrice();
		
		long tax = (city.getIndustryTax()*gain)/100;
		gain = gain-tax;
		
		savings+=gain;
		
		city.increaseTreasure(tax);
		gamer.increaseTreasure(tax);		
	}
	
	@Override
	public boolean acceptEmploee(Gamer gamer, City city, Citizen citizen) {
		
		if(gamer.getResearchs().getIndustryLevel() > 0) {
			switch(citizen.familyClass) {
				case C:
					return true;
				case B:
					return true;
				default:
			}
		} else {
			switch(citizen.familyClass) {
				case D:
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
