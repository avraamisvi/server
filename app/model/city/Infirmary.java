package model.city;

import java.util.ArrayList;
import java.util.List;

import model.City;
import model.Gamer;
import model.city.barracks.Doctor;
import model.city.barracks.Warrior;
import model.city.barracks.WarriorCreationJob;
import enums.ConstructionType;
import enums.EmperiumConstants;
import enums.OwnerType;
import enums.WarriorsConstants;

public class Infirmary extends Construction {
	
	List<Doctor> doctors= new ArrayList<Doctor>();
	
	WarriorCreationJob[] jobs = new WarriorCreationJob[10]; 
	
	public Infirmary() {
		type = ConstructionType.Infirmary;
		conservationCost = EmperiumConstants.INFIRMARY_CONSERVATION_COST;
		ownerType = OwnerType.national;
		avaliableJobs = 0;
		working=true;
		timeToBuild = EmperiumConstants.INFIRMARY_TIME_TO_BUILD;
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
		
		for(Warrior w : doctors ) {
			cost+=w.getCost();
		}
		
		this.generalCost = cost;
	}
	
	/**
	 * Start a job for create a new warrior.
	 * @param type
	 */
	public void newDoctor() {
		int type = WarriorsConstants.DOCTOR;
		
		for(int i = 0; i < jobs.length; i++) {
			if(jobs[i] == null) {
				jobs[i] = new WarriorCreationJob(type, getCreationTime(type));
			}
		}
	}
	
	private int getCreationTime(int type) {
		int ret=WarriorsConstants.MIDDLE_CREATION; 
		return ret;
	}
	
	private void create(int type) {
		doctors.add(new Doctor());
	}

	public List<Doctor> getDoctors() {
		return doctors;
	}

	public void setDoctors(List<Doctor> doctors) {
		this.doctors = doctors;
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
