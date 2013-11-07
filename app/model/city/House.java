package model.city;

import java.util.ArrayList;
import java.util.List;

import model.City;
import model.Gamer;

import org.mongodb.morphia.annotations.Embedded;

import enums.ConstructionType;
import enums.EmperiumConstants;
import enums.FamilyClass;
import enums.OwnerType;

@Embedded
public class House extends Construction{
	
	FamilyClass familyClass;	
	
	int ceramics;//AA
	int wine;//A 
	int furniture;//BA
	int meat;//B
	int beans;//C
	int rice;//D
	int cloths;//D	
	
	List<Citizen> citizens;//adult citizens available to work
	int citizenChild;//child citizens unavailable to work, indice de quantos filhos nascen para virarem força ativa
	
	int satisfaction;//satisfaction level 1-100; 1 = angry, 100 = happy 

	public int exceeded;
	
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
	
	public long getCeramics() {
		return ceramics;
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
		satisfaction = 0;
		if (citizens.size() > 0) {
			for (Citizen cit : citizens) {
				satisfaction += cit.satisfaction;
			}

			satisfaction = satisfaction / citizens.size();
		}

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

	private void updateCitizens(Gamer gamer, City city) {
		
		Citizen [] cits = citizens.toArray(new Citizen[0]);
		List<Citizen> listCitizens = new ArrayList<Citizen>();
		
		this.citizenChild = 0;
		
		satisfaction = 0;
		
		int _cloths = cloths;
		int _rice= rice;
		int _beans= beans;
		int _meat= meat;
		int _furniture= furniture;
		int _wine = wine;
		int _ceramics= ceramics;			
		
		for (Citizen c : cits) {
			
			c.satisfaction = 0;
//			c.familyClass = familyClass;
			
			if(_cloths > 0) {
				_cloths--;
				c.wear();
			}
			
			if(_rice > 0) {
				_rice--;
				c.eatRice();
			}
			
			if(_beans > 0) {
				_beans--;
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
			
			if(_ceramics> 0) {
				_ceramics--;
				c.buyCeramics();
			}
			
			this.citizenChild += c.procreate(gamer, city, this);
			
			if(!c.isDead()) {
				c.id = listCitizens.size();
				listCitizens.add(c);
			} else {
				System.out.println("Cidadão morreu:" + c.id + " Casa:" + this.mapPositionId);
			}
			
			c.calculateAge();
			c.update(gamer, city, this);
			
		}
		
		this.citizens = listCitizens;
		int i = 0;
		for (i = 0; i < this.citizenChild; i++) {
			if(getHousePopulation() >= getMaxPopulation()) {
				break;
			}
			Citizen c = new Citizen();
			c.home = (int) this.mapPositionId;
			c.id = this.citizens.size();
			c.familyClass = familyClass;
			this.citizens.add(c);
			System.out.println("Cidadão nasceu:" + c.id + " Casa:" + this.mapPositionId);
		}
		
		exceeded = this.citizenChild-i; 
	}
	
	@Override
	protected void updateHandle(Gamer gamer, City city) {
		
		if(this.conservation <= 0) {
			this.citizens = new ArrayList<Citizen>();
			this.citizenChild = 0;
			this.ceramics=0;//AA
			this.wine=0;//A 
			this.furniture=0;//BA
			this.meat=0;//B
			this.beans=0;//C
			this.rice=0;//D
			this.cloths=0;
		} else {
			
			calculateConsumeGoods(city);
			
			if(getHousePopulation() <= 0) {
				this.savings = 0;
			}
			
			updateCitizens(gamer,city);
			
			city.increasePopulation(this.getHousePopulation());
			evalFamilyClassUpgrade(city);
		}
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
		ceramics = 0;		
		
		if(savings > 0) {
			Prices prices = new Prices();
			prices.ceramicsPrice = getHousePopulation()*city.getCeramicsPrice();//AA
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
			ceramics= (int) city.decreaseCeramics(ceramics);
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
	 * @param b_ceramics
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
	boolean b_ceramics, Prices prices, City city) {
		
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
		if (b_ceramics) {
			ceramics = calculateGoods(prices.ceramicsPrice);
			decreaseSavings(ceramics * city.getCeramicsPrice());
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
	
//	private void calculateDeath(City city, Gamer gamer) {
//		ArrayList<Citizen> cits = new ArrayList<Citizen>();
//		
//		for (Citizen citizen : citizens) {
//			if(!citizen.calculateAge()) {
//				citizen.id=cits.size();
//				cits.add(citizen);
//			}
//		}
//		
//		citizens = cits;
//	}
	
	public int getHousePopulation() {
		return citizens.size();
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

	public void setCeramics(int entertainment) {
		this.ceramics = entertainment;
	}

	public void setCitizens(List<Citizen> citizens) {
		this.citizens = citizens;
	}

	@Override
	public void setMapPositionId(long mapPositionId) {
		super.setMapPositionId(mapPositionId);
		
		for(Citizen c : citizens) {
			c.home = (int) mapPositionId;
		}
	}
	
	public void printDebug() {
		
		super.printDebug();
		
		System.out.println("Satisfaction:" + getSatisfaction());
		System.out.println("HousePolulation:" + getHousePopulation());
		System.out.println("CitizenChild:" + citizenChild);
		System.out.println("WithaJob:" + getWithaJob());
		
		System.out.println("rice:" + rice);
		System.out.println("beans:" + beans);
		System.out.println("cloths:" + cloths);
		System.out.println("beans:" + furniture);
		System.out.println("wine:" + wine);
		System.out.println("meat:" + meat);
		System.out.println("ceramics:" + ceramics);
		System.out.println("Class:" + familyClass.name());
	}
	
	@Override
	public void updateJobs(Gamer gamer, City city) {
		if(conservation > 0) {
			for (Citizen c : citizens) {
				if(!c.isDead()) {// && !c.isRetired()
//					c.familyClass = familyClass;
					c.searchJob(gamer, city);
				}
			}
		}
	}
	
	class Prices {
		public long ceramicsPrice;
		public long winePrice; 
		public long furniturePrice;
		public long meatPrice;
		public long beansPrice;
		public long ricePrice;
		public long clothsPrice;		
	}
}
