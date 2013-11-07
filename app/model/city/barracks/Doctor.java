package model.city.barracks;

import enums.WarriorsConstants;

public class Doctor extends Warrior{

	public Doctor() {
		level = 0;
		type = WarriorsConstants.DOCTOR;
		power = 5;
		life = 25;
		velocity = 2;
		cost = 60;
	}
	
}
