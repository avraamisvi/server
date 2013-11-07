package model.city.barracks;

import enums.WarriorsConstants;

public class Knight extends Warrior{

	public Knight() {
		level = 0;
		type = WarriorsConstants.KNIGHT;
		power = 20;
		life = 30;
		velocity = 5;
		cost = 35;
	}
	
}
