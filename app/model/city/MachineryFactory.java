package model.city;

import java.util.ArrayList;
import java.util.List;

import model.City;
import model.Gamer;
import model.city.barracks.Cannon;
import model.city.barracks.Catapult;
import model.city.barracks.Warrior;
import model.city.barracks.WarriorCreationJob;
import enums.ConstructionType;
import enums.EmperiumConstants;
import enums.OwnerType;
import enums.WarriorsConstants;

public class MachineryFactory extends Construction {
	
	List<Catapult> catapults= new ArrayList<Catapult>();
	List<Cannon> cannons= new ArrayList<Cannon>();
	
	WarriorCreationJob[] jobs = new WarriorCreationJob[20]; 
	
	public MachineryFactory() {
		type = ConstructionType.Machinery;
		conservationCost = EmperiumConstants.MACHINERY_CONSERVATION_COST;
		ownerType = OwnerType.national;
		avaliableJobs = 0;
		working=true;
		timeToBuild = EmperiumConstants.MACHINERY_TIME_TO_BUILD;
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
		
		for(Warrior w : catapults ) {
			cost+=w.getCost();
		}
		for(Warrior w : cannons ) {
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
	
	
	public void newCatapult() {
		newWarrior(WarriorsConstants.CATAPULT);
	}
	
	public void newCannon() {
		newWarrior(WarriorsConstants.CANNON);
	}
	
	private int getCreationTime(int type) {
		int ret=WarriorsConstants.SLOW_CREATION; 
		return ret;
	}
	
	private void create(int type) {
		switch (type) {
		case WarriorsConstants.CANNON:
			cannons.add(new Cannon());
			break;
		case WarriorsConstants.CATAPULT:
			catapults.add(new Catapult());
			break;
		}
	}	

	public List<Catapult> getCatapults() {
		return catapults;
	}

	public void setCatapults(List<Catapult> catapults) {
		this.catapults = catapults;
	}

	public List<Cannon> getCannons() {
		return cannons;
	}

	public void setCannons(List<Cannon> cannons) {
		this.cannons = cannons;
	}

	public WarriorCreationJob[] getJobs() {
		return jobs;
	}

	public void setJobs(WarriorCreationJob[] jobs) {
		this.jobs = jobs;
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
