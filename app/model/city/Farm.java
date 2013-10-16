package model.city;

import enums.ConstructionType;
import enums.GoodsTypeProduction;
import enums.OwnerType;
import model.City;
import model.Gamer;

public class Farm extends Construction{

	GoodsTypeProduction typeProduction;
	int production;
	int produced;
	
	public Farm() {
		type = ConstructionType.Farm;
		ownerType = OwnerType.citizen;
		conservationCost = 100;
		generalCost = 3200;
		avaliableJobs = 8;		
		typeProduction = GoodsTypeProduction.rice;
		production = 100;
	}
	
	@Override
	protected void updateHandle(Gamer gamer, City city) {
		int force = employed.size()*100/avaliableJobs;
		produced = (force*production)/100;
		
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
				
			default:
				break;
		}
		
	}
	
	protected void calculateSavingsTaxes(Gamer gamer, City city) {		
		
		long gain = 0;
		
		switch (typeProduction) {
			case rice:
				gain = produced * city.getRicePrice(); 
				break;
				
			case beans:
				gain = produced * city.getRicePrice();
				break;
				
			case meat:
				gain = produced * city.getRicePrice();
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

}
