package model;

import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;

@Entity
public class Gamer {		
	
     @Id
	 ObjectId id;
	
	String empireName = "Test";
	
	/**
	 * Resources 
	 */
	int iron = 100;
	int clay = 100;
	int wood = 100;
	int sulfur = 100;
	int rock = 100;
	int cotton = 100;
	int grapes = 100;
	
	int knowledge = 0;
	
	/**
	 * Goods total monthly production (from every city)
	 */
	long ceramics = 1000;//AA
	long wine = 1000;//A 
	long furniture;//BA
	long meat = 1000;//B
	long beans = 1000;//C
	long rice = 1000;//D
	long cloths = 1000;//D
	
	
	/**
	 * If the gamer will spend money for conservation of public buildings
	 */
	boolean blockInfrastructure = false;
	
	/**
	 * How much is spent in propaganda
	 */
	long propaganda;
	
	/**
	 * Given to the uneployed or poor citizens
	 */
	long benefits;
	
	/**
	 * How much on research.
	 */
	long research;	
	
	/**
	 * National treasure.
	 */
	long treasure = 100000;
	
	long totalExpenses = 0;
	
	//LAWS
	boolean abortion;
	boolean euthanasia;
	boolean divorce;

	Research researchs = new Research();
	
	long population;
	
	long maxPopulation;
	
	int industryLevel = 1;	
	int mineRockLevel = 1;
	int mineIronLevel = 1;
	int mineSulfurLevel = 1;
	int mineClayLevel = 1;
	int woodLevel = 1;
	
	int farmLevel = 1;
	
	public ObjectId getId() {
		return id;
	}

	public void setId(ObjectId id) {
		this.id = id;
	}

	public String getEmpireName() {
		return empireName;
	}

	public void setEmpireName(String empireName) {
		this.empireName = empireName;
	}

	public long getPropaganda() {
		return propaganda;
	}

	public void setPropaganda(long propaganda) {
		this.propaganda = propaganda;
	}

	public long getBenefits() {
		return benefits;
	}

	public void setBenefits(long benefits) {
		this.benefits = benefits;
	}

	public long getResearch() {
		return research;
	}

	public void setResearch(long research) {
		this.research = research;
	}


	public long getTreasure() {
		return treasure;
	}	
	
	/**
	 * Decrease the treasure from value
	 * @param value
	 * @return The value spent, but if the value is bigger than treasure then returns how much is lacking
	 */
	public synchronized long decreaseTreasure(long value) {
		if(treasure > value) {
			treasure = treasure - value;
			return value;
		}
			
		return treasure - value; 
	}
	
	public synchronized void increaseTreasure(long value) {
		 treasure += value;
	}	
	
	public long getTotalExpenses() {
		return totalExpenses;
	}
	
	public void resetExpenses() {
		totalExpenses = 0;
	}
	
	public void increaseExpenses(long exp) {
		totalExpenses += exp;
	}
	
	public void decreaseExpenses(long exp) {
		totalExpenses -= exp;
		if(totalExpenses < 0) {
			totalExpenses = 0;
		}
	}	
	
	public void resetPopulation() {
		population = 0;
		maxPopulation = 0;
	}
	
	public void increasePopulation(long pop) {
		population += pop;
	}
	
	public void increaseMaxPopulation(long pop) {
		maxPopulation += pop;
	}
	
	public void setTreasure(long treasure) {
		this.treasure = treasure;
	}

	public int getIron() {
		return iron;
	}

	public void setIron(int iron) {
		this.iron = iron;
	}

	public int getWood() {
		return wood;
	}

	public void setWood(int wood) {
		this.wood = wood;
	}

	public int getSulfur() {
		return sulfur;
	}

	public void setSulfur(int sulfur) {
		this.sulfur = sulfur;
	}

	public int getRock() {
		return rock;
	}

	public void setRock(int rock) {
		this.rock = rock;
	}

	public boolean isBlockInfrastructure() {
		return blockInfrastructure;
	}

	public void setBlockInfrastructure(boolean blockInfrastructure) {
		this.blockInfrastructure = blockInfrastructure;
	}
	
	/**
	 * Goods is by city
	 */
	public void resetGoods() {
		
		this.ceramics = 0;
		this.wine = 0; 
		this.meat = 0;
		this.beans = 0;
		this.rice = 0;
		this.cloths = 0;
		this.furniture = 0;
	}

	public long getCeramics() {
		return ceramics;
	}

	public void setCeramics(long entertainment) {
		this.ceramics = entertainment;
	}

	public void increaseCeramics(long l) {
		this.ceramics += l;
	}
	
	public long getWine() {
		return wine;
	}

	public void setWine(long wine) {
		this.wine = wine;
	}
	
	public void increaseWine(long wine) {
		this.wine += wine;
	}

	public long getMeat() {
		return meat;
	}

	public void setMeat(long meat) {
		this.meat = meat;
	}
	
	public void increaseMeat(long meat) {
		this.meat += meat;
	}

	public long getBeans() {
		return beans;
	}

	public void setBeans(long beans) {
		this.beans = beans;
	}
	
	public void increaseBeans(long beans) {
		this.beans += beans;
	}

	public long getRice() {
		return rice;
	}

	public void setRice(long rice) {
		this.rice = rice;
	}
	
	public void increaseRice(long rice) {
		this.rice += rice;
	}

	public long getCloths() {
		return cloths;
	}

	public void setCloths(long cloths) {
		this.cloths = cloths;
	}
	
	public void increaseCloths(long cloths) {
		this.cloths += cloths;
	}

	//LAWS
	public boolean isAbortion() {
		return abortion;
	}

	public void setAbortion(boolean abortion) {
		this.abortion = abortion;
	}

	public boolean isEuthanasia() {
		return euthanasia;
	}

	public void setEuthanasia(boolean euthanasia) {
		this.euthanasia = euthanasia;
	}

	public boolean isDivorce() {
		return divorce;
	}

	public void setDivorce(boolean divorce) {
		this.divorce = divorce;
	}
	//END LAWS
	
	public long getFurniture() {
		return furniture;
	}

	public void setFurniture(long furniture) {
		this.furniture = furniture;
	}

	public void increaseFurniture(long furniture) {
		this.furniture += furniture;
	}
	
	public void setTotalExpenses(long totalExpenses) {
		this.totalExpenses = totalExpenses;
	}

	public Research getResearchs() {
		return researchs;
	}

	public void setResearchs(Research researchs) {
		this.researchs = researchs;
	}

	public long getPopulation() {
		return population;
	}

	public void setPopulation(long population) {
		this.population = population;
	}

	public long getMaxPopulation() {
		return maxPopulation;
	}

	public void setMaxPopulation(long maxPopulation) {
		this.maxPopulation = maxPopulation;
	}

	public int getCotton() {
		return cotton;
	}

	public void setCotton(int cotton) {
		this.cotton = cotton;
	}

	public int getIndustryLevel() {
		return industryLevel;
	}

	public void setIndustryLevel(int industryLevel) {
		this.industryLevel = industryLevel;
	}

	public int getMineRockLevel() {
		return mineRockLevel;
	}

	public void setMineRockLevel(int mineRockLevel) {
		this.mineRockLevel = mineRockLevel;
	}

	public int getMineIronLevel() {
		return mineIronLevel;
	}

	public void setMineIronLevel(int mineIronLevel) {
		this.mineIronLevel = mineIronLevel;
	}

	public int getMineSulfurLevel() {
		return mineSulfurLevel;
	}

	public void setMineSulfurLevel(int mineSulfurLevel) {
		this.mineSulfurLevel = mineSulfurLevel;
	}

	public int getClay() {
		return clay;
	}

	public void setClay(int clay) {
		this.clay = clay;
	}

	public int getMineClayLevel() {
		return mineClayLevel;
	}

	public void setMineClayLevel(int mineClayLevel) {
		this.mineClayLevel = mineClayLevel;
	}

	public int getWoodLevel() {
		return woodLevel;
	}

	public void setWoodLevel(int woodLevel) {
		this.woodLevel = woodLevel;
	}

	public int getFarmLevel() {
		return farmLevel;
	}

	public void setFarmLevel(int farmLevel) {
		this.farmLevel = farmLevel;
	}

	public void increaseCotton(int cotton) {
		this.cotton += cotton;
	}
	
	public int decreaseCotton(int cotton) {
		int ret = 0;
		
		if(this.cotton > cotton) {
			this.cotton -= cotton;
			ret = cotton;
		} else {
			ret = this.cotton;
			this.cotton=0;
		}
		
		return ret;	
	}
	
	public void increaseWood(int wood) {
		this.wood += wood;
	}
	
	public int decreaseWood(int wood) {
		int ret = 0;
		
		if(this.wood > wood) {
			this.wood -= wood;
			ret = wood;
		} else {
			ret = this.wood;
			this.wood=0;
		}
		
		return ret;	
	}
	
	public void increaseClay(int c) {
		this.clay += c;
	}
	
	public int decreaseClay(int c) {
		int ret = 0;
		
		if(this.clay > c) {
			this.clay -= c;
			ret = c;
		} else {
			ret = this.clay;
			this.clay=0;
		}
		
		return ret;	
	}	
	
	public void increaseIron(int iron) {
		this.iron += iron;
	}
	
	public int decreaseIron(int iron) {
		int ret = 0;
		
		if(this.iron > iron) {
			this.iron -= iron;
			ret = iron;
		} else {
			ret = this.iron;
			this.iron=0;
		}
		
		return ret;	
	}	
	
	public void increaseRock(int rock) {
		this.rock += rock;
	}
	
	public int decreaseRock(int rock) {
		int ret = 0;
		
		if(this.rock > rock) {
			this.rock -= rock;
			ret = rock;
		} else {
			ret = this.rock;
			this.rock=0;
		}
		
		return ret;	
	}	
	
	public void increaseSulfur(int rock) {
		this.sulfur += sulfur;
	}
	
	public int decreaseSulfur(int sulfur) {
		int ret = 0;
		
		if(this.sulfur > sulfur) {
			this.sulfur -= sulfur;
			ret = sulfur;
		} else {
			ret = this.sulfur;
			this.sulfur=0;
		}
		
		return ret;	
	}	

	public void increaseKnowledge(int val) {
		this.knowledge += val;
	}	

	public int decreaseKnowledge(int val) {
		int ret = 0;
		
		if(this.knowledge > val) {
			this.knowledge -= val;
			ret = val;
		} else {
			ret = this.knowledge;
			this.knowledge=0;
		}
		
		return ret;	
	}	
	
	public void increaseGrapes(int grapes) {
		this.grapes += grapes;
	}
	
	public int decreaseGrapes(int grapes) {
		int ret = 0;
		
		if(this.grapes > grapes) {
			this.grapes -= grapes;
			ret = grapes;
		} else {
			ret = this.grapes;
			this.grapes=0;
		}
		
		return ret;	
	}
	
	public int getGrapes() {
		return grapes;
	}
	
	public void setGrapes(int grapes) {
		this.grapes = grapes;
	}

	public int getKnowledge() {
		return knowledge;
	}

	public void setKnowledge(int knowledge) {
		this.knowledge = knowledge;
	}
}
