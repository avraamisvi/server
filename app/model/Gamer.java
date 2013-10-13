package model;

import enums.Constants;


public class Gamer {		
	
	int id = Constants.GAMER_TEST;
	
	String empireName = "Test";
	
	/**
	 * Resources 
	 */
	int iron = 100;
	int wood = 100;
	int sulfur = 100;
	int rock = 100;
	
	/**
	 * Goods total monthly production (from every city)
	 */
	long entertainment = 1000;//AA
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


	public int getId() {
		return id;
	}

	public void setId(int id) {
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
	
	public void resetGoods() {
		
		this.entertainment = 0;
		this.wine = 0; 
		this.meat = 0;
		this.beans = 0;
		this.rice = 0;
		this.cloths = 0;
		this.furniture = 0;
	}

	public long getEntertainment() {
		return entertainment;
	}

	public void setEntertainment(long entertainment) {
		this.entertainment = entertainment;
	}

	public void increaseEntertainment(long l) {
		this.entertainment += l;
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
	
}
