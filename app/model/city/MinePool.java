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
public class MinePool extends Construction {

	MineType mineType;
	int production;
	int produced;
	long gain;
	
	public MinePool() {
		type = ConstructionType.Mine;
		ownerType = OwnerType.enterprise;
		conservationCost = EmperiumConstants.MINE_CONSERVATION_COST;
		avaliableJobs = 4;		
		mineType = MineType.rock;
		production = 8;
		working=true;
		timeToBuild = EmperiumConstants.MINE_SECONDS_TO_BUILD;
	}
	
	public int getLevel(Gamer gamer) {
		int level = 1;
		
		switch(mineType) {
		case iron:
			level = gamer.getMineIronLevel();
			break;
		case rock:
			level = gamer.getMineRockLevel();
			break;
		case sulfur:
			level = gamer.getMineSulfurLevel();
			break;
		case clay:
			level = gamer.getMineClayLevel();
			break;
		default:
		}
		
		return level;
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
	public void clearEmployed() {
		// TODO Auto-generated method stub
		super.clearEmployed();
	}
	
	@Override
	protected void updateHandle(Gamer gamer, City city) {
		int force = employed.size()*100/avaliableJobs;
		produced = (force*getMaxProduction(gamer))/100;
		
		switch (mineType) {
			case rock:
				gamer.increaseRock(produced);
				break;
				
			case iron:
				gamer.increaseIron(produced);
				break;
				
			case sulfur:
				gamer.increaseSulfur(produced);
				break;
				
			case wood:
				gamer.increaseWood(produced);
				break;
			case clay:
				gamer.increaseClay(produced);
				break;
			default:
				break;
		}
		
	}
	
	protected void calculateSavingsTaxes(Gamer gamer, City city) {		
		
		gain = 0;
		
		switch (mineType) {
			case iron:
				gain = produced * EmperiumConstants.IRON_PRICE; 
				break;
				
			case rock:
				gain = produced * EmperiumConstants.ROCK_PRICE;
				break;
				
			case sulfur:
				gain = produced * EmperiumConstants.SULFUR_PRICE;
				break;
			case clay:
				gain = produced * EmperiumConstants.CLAY_PRICE;
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