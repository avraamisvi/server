package model.city.barracks;

import enums.WarriorsConstants;

public class Ship extends Warrior{

	Army army1;
	Army army2;
	Army army3;
	
	public Ship() {
		level = 0;
		type = WarriorsConstants.SHIP;
		power = 30;
		life = 75;
		velocity = 25;
		cost = 100;
	}

	public Army getArmy1() {
		return army1;
	}

	public void setArmy1(Army army1) {
		this.army1 = army1;
	}

	public Army getArmy2() {
		return army2;
	}

	public void setArmy2(Army army2) {
		this.army2 = army2;
	}

	public Army getArmy3() {
		return army3;
	}

	public void setArmy3(Army army3) {
		this.army3 = army3;
	}
	
}
