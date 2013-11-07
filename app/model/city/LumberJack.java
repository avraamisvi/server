package model.city;

import model.City;
import model.Gamer;
import enums.ConstructionType;
import enums.EmperiumConstants;
import enums.MineType;
import enums.OwnerType;

/**
 * Represents a mine
 * 
 * @author abraao
 *
 */
public class LumberJack extends Construction {

	MineType mineType;
	int production;
	int produced;
	long gain;
	
	public LumberJack() {
		type = ConstructionType.LumberJack;
		ownerType = OwnerType.enterprise;
		conservationCost = EmperiumConstants.MINE_CONSERVATION_COST;
		avaliableJobs = 4;		
		mineType = MineType.rock;
		production = 8;
		working=true;
		timeToBuild = EmperiumConstants.MINE_SECONDS_TO_BUILD;
	}
	
	public int getLevel(Gamer gamer) {
		return gamer.getWoodLevel();
	}
	
	private int getMaxProduction(Gamer gamer) {
		int level = getLevel(gamer);
		
		if(level == 1) {
			return 8;
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
		
		gamer.increaseWood(produced);
		
	}
	
	protected void calculateSavingsTaxes(Gamer gamer, City city) {		
		
		gain = produced * EmperiumConstants.WOOD_PRICE; 
		
		long tax = (city.getIndustryTax()*gain)/100;
		gain = gain-tax;
		
		savings+=gain;
		
		city.increaseTreasure(tax);
		gamer.increaseTreasure(tax);		
	}
	
	@Override
	public boolean acceptEmploee(Gamer gamer, City city, Citizen citizen) {
		int level = getLevel(gamer);
		
		if(level < 2) {
			switch(citizen.familyClass) {
				case D:
					return true;
				default:
			}
		} else if(level < 3) {
			switch(citizen.familyClass) {
				case D:
					return true;
				case C:
					return true;
				default:
			}
		} else {
			switch(citizen.familyClass) {
			case D:
				return true;
			case C:
				return true;
			case B:
				return true;
			default:
		}
	}
		
		return false;
	}

	public MineType getMineType() {
		return mineType;
	}

	public void setMineType(MineType mineType) {
		this.mineType = mineType;
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