package model.city.barracks;

import enums.WarriorsConstants;

public class Catapult extends Warrior{

	public Catapult() {
		level = 0;
		type = WarriorsConstants.CATAPULT;
		power = 45;
		life = 15;
		velocity = 2;
		cost = 45;
	}
	
}
