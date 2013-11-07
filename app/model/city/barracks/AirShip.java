package model.city.barracks;

import enums.WarriorsConstants;

public class AirShip extends Warrior{

	Army army1;
	Army army2;
	Army army3;
	Army army4;
	
	public AirShip() {
		level = 0;
		type = WarriorsConstants.AIRSHIP;
		power = 35;
		life = 90;
		velocity = 30;
		cost = 110;
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

	public Army getArmy4() {
		return army4;
	}

	public void setArmy4(Army army4) {
		this.army4 = army4;
	}
}
