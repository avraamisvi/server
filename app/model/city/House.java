package model.city;

import java.util.ArrayList;
import java.util.List;

import org.mongodb.morphia.annotations.Embedded;

import com.sun.jna.platform.win32.WinUser.WNDENUMPROC;

import enums.EmperiumConstants;
import enums.ConstructionType;
import enums.FamilyClass;
import enums.OwnerType;
import model.City;
import model.Gamer;

@Embedded
public class House extends Construction{
	
	FamilyClass familyClass;	
	
	int entertainment;//AA
	int wine;//A 
	int furniture;//BA
	int meat;//B
	int beans;//C
	int rice;//D
	int cloths;//D	
	
	List<Citizen> citizens;//adult citizens available to work
	int citizenChild;//child citizens unavailable to work, indice de quantos filhos nascen para virarem força ativa
	
	int satisfaction;//satisfaction level 1-100; 1 = angry, 100 = happy 
	
	public House() {
		type = ConstructionType.House;
		ownerType = OwnerType.citizen;
		familyClass = FamilyClass.D;
		conservationCost = EmperiumConstants.HOUSE_CONSERVATION_COST_D;
		generalCost=0;
		
		this.satisfaction = 100;
		citizens = new ArrayList<Citizen>();
		
		Citizen cit = new Citizen();
		cit.id = 0;
		citizens.add(cit);
		
		cit = new Citizen();
		cit.id = 1;
		citizens.add(cit);
		
		citizenChild = 4;		
	}	
	
	public long getEntertainment() {
		return entertainment;
	}

	public List<Citizen> getCitizens() {
		return citizens;
	}

	public void setCitizenActive(List<Citizen> citizens) {
		this.citizens = citizens;
	}

	public int getCitizenChild() {
		return citizenChild;
	}

	public void setCitizenChild(int citizenChild) {
		this.citizenChild = citizenChild;
	}

	public int getWithaJob() {
		int total = 0;
		
		if(citizens != null) {
			for(Citizen c : this.citizens) {
				if(c.works != -1) {
					total++;
				}
			}
		}
		return total;
	}

	public void setWithaJob(int withaJob) {
//		this.withaJob = withaJob;
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
		
		if(this.conservation <= 0) {
			this.citizens = new ArrayList<Citizen>();
			this.citizenChild = 0;
			this.entertainment=0;//AA
			this.wine=0;//A 
			this.furniture=0;//BA
			this.meat=0;//B
			this.beans=0;//C
			this.rice=0;//D
			this.cloths=0;
		}
		
		if(getHousePopulation() <= 0) {
			this.savings = 0;
		}
		
		calculateSatisfaction();
		calculateBirth(city, gamer);
		calculateAdultAndDeath(city, gamer);
		
		city.increasePopulation(this.getHousePopulation());
		city.searchJobs(citizens);
	}
	
	@Override
	protected void calculateSavingsTaxes(Gamer gamer, City city) {
		
		if(getHousePopulation() <= 0) {
			return;
		}
		
		for(Citizen c : citizens) {
			if(c.salary > 0) {		
				long tax = (city.getCitizenTax()*c.salary)/100;
				c.salary = (int) (c.salary-tax);
				
				this.savings+=c.salary;
				
				city.increaseTreasure(tax);
				gamer.increaseTreasure(tax);
			}			
		}
		
		evalFamilyClassUpgrade(city);
		calculateConsumeGoods(city);
	}
	
	private void evalFamilyClassUpgrade(City city) {
		if(familyClass.equals(FamilyClass.A)) {
			if(city.getSalaryAA() <= savings) {
				this.familyClass = FamilyClass.AA;
				this.conservationCost = EmperiumConstants.HOUSE_CONSERVATION_COST_AA;
			} else {
				this.familyClass = FamilyClass.A;
				this.conservationCost = EmperiumConstants.HOUSE_CONSERVATION_COST_A;
			}
		} else {
			if(city.getSalaryBA() <= savings) {
				this.familyClass = FamilyClass.BA;
				this.conservationCost = EmperiumConstants.HOUSE_CONSERVATION_COST_BA;
			} else if (city.getSalaryB() <= savings) {
				this.familyClass = FamilyClass.B;
				this.conservationCost = EmperiumConstants.HOUSE_CONSERVATION_COST_B;
			} else if(city.getSalaryC() <= savings) {
				this.familyClass = FamilyClass.C;
				this.conservationCost = EmperiumConstants.HOUSE_CONSERVATION_COST_C;
			} else {
				this.familyClass = FamilyClass.D;
				this.conservationCost = EmperiumConstants.HOUSE_CONSERVATION_COST_D;
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
			rice= (int) city.decreaseCloths(cloths);
			beans= (int) city.decreaseBeans(beans);
			meat= (int) city.decreaseMeat(meat);
			furniture= (int) city.decreaseFurniture(furniture);
			wine = (int) city.decreaseWine(wine);
			entertainment= (int) city.decreaseEntertainment(entertainment);
			
			int _cloths = cloths;
			int _rice= rice;
			int _beans= beans;
			int _meat= meat;
			int _furniture= furniture;
			int _wine = wine;
			int _entertainment= entertainment;			
			
			for (Citizen c : citizens) {
				if(_cloths > 0) {
					_cloths--;
					c.wear();
				}
				
				if(_rice > 0) {
					_rice--;
					c.eatRice();
				}
				
				if(_beans > 0) {
					beans--;
					c.eatBeans();
				}
				
				if(_meat> 0) {
					_meat--;
					c.eatMeat();
				}
				
				if(_furniture> 0) {
					_furniture--;
					c.obtainFurniture();
				}
				
				if(_wine> 0) {
					_wine--;
					c.drinkWine();
				}
				
				if(_entertainment> 0) {
					_entertainment--;
					c.entertain();
				}				
			}
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
		
		if(savings <= 0) {
			System.out.println("XIII");
		}
		
		if(price <= 0) {
			System.out.println("XIII");
		}
		
		if(this.savings > price) {
			int val = (int) (this.savings/price);
			ret = val >= getHousePopulation()? getHousePopulation(): val;
		}
		
		return ret;
	}
	
	private void calculateAdultAndDeath(City city, Gamer gamer) {
		ArrayList<Citizen> cits = new ArrayList<Citizen>();
		
		for (Citizen citizen : citizens) {
			if(!citizen.calculateAge()) {
				cits.add(citizen);
			}
		}
		
		citizens = cits;
	}
	
	private void calculateBirth(City city, Gamer gamer) {
		this.citizenChild = 0;
		int exceeded = 0;
		for (Citizen citizen : citizens) {			
			if(this.citizenChild < getMaxPopulation()) {
				this.citizenChild += citizen.procreate(city, gamer);
			} else {
				exceeded+=citizen.procreate(city, gamer);
			}
		}
		
		city.setPopulationExceded(city.getPopulationExceded()+exceeded);
				
	}
	
	public int getHousePopulation() {
		return citizens.size();
	}
	
	public void calculateSatisfaction() {
		//satisfaction
	}
	
	public int getWine() {
		return wine;
	}

	public void setWine(int wine) {
		this.wine = wine;
	}

	public int getFurniture() {
		return furniture;
	}

	public void setFurniture(int furniture) {
		this.furniture = furniture;
	}

	public int getMeat() {
		return meat;
	}

	public void setMeat(int meat) {
		this.meat = meat;
	}

	public int getBeans() {
		return beans;
	}

	public void setBeans(int beans) {
		this.beans = beans;
	}

	public int getRice() {
		return rice;
	}

	public void setRice(int rice) {
		this.rice = rice;
	}

	public int getCloths() {
		return cloths;
	}

	public void setCloths(int cloths) {
		this.cloths = cloths;
	}

	public void setEntertainment(int entertainment) {
		this.entertainment = entertainment;
	}

	public void setCitizens(List<Citizen> citizens) {
		this.citizens = citizens;
	}

	public void printDebug() {
		
		super.printDebug();
		
		System.out.println("Satisfaction:" + satisfaction);
		System.out.println("HousePolulation:" + getHousePopulation());
		System.out.println("CitizenChild:" + citizenChild);
		System.out.println("WithaJob:" + getWithaJob());
		
		System.out.println("rice:" + rice);
		System.out.println("beans:" + beans);
		System.out.println("cloths:" + cloths);
		System.out.println("rice:" + entertainment);
		System.out.println("beans:" + furniture);
		System.out.println("cloths:" + wine);
		System.out.println("cloths:" + meat);
		System.out.println("Class:" + familyClass.name());
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
