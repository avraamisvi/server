package model.city;

import java.util.ArrayList;
import java.util.List;

import model.City;
import model.Gamer;
import model.city.barracks.Knight;
import model.city.barracks.Warrior;
import model.city.barracks.WarriorCreationJob;
import enums.ConstructionType;
import enums.EmperiumConstants;
import enums.OwnerType;
import enums.WarriorsConstants;

public class KnightsCamp extends Construction {
	
	List<Knight> knights= new ArrayList<Knight>();
	
	WarriorCreationJob[] jobs = new WarriorCreationJob[10]; 
	
	public KnightsCamp() {
		type = ConstructionType.Knights;
		conservationCost = EmperiumConstants.KNIGHTS_CONSERVATION_COST;
		ownerType = OwnerType.national;
		avaliableJobs = 0;
		working=true;
		timeToBuild = EmperiumConstants.KNIGHTS_TIME_TO_BUILD;
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
					create();
					jobs[i]=null;
				}
			}
		}
	}

	@Override
	protected void calculateSavingsTaxes(Gamer gamer, City city) {
		int cost = 0; 
		
		for(Warrior w : knights ) {
			cost+=w.getCost();
		}
		
		this.generalCost = cost;
	}
	
	/**
	 * Start a job for create a new warrior.
	 * @param type
	 */
	public void newKnight() {
		int type = WarriorsConstants.KNIGHT;
		
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
	
	private void create() {
		knights.add(new Knight());
	}

	public WarriorCreationJob[] getJobs() {
		return jobs;
	}

	public void setJobs(WarriorCreationJob[] jobs) {
		this.jobs = jobs;
	}

	public List<Knight> getKnights() {
		return knights;
	}

	public void setKnights(List<Knight> knights) {
		this.knights = knights;
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
