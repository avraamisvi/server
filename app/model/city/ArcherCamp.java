package model.city;

import java.util.ArrayList;
import java.util.List;

import model.City;
import model.Gamer;
import model.city.barracks.Archer;
import model.city.barracks.Warrior;
import model.city.barracks.WarriorCreationJob;
import enums.ConstructionType;
import enums.EmperiumConstants;
import enums.OwnerType;
import enums.WarriorsConstants;

public class ArcherCamp extends Construction {
	
	int level = 1;
	List<Archer> archers = new ArrayList<Archer>();
	
	WarriorCreationJob[] jobs = new WarriorCreationJob[20]; 
	
	public ArcherCamp() {
		type = ConstructionType.ArcherCamp;
		conservationCost = EmperiumConstants.ARCHER_CAMP_CONSERVATION_COST;
		ownerType = OwnerType.national;
		avaliableJobs = 0;
		working=true;
		timeToBuild = EmperiumConstants.ARCHER_CAMP_TIME_TO_BUILD;
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
		
		for(Warrior w : archers ) {
			cost+=w.getCost();
		}
		
		this.generalCost = cost;
	}
	
	/**
	 * Start a job for create a new warrior.
	 * @param type
	 */
	public void newArcher() {
		int type = WarriorsConstants.ARCHER;
		
		for(int i = 0; i < jobs.length; i++) {
			if(jobs[i] == null) {
				jobs[i] = new WarriorCreationJob(type, getCreationTime(type));
				break;
			}
		}
	}
	
	private int getCreationTime(int type) {
		int ret=WarriorsConstants.MIDDLE_CREATION; 
		return ret;
	}
	
	private void create(int type) {
		archers.add(new Archer());
	}	
	
	public WarriorCreationJob[] getJobs() {
		return jobs;
	}

	public void setJobs(WarriorCreationJob[] jobs) {
		this.jobs = jobs;
	}

	public List<Archer> getArchers() {
		return archers;
	}

	public void setArchers(List<Archer> archers) {
		this.archers = archers;
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
