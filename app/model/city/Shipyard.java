package model.city;

import java.util.ArrayList;
import java.util.List;

import model.City;
import model.Gamer;
import model.city.barracks.Boat;
import model.city.barracks.Caravel;
import model.city.barracks.Ship;
import model.city.barracks.Warrior;
import model.city.barracks.WarriorCreationJob;
import enums.ConstructionType;
import enums.EmperiumConstants;
import enums.OwnerType;
import enums.WarriorsConstants;

public class Shipyard extends Construction {
	
	List<Boat> boats= new ArrayList<Boat>();
	List<Caravel> caravels= new ArrayList<Caravel>();
	List<Ship> ships= new ArrayList<Ship>();
//	List<AirShip> airships;
	
	WarriorCreationJob[] jobs = new WarriorCreationJob[20]; 
	
	public Shipyard() {
		type = ConstructionType.Shipyard;
		conservationCost = EmperiumConstants.MACHINERY_CONSERVATION_COST;
		ownerType = OwnerType.national;
		avaliableJobs = 0;
		working=true;
		timeToBuild = EmperiumConstants.MACHINERY_TIME_TO_BUILD;
	}

	@Override
	public boolean acceptEmploee(Gamer gamer, City city, Citizen citizen) {
		return false;
	}
	
	@Override
	protected void updateHandle(Gamer gamer, City city) {
		for(int i = 0; i < jobs.length; i++) {
			if(jobs[i] != null) {
				if(jobs[i].elapsed()) {
					create(jobs[i].getType());
					jobs[i]=null;
				}
			}
		}
	}

	@Override
	protected void calculateSavingsTaxes(Gamer gamer, City city) {
		int cost = 0; 
		
		for(Warrior w : ships ) {
			cost+=w.getCost();
		}
		for(Warrior w : boats ) {
			cost+=w.getCost();
		}
		for(Warrior w : caravels ) {
			cost+=w.getCost();
		}
//		for(Warrior w : airships) {
//			cost+=w.getCost();
//		}
		
		this.generalCost = cost;
	}
	
	/**
	 * Start a job for create a new warrior.
	 * @param type
	 */
	private void newWarrior(int type) {
		for(int i = 0; i < jobs.length; i++) {
			if(jobs[i] == null) {
				jobs[i] = new WarriorCreationJob(type, getCreationTime(type));
				break;
			}
		}
	}
	
	
	public void newAirShip() {
		newWarrior(WarriorsConstants.AIRSHIP);
	}
	
	public void newBoat() {
		newWarrior(WarriorsConstants.BOAT);
	}
	
	public void newCaravel() {
		newWarrior(WarriorsConstants.CARAVEL);
	}
	
	public void newShip() {
		newWarrior(WarriorsConstants.CANNON);
	}
	
	private int getCreationTime(int type) {
		int ret=WarriorsConstants.SLOW_CREATION; 
		return ret;
	}
	
	private void create(int type) {
		switch (type) {
		case WarriorsConstants.BOAT:
			boats.add(new Boat());
			break;
		case WarriorsConstants.SHIP:
			ships.add(new Ship());
			break;
//		case WarriorsConstants.AIRSHIP:
//			airships.add(new AirShip());
//			break;
		case WarriorsConstants.CARAVEL:
			caravels.add(new Caravel());
			break;
		}
	}	
	
	public WarriorCreationJob[] getJobs() {
		return jobs;
	}

	public void setJobs(WarriorCreationJob[] jobs) {
		this.jobs = jobs;
	}

	public List<Boat> getBoats() {
		return boats;
	}

	public void setBoats(List<Boat> boats) {
		this.boats = boats;
	}

	public List<Caravel> getCaravels() {
		return caravels;
	}

	public void setCaravels(List<Caravel> caravels) {
		this.caravels = caravels;
	}

	public List<Ship> getShips() {
		return ships;
	}

	public void setShips(List<Ship> ships) {
		this.ships = ships;
	}

	public boolean haveSlot() {
		for(int i = 0; i < jobs.length; i++) {
			if(jobs[i] == null) {
				return true;
			}
		}
		
		return false;
	}

//	public List<AirShip> getAirships() {
//		return airships;
//	}
//
//	public void setAirships(List<AirShip> airships) {
//		this.airships = airships;
//	}
}
