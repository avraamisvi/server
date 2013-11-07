package model.city.barracks;

import enums.WarriorsConstants;

public class Boat extends Warrior{

	Army army;
	
	public Boat() {
		level = 0;
		type = WarriorsConstants.BOAT;
		power = 15;
		life = 25;
		velocity = 5;
		cost = 25;
	}

	public Army getArmy() {
		return army;
	}

	public void setArmy(Army army) {
		this.army = army;
	}
	
}
