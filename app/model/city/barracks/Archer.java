package model.city.barracks;

import enums.WarriorsConstants;

public class Archer extends Warrior{

	public Archer() {
		level = 0;
		type = WarriorsConstants.ARCHER;
		power = 15;
		life = 25;
		velocity = 3;
		cost = 25;
	}
	
}
