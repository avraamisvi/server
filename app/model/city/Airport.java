package model.city;

import java.util.ArrayList;
import java.util.List;

import model.City;
import model.Gamer;
import model.city.barracks.AirShip;
import model.city.barracks.Warrior;
import model.city.barracks.WarriorCreationJob;
import enums.ConstructionType;
import enums.EmperiumConstants;
import enums.OwnerType;
import enums.WarriorsConstants;

public class Airport extends Construction {
	
	int level = 1;
	List<AirShip> airships= new ArrayList<AirShip>();
	
	WarriorCreationJob[] jobs = new WarriorCreationJob[20]; 
	
	public Airport() {
		type = ConstructionType.Airport;
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
			
		for(Warrior w : airships) {
			cost+=w.getCost();
		}
		
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
	
	private int getCreationTime(int type) {
		int ret=WarriorsConstants.SLOW_CREATION; 
		return ret;
	}	
	
	public void newAirShip() {
		newWarrior(WarriorsConstants.AIRSHIP);
	}
	
	private void create(int type) {
		airships.add(new AirShip());
	}	
	
	public WarriorCreationJob[] getJobs() {
		return jobs;
	}

	public void setJobs(WarriorCreationJob[] jobs) {
		this.jobs = jobs;
	}

	public List<AirShip> getAirships() {
		return airships;
	}

	public void setAirships(List<AirShip> airships) {
		this.airships = airships;
	}

	public boolean haveSlot() {
		for(int i = 0; i < jobs.length; i++) {
			if(jobs[i] == null) {
				return true;
			}
		}
		return false;
	}
}
