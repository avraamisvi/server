package model.city;

import model.City;
import model.Gamer;
import enums.ConstructionType;
import enums.EmperiumConstants;
import enums.OwnerType;

public class Bank extends Construction{
	long money;
	long limit = 10000;
	
	public Bank() {
		type = ConstructionType.Bank;
		ownerType = OwnerType.enterprise;
		conservationCost = EmperiumConstants.INDUSTRY_CONSERVATION_COST;
		avaliableJobs = 2;		
		timeToBuild = EmperiumConstants.INDUSTRY_SECONDS_TO_BUILD;		
		working=true;
	}
	
	@Override
	protected void updateHandle(Gamer gamer, City city) {
		if(money < 0) {
			money = money * 5;
//			int tax = (int) (money*10/100);
//			gamer.increaseTreasure(tax);
		}
		
		if(money > limit) {
			money = limit;
		}
	}

	public long getMoney() {
		return money;
	}

	public void setMoney(long money) {
		this.money = money;
	}

	public long getLimit() {
		return limit;
	}

	public void setLimit(long limit) {
		this.limit = limit;
	}
	
	@Override
	public boolean acceptEmploee(Gamer gamer, City city, Citizen citizen) {
		switch(citizen.familyClass) {
		case AA:
		case A:
			return true;
		default:
			return false;
		}		
	}
}
