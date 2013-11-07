package model.city.barracks;

import enums.WarriorsConstants;

public class SpearMan extends Warrior{

	public SpearMan() {
		level = 0;
		type = WarriorsConstants.SPEARMAN;
		power = 10;
		life = 15;
		velocity = 3;
		cost = 15;
	}
	
}
