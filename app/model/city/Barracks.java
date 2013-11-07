package model.city;

import java.util.ArrayList;
import java.util.List;

import model.City;
import model.Gamer;
import model.city.barracks.Rifleman;
import model.city.barracks.SlingMan;
import model.city.barracks.SpearMan;
import model.city.barracks.SwordMan;
import model.city.barracks.Warrior;
import model.city.barracks.WarriorCreationJob;
import enums.ConstructionType;
import enums.EmperiumConstants;
import enums.OwnerType;
import enums.WarriorsConstants;

public class Barracks extends Construction {
	
	int level = 1;
	List<Rifleman> riflemans = new ArrayList<Rifleman>();
	List<SlingMan> slingMans = new ArrayList<SlingMan>();
	List<SpearMan> spearMans = new ArrayList<SpearMan>();
	List<SwordMan> swordMans = new ArrayList<SwordMan>();
	
	WarriorCreationJob[] jobs = new WarriorCreationJob[20]; 
	
	public Barracks() {
		type = ConstructionType.Barracks;
		conservationCost = EmperiumConstants.BARRACKS_CONSERVATION_COST;
		ownerType = OwnerType.national;
		avaliableJobs = 0;
		working=true;
		timeToBuild = EmperiumConstants.BARRACKS_TIME_TO_BUILD;
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
		
		for(Warrior w : swordMans ) {
			cost+=w.getCost();
		}
		for(Warrior w : spearMans ) {
			cost+=w.getCost();
		}
		for(Warrior w : riflemans ) {
			cost+=w.getCost();
		}
		for(Warrior w : slingMans ) {
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
	
	public boolean haveSlot() {
		for(int i = 0; i < jobs.length; i++) {
			if(jobs[i] == null) {
				return true;
			}
		}
		
		return false;
	}
	
	public void newRiflemans() {
		newWarrior(WarriorsConstants.RIFLEMAN);
	}
	
	public void newSlingMans() {
		newWarrior(WarriorsConstants.SLING);
	}
	public void newSpearMans() {
		newWarrior(WarriorsConstants.SPEARMAN);
	}
	public void newSwordMans() {
		newWarrior(WarriorsConstants.SWORDMAN);
	}
	
	private int getCreationTime(int type) {
		int ret=WarriorsConstants.SLOW_CREATION; 
		
		switch (type) {
		case WarriorsConstants.SWORDMAN:
			ret = WarriorsConstants.QUICKLY_CREATION;
			break;
		case WarriorsConstants.RIFLEMAN:
			ret = WarriorsConstants.MIDDLE_CREATION;
			break;
		case WarriorsConstants.SLING:			
			ret = WarriorsConstants.MIDDLE_CREATION;
			break;
		case WarriorsConstants.SPEARMAN:			
			ret = WarriorsConstants.MIDDLE_CREATION;
			break;
		}
		
		return ret;
	}
	
	private void create(int type) {
		switch (type) {
		case WarriorsConstants.SWORDMAN:
			swordMans.add(new SwordMan());
			break;
		case WarriorsConstants.RIFLEMAN:
			riflemans.add(new Rifleman());
			break;
		case WarriorsConstants.SLING:		
			slingMans.add(new SlingMan());
			break;
		case WarriorsConstants.SPEARMAN:	
			spearMans.add(new SpearMan());
			break;
		}
	}	
	
	public List<Rifleman> getRiflemans() {
		return riflemans;
	}

	public void setRiflemans(List<Rifleman> riflemans) {
		this.riflemans = riflemans;
	}

	public List<SlingMan> getSlingMans() {
		return slingMans;
	}

	public void setSlingMans(List<SlingMan> slingMans) {
		this.slingMans = slingMans;
	}

	public List<SpearMan> getSpearMans() {
		return spearMans;
	}

	public void setSpearMans(List<SpearMan> spearMans) {
		this.spearMans = spearMans;
	}

	public List<SwordMan> getSwordMans() {
		return swordMans;
	}

	public void setSwordMans(List<SwordMan> swordMans) {
		this.swordMans = swordMans;
	}

	public WarriorCreationJob[] getJobs() {
		return jobs;
	}

	public void setJobs(WarriorCreationJob[] jobs) {
		this.jobs = jobs;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}
	
	@Override
	public boolean acceptEmploee(Gamer gamer, City city, Citizen citizen) {
		return false;
	}
}
