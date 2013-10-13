package model.city;

import java.util.Date;

import model.City;
import model.Gamer;

import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;

import enums.Constants;
import enums.ConstructionType;
import enums.OwnerType;

@Entity
public abstract class Construction {
	
	@Id
	ObjectId id;
	
	long mapPositionId;
	
	OwnerType ownerType;
	
	long savings;	
	
	Date startDate;
	
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
	
	int employed;//how many are employed
	
	/**
	 * Used for calculate how much the government will spend on public workers
	 */
	int generalCost = 100;
	
	protected void calculateSavingsTaxes(Gamer gamer, City city) {
		if (this.getOwnerType().equals(OwnerType.enterprise)) {//calculate the taxes
			//TODO 
		}
	}

	public void decreaseConservation() {
		if(conservation > 0) {
			conservation = conservation- Constants.DEGRADATION_CONSEVATION_FACTOR;
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

	public int getEmployed() {
		return employed;
	}

	public int getGeneralCost() {
		return generalCost;
	}

	public ObjectId getId() {
		return id;
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
			conservation += Constants.DEGRADATION_CONSEVATION_FACTOR;
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

	public void setEmployed(int employed) {
		this.employed = employed;
	}
	
	public void setGeneralCost(int generalCost) {
		this.generalCost = generalCost;
	}

	public void setId(ObjectId id) {
		this.id = id;
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
		
		if (this.getBuilt() == 100 && this.conservation > 0) {
						
			employed = (int) city.decreaseUneployed(avaliableJobs);
			
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
	protected abstract void updateHandle(Gamer gamer, City city);

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
}
