package model.city;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import model.City;
import model.Gamer;

import org.bson.types.ObjectId;
import org.joda.time.DateTime;
import org.mongodb.morphia.annotations.Embedded;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;

import enums.ConstructionType;
import enums.EmperiumConstants;
import enums.OwnerType;

@Embedded
public abstract class Construction {
	
	long mapPositionId;
	
	List<MapPositionPoint> mapSlots = new ArrayList<MapPositionPoint>(); 
	
	OwnerType ownerType;
	
	long savings;	
	
	/**
	 * Whe the construction was started.
	 */
	Date startDate = DateTime.now().toDate();
	Date lastUpdate = DateTime.now().toDate();
	
	ConstructionType type;

	boolean working;
	
	/**
	 * How many jobs are available
	 */
	int avaliableJobs;
	
	/**
	 * percent.
	 */
	int built;
	
	/**
	 * After destroyed, the player can clean the soil
	 */
	boolean cleaned;
	
	/**
	 * Percent, status of this building conservation
	 */
	int conservation = 100;
	
	/**
	 * The conservation cost.
	 */
	int conservationCost;
	
	List<Citizen> employed;//how many are employed
	
	int salary;
	
	/**
	 * Used for calculate how much the government will spend on public workers
	 */
	int generalCost = 100;
		
	
//	public Construction() {
//		super();		
//	}
	
	protected void calculateSavingsTaxes(Gamer gamer, City city) {
		if (this.getOwnerType().equals(OwnerType.enterprise)) {//calculate the taxes
			//TODO 
		}
	}

	public void decreaseConservation() {
		if(conservation > 0) {
			conservation = conservation- EmperiumConstants.DEGRADATION_CONSEVATION_FACTOR;
		}
	}

	public int getAvaliableJobs() {
		return avaliableJobs;
	}

	public int getBuilt() {
		return built;
	}

	public int getConservation() {
		return conservation;
	}

	public int getConservationCost() {
		return conservationCost;
	}

	public List<Citizen> getEmployed() {
		return employed;
	}

	public int getGeneralCost() {
		return generalCost;
	}

	public OwnerType getOwnerType() {
		return ownerType;
	}

	public long getSavings() {
		return savings;
	}

	public Date getStartDate() {
		return startDate;
	}

	public ConstructionType getType() {
		return type;
	}

	public void increaseConservation() {
		if(conservation < 100) {
			conservation += EmperiumConstants.DEGRADATION_CONSEVATION_FACTOR;
		} else {
			conservation = 100;
		}
	}

	public boolean isCleaned() {
		return cleaned;
	}

	public boolean isWorking() {
		return working;
	}

	public void printDebug() {
		System.out.println("###### CONSTRUCTION " + this.type.name() + " ID:" + this.getMapPositionId() + "###########");
		System.out.println("Conservation:" + conservation);
		System.out.println("Savings:" + savings);
		System.out.println("Working:" + isWorking());
		System.out.println("Employed:" + employed);
		System.out.println("Cost:" + (generalCost+conservationCost));
	}

	public void setAvaliableJobs(int avaliableJobs) {
		this.avaliableJobs = avaliableJobs;
	}
	
	public void setBuilt(int built) {
		this.built = built;
	}
	
	public void setCleaned(boolean cleaned) {
		this.cleaned = cleaned;
	}
	
	public void setConservation(int conservation) {
		this.conservation = conservation;
	}

	public void setConservationCost(int conservationCost) {
		this.conservationCost = conservationCost;
	}

	public void setEmployed(List<Citizen> employed) {
		this.employed = employed;
	}
	
	public void setGeneralCost(int generalCost) {
		this.generalCost = generalCost;
	}

	public void setOwnerType(OwnerType ownerType) {
		this.ownerType = ownerType;
	}

	public void setSavings(long savings) {
		this.savings = savings;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public void setType(ConstructionType type) {
		this.type = type;
	}

	public void setWorking(boolean working) {
		this.working = working;
	}

	/**
	 * Update the construction status
	 */
	public void update(Gamer gamer, City city) {
		
		lastUpdate = DateTime.now().toDate();
		DateTime start = new DateTime(startDate);		
		
		if(this.getBuilt() < 100) {
			DateTime now = DateTime.now();
			if(now.isAfter(start.plusSeconds(EmperiumConstants.SECONDS_TO_BUILD))) {//TODO each construction has a time to build
				this.built = 100;
			} else {
				DateTime targ = start.plusSeconds(EmperiumConstants.SECONDS_TO_BUILD);
				int total = (int) (100*((float)start.getMillis()/(float)targ.getMillis()));
				this.built = (int) total;
			}
						
		}
		
		if (this.getBuilt() == 100 && this.conservation > 0) {
						
//			employed = (int) city.decreaseUneployed(avaliableJobs);
			
			this.updateHandle(gamer, city);//particular could differ in logic
			this.calculateSavingsTaxes(gamer, city);
			
			if (this.getOwnerType().equals(OwnerType.national)) {
				if (!gamer.isBlockInfrastructure()) {
					savings += gamer.decreaseTreasure(conservationCost);
					savings += gamer.decreaseTreasure(generalCost);
					
					city.increaseExpenses(conservationCost);
					city.increaseExpenses(generalCost);
				}
			} 
			
			if (savings < conservationCost) {
				decreaseConservation();
			} else {
				increaseConservation();
				savings = savings - conservationCost;
			}

			if (savings < generalCost) {
				working = false;
			} else {
				working = true;
				savings = savings - generalCost;
			}
		}
		
		if (conservation <= 0) {
			working = false;
		}
	}

	/**
	 * For special updates
	 * 
	 * @param gamer
	 * @param city
	 */
	protected void updateHandle(Gamer gamer, City city) {
		
	}

	public void updateJobs(City city) {
		if(working)
			city.increaseJobs(avaliableJobs);
	}

	public long getMapPositionId() {
		return mapPositionId;
	}

	public void setMapPositionId(long mapPositionId) {
		this.mapPositionId = mapPositionId;
	}

	public Date getLastUpdate() {
		return lastUpdate;
	}

	public void setLastUpdate(Date lastUpdate) {
		this.lastUpdate = lastUpdate;
	}

	public List<MapPositionPoint> getMapSlots() {
		return mapSlots;
	}

	public void setMapSlots(List<MapPositionPoint> mapSlots) {
		this.mapSlots = mapSlots;
	}
	
	public boolean haveJob(Citizen citizen) {
		if(employed == null)
			employed = new ArrayList<Citizen>();
		
		if(employed.size() < avaliableJobs) {
			employed.add(citizen);
			citizen.setSalary(salary);
			citizen.setWorks((int) mapPositionId);
			return true;
		}
		
		return false;
	}
	
	public void clearEmployed() {
		employed = new ArrayList<Citizen>();
	}
}
