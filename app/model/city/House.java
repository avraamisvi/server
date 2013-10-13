package model.city;

import enums.Constants;
import enums.ConstructionType;
import enums.FamilyClass;
import enums.OwnerType;
import model.City;
import model.Gamer;

public class House extends Construction{
	
	FamilyClass familyClass;	
	
	long entertainment;//AA
	long wine;//A 
	long furniture;//BA
	long meat;//B
	long beans;//C
	long rice;//D
	long cloths;//D	
	
	int citizenActive;//adult citizens available to work
	int citizenChild;//child citizens unavailable to work, indice de quantos filhos nascen para virarem força ativa
	
	int withaJob;//citizens of this house with a job
	
	int satisfaction;//satisfaction level 1-100; 1 = angry, 100 = happy 
	
	public House() {
		type = ConstructionType.House;
		ownerType = OwnerType.citizen;
		familyClass = FamilyClass.C;
		conservationCost = Constants.HOUSE_CONSERVATION_COST_D;
		generalCost=0;
		
		this.satisfaction = 100;
		citizenActive = 2;
		citizenChild = 4;		
	}	
	
	public long getEntertainment() {
		return entertainment;
	}

	public void setEntertainment(long entertainment) {
		this.entertainment = entertainment;
	}

	public long getWine() {
		return wine;
	}

	public void setWine(long wine) {
		this.wine = wine;
	}

	public long getFurniture() {
		return furniture;
	}

	public void setFurniture(long furniture) {
		this.furniture = furniture;
	}

	public long getMeat() {
		return meat;
	}

	public void setMeat(long meat) {
		this.meat = meat;
	}

	public long getBeans() {
		return beans;
	}

	public void setBeans(long beans) {
		this.beans = beans;
	}

	public long getRice() {
		return rice;
	}

	public void setRice(long rice) {
		this.rice = rice;
	}

	public long getCloths() {
		return cloths;
	}

	public void setCloths(long cloths) {
		this.cloths = cloths;
	}

	public int getCitizenActive() {
		return citizenActive;
	}

	public void setCitizenActive(int citizenActive) {
		this.citizenActive = citizenActive;
	}

	public int getCitizenChild() {
		return citizenChild;
	}

	public void setCitizenChild(int citizenChild) {
		this.citizenChild = citizenChild;
	}

	public int getWithaJob() {
		return withaJob;
	}

	public void setWithaJob(int withaJob) {
		this.withaJob = withaJob;
	}

	public int getSatisfaction() {
		return satisfaction;
	}

	public void setSatisfaction(int satisfaction) {
		this.satisfaction = satisfaction;
	}

	public int getMaxPopulation() {
		if(familyClass.equals(FamilyClass.A) || familyClass.equals(FamilyClass.AA)) {
			return 3;
		} else {
			return 6;
		}
	}

	public FamilyClass getFamilyClass() {
		return familyClass;
	}
	
	public void setFamilyClass(FamilyClass familyClass) {
		this.familyClass = familyClass;
	}

	@Override
	protected void updateHandle(Gamer gamer, City city) {
		calculateSatisfaction();
		calculateBirth(city, gamer);
		calculateAdultAndDeath(city, gamer);
		
		city.increasePopulation(this.getHousePopulation());
		withaJob = (int) city.decreaseJobs(citizenActive);
	}
	
	@Override
	protected void calculateSavingsTaxes(Gamer gamer, City city) {
		
		long salary = withaJob*city.getSalaryByFamilyClass(this.getFamilyClass());
		
		if(salary > 0) {		
			long tax = (city.getCitizenTax()*salary)/100;
			salary = salary-tax;
			
			this.savings+=salary;
			
			city.increaseTreasure(tax);
			gamer.increaseTreasure(tax);
		}
		
		evalFamilyClassUpgrade(city);
		calculateConsumeGoods(city);
	}
	
	private void evalFamilyClassUpgrade(City city) {
		if(familyClass.equals(FamilyClass.A)) {
			if(city.getSalaryAA() <= savings) {
				this.familyClass = FamilyClass.AA;
				this.conservationCost = Constants.HOUSE_CONSERVATION_COST_AA;
			} else {
				this.familyClass = FamilyClass.A;
				this.conservationCost = Constants.HOUSE_CONSERVATION_COST_A;
			}
		} else {
			if(city.getSalaryBA() <= savings) {
				this.familyClass = FamilyClass.BA;
				this.conservationCost = Constants.HOUSE_CONSERVATION_COST_BA;
			} else if (city.getSalaryB() <= savings) {
				this.familyClass = FamilyClass.B;
				this.conservationCost = Constants.HOUSE_CONSERVATION_COST_B;
			} else if(city.getSalaryC() <= savings) {
				this.familyClass = FamilyClass.C;
				this.conservationCost = Constants.HOUSE_CONSERVATION_COST_C;
			} else {
				this.familyClass = FamilyClass.D;
				this.conservationCost = Constants.HOUSE_CONSERVATION_COST_D;
			}
		}
	}
	
	/**
	 * Calculates the consume of this house
	 * @param city
	 */
	private void calculateConsumeGoods(City city) {
		
		cloths = 0;
		rice= 0;
		beans = 0;
		meat = 0;
		furniture = 0;
		wine = 0;
		entertainment = 0;		
		
		if(savings > 0) {
			Prices prices = new Prices();
			prices.entertainmentPrice = getHousePopulation()*city.getEntertainmentPrice();//AA
			prices.winePrice = getHousePopulation()*city.getWinePrice();//A 
			prices.furniturePrice = getHousePopulation()*city.getFurniturePrice();//BA
			prices.meatPrice = getHousePopulation()*city.getMeatPrice();//B
			prices.beansPrice = getHousePopulation()*city.getBeansPrice();//C
			prices.ricePrice = getHousePopulation()*city.getRicePrice();//D
			prices.clothsPrice = getHousePopulation()*city.getClothsPrice();//D
			
			if(this.familyClass.equals(FamilyClass.AA)) {	
				caculateGoodsBy(true, true, true, true, true, true, true, prices, city);
			} else if (this.familyClass.equals(FamilyClass.A)) {
				caculateGoodsBy(true, true, true, true, true, true, false, prices, city);
			} else if(this.familyClass.equals(FamilyClass.BA)) {
				caculateGoodsBy(true, true, true, true, true, false, false, prices, city);
			} else if(this.familyClass.equals(FamilyClass.B)) {
				caculateGoodsBy(true, true, true, true, false, false, false, prices, city);
			} else if(this.familyClass.equals(FamilyClass.C)) {
				caculateGoodsBy(true, true, true, false, false, false, false, prices, city);		
			} else {
				caculateGoodsBy(true, true, false, false, false, false, false, prices, city);
			}
			
			cloths = city.decreaseRice(rice);
			rice= city.decreaseCloths(cloths);
			beans= city.decreaseBeans(beans);
			meat= city.decreaseMeat(meat);
			furniture= city.decreaseFurniture(furniture);
			wine = city.decreaseWine(wine);
			entertainment= city.decreaseEntertainment(entertainment);		
		}
	}
	
	/**
	 * Used to remove repeated code
	 * 
	 * @param b_rice
	 * @param b_cloths
	 * @param b_beans
	 * @param b_meat
	 * @param b_furniture
	 * @param b_wine
	 * @param b_entertainment
	 * @param prices
	 * @param city
	 */
	private void caculateGoodsBy(
	boolean b_rice,
	boolean b_cloths,
	boolean b_beans,
	boolean b_meat,
	boolean b_furniture,
	boolean b_wine,
	boolean b_entertainment, Prices prices, City city) {
		
		if(b_rice) {
			rice = calculateGoods(prices.ricePrice);
			decreaseSavings(rice*city.getRicePrice());
		}
		if (b_cloths) {
			cloths = calculateGoods(prices.clothsPrice);
			decreaseSavings(cloths * city.getClothsPrice());
		}
		if (b_beans) {
			beans = calculateGoods(prices.beansPrice);
			decreaseSavings(beans * city.getBeansPrice());
		}
		if (b_meat) {
			meat = calculateGoods(prices.meatPrice);
			decreaseSavings(meat * city.getMeatPrice());
		}
		if (b_furniture) {
			furniture = calculateGoods(prices.furniturePrice);
			decreaseSavings(furniture * city.getFurniturePrice());
		}
		if (b_wine) {
			wine = calculateGoods(prices.winePrice);
			decreaseSavings(wine * city.getWinePrice());
		}
		if (b_entertainment) {
			entertainment = calculateGoods(prices.entertainmentPrice);
			decreaseSavings(entertainment * city.getEntertainmentPrice());
		}
	}
	
	private void decreaseSavings(long val) {
		if(this.savings > 0) {
			this.savings = this.savings-val;
		}
	}
	
	private int calculateGoods(long price) {
		
		int ret = 0;
		
		if(this.savings > price) {
			int val = (int) (this.savings/price);
			ret = val >= getHousePopulation()? getHousePopulation(): val;
		}
		
		return ret;
	}
	
	private void calculateAdultAndDeath(City city, Gamer gamer) {
		
		if(gamer.isEuthanasia()) {
			this.citizenActive--;
			if(withaJob>citizenActive) {
				this.citizenActive--;
			}
		}
		
		if(satisfaction < 30) {
			this.citizenActive = this.citizenActive-2;
		} else {
			this.citizenActive--;
		}
		
		if(citizenActive < citizenChild/2) {
			if(citizenActive<=0) {
				citizenActive = 2;
			} else {
				citizenActive += citizenActive;
			}
		} else {		
			this.citizenActive+=this.citizenChild;
		}
		
		if(this.citizenActive < 0) {
			this.citizenActive = 0;
		} else if(this.citizenActive > getMaxPopulation()) {
			this.citizenActive = getMaxPopulation();
		}
	}
	
	//FIXME
	private void calculateBirth(City city, Gamer gamer) {
		
		if(this.citizenActive <= 0) {
			this.citizenChild = 0;
			return;
		}
		
		if(gamer.isAbortion()) {
			this.citizenChild = this.citizenChild-2;
		}
		
		if(satisfaction < 30) {
			this.citizenChild = this.citizenChild-2;
		} else if(satisfaction < 50) {
			this.citizenChild--;
		} else if(satisfaction > 80) {
			this.citizenChild=this.citizenChild+2;
		} else if(satisfaction > 50) {
			this.citizenChild=this.citizenChild+2;
		} else {
			this.citizenChild++;
		}
		
		if(this.citizenChild < 0) {
			this.citizenChild = 0;
		}
		
		if(this.citizenChild > getMaxPopulation()) {
			this.citizenChild = getMaxPopulation()/2;
		}
	}
	
	public int getHousePopulation() {
		return citizenActive;
	}
	
	private void decreaseSatisfaction() {
		satisfaction--;
		if(satisfaction < 0)
			satisfaction = 0;
	}
	
	private void increaseSatisfaction() {
		satisfaction++;
		if(satisfaction > 100)
			satisfaction = 100;
	}
	
	public void calculateSatisfaction() {
		//satisfaction
		if(this.familyClass.equals(FamilyClass.AA)) {	
			if(rice < getHousePopulation()) {
				decreaseSatisfaction();
			} else {
				increaseSatisfaction();
			}
			
			if(cloths < getHousePopulation()) {
				decreaseSatisfaction();
			} else {
				increaseSatisfaction();
			}
			
			if(beans < getHousePopulation()) {
				decreaseSatisfaction();	
			} else {
				increaseSatisfaction();
			}
			
			if(meat < getHousePopulation()) {
				decreaseSatisfaction();
			} else {
				increaseSatisfaction();
			}		
			
			if(furniture < getHousePopulation()) {
				decreaseSatisfaction();
			} else {
				increaseSatisfaction();
			}
			
			if(wine < getHousePopulation()) {
				decreaseSatisfaction();
			} else {
				increaseSatisfaction();
			}		
			
			if(entertainment < getHousePopulation()) {
				decreaseSatisfaction();
			} else {
				increaseSatisfaction();
			}
			
		} else if (this.familyClass.equals(FamilyClass.A)) {
			if(rice < getHousePopulation()) {
				decreaseSatisfaction();
			} else {
				increaseSatisfaction();
			}
			
			if(cloths < getHousePopulation()) {
				decreaseSatisfaction();
			} else {
				increaseSatisfaction();
			}
			
			if(beans < getHousePopulation()) {
				decreaseSatisfaction();	
			} else {
				increaseSatisfaction();
			}
			
			if(meat < getHousePopulation()) {
				decreaseSatisfaction();
			} else {
				increaseSatisfaction();
			}		
			
			if(furniture < getHousePopulation()) {
				decreaseSatisfaction();
			} else {
				increaseSatisfaction();
			}
			
			if(wine < getHousePopulation()) {
				decreaseSatisfaction();
			} else {
				increaseSatisfaction();
			}		
		} else if(this.familyClass.equals(FamilyClass.BA)) {
			if(rice < getHousePopulation()) {
				decreaseSatisfaction();
			} else {
				increaseSatisfaction();
			}
			
			if(cloths < getHousePopulation()) {
				decreaseSatisfaction();
			} else {
				increaseSatisfaction();
			}
			
			if(beans < getHousePopulation()) {
				decreaseSatisfaction();	
			} else {
				increaseSatisfaction();
			}
			
			if(meat < getHousePopulation()) {
				decreaseSatisfaction();
			} else {
				increaseSatisfaction();
			}		
			
			if(furniture < getHousePopulation()) {
				decreaseSatisfaction();
			} else {
				increaseSatisfaction();
			}			
		} else if(this.familyClass.equals(FamilyClass.B)) {
			if(rice < getHousePopulation()) {
				decreaseSatisfaction();
			} else {
				increaseSatisfaction();
			}
			
			if(cloths < getHousePopulation()) {
				decreaseSatisfaction();
			} else {
				increaseSatisfaction();
			}
			
			if(beans < getHousePopulation()) {
				decreaseSatisfaction();	
			} else {
				increaseSatisfaction();
			}
			
			if(meat < getHousePopulation()) {
				decreaseSatisfaction();
			} else {
				increaseSatisfaction();
			}		
						
		} else if(this.familyClass.equals(FamilyClass.C)) {
			if(rice < getHousePopulation()) {
				decreaseSatisfaction();
			} else {
				increaseSatisfaction();
			}
			
			if(cloths < getHousePopulation()) {
				decreaseSatisfaction();
			} else {
				increaseSatisfaction();
			}
			
			if(beans < getHousePopulation()) {
				decreaseSatisfaction();	
			} else {
				increaseSatisfaction();
			}
			
		} else {
			if(rice < getHousePopulation()) {
				decreaseSatisfaction();
			} else {
				increaseSatisfaction();
			}
			
			if(cloths < getHousePopulation()) {
				decreaseSatisfaction();
			} else {
				increaseSatisfaction();
			}					
		}				
	}
	
	public void printDebug() {
		
		super.printDebug();
		
		System.out.println("Satisfaction:" + satisfaction);
		System.out.println("HousePolulation:" + getHousePopulation());
		System.out.println("CitizenChild:" + citizenChild);
		System.out.println("WithaJob:" + withaJob);
		
		System.out.println("rice:" + rice);
		System.out.println("beans:" + beans);
		System.out.println("cloths:" + cloths);
		System.out.println("rice:" + entertainment);
		System.out.println("beans:" + furniture);
		System.out.println("cloths:" + wine);
		System.out.println("cloths:" + meat);
		System.out.println("Class:" + familyClass.name());
	}
	
	public void updateJobs(City city) {
		super.updateJobs(city);
		city.increaseUneployed(citizenActive);
	}
	
	class Prices {
		public long entertainmentPrice;
		public long winePrice; 
		public long furniturePrice;
		public long meatPrice;
		public long beansPrice;
		public long ricePrice;
		public long clothsPrice;		
	}
}
