package model.city.barracks;

import enums.WarriorsConstants;

public class Rifleman extends Warrior{

	public Rifleman() {
		level = 0;
		type = WarriorsConstants.RIFLEMAN;
		power = 35;
		life = 35;
		velocity = 5;
		cost = 50;
	}
	
}
