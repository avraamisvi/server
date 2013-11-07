package model.city.barracks;

import enums.WarriorsConstants;

public class Cannon extends Warrior{

	public Cannon() {
		level = 0;
		type = WarriorsConstants.CANNON;
		power = 50;
		life = 25;
		velocity = 2;
		cost = 50;
	}
	
}
