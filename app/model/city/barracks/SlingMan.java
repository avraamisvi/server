package model.city.barracks;

import enums.WarriorsConstants;

public class SlingMan extends Warrior{

	public SlingMan() {
		level = 0;
		type = WarriorsConstants.SLING;
		power = 15;
		life = 15;
		velocity = 2;
		cost = 15;
	}
	
}
