package model.city.barracks;

import enums.WarriorsConstants;

public class Caravel extends Warrior{

	Army army;
	
	public Caravel() {
		level = 0;
		type = WarriorsConstants.CARAVEL;
		power = 25;
		life = 50;
		velocity = 15;
		cost = 50;
	}

	public Army getArmy() {
		return army;
	}

	public void setArmy(Army army) {
		this.army = army;
	}
	
}
