package model;

import java.util.List;

import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Embedded;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;

import enums.FamilyClass;
import model.city.Citizen;
import model.city.CityMap;
import model.city.Construction;
import model.city.Constructions;

@Entity
public class City {
	
	/**
	 * Gamer Id
	 */
	ObjectId gamerId;//FIXME
	
	String name;
	
	/**
	 * Own Id
	 */
	@Id
	ObjectId id;
	
	/**
	 * How much money it is making
	 */
	long treasureContribution;
	
	/**
	 * How much is being spent
	 */
	long treasureExpenses;
	
	/**
	 * Percent
	 */
	long industryTax = 10;
	
	/**
	 * Percent
	 */
	long citizenTax = 10;
	
	int maxPopulation;
	
	int population;
	
	int populationExceded;
	
	long uneployed;
	
	/**
	 * Jobs available on a city.
	 */
	long jobs = 0;
		
	/**
	 * Goods price
	 */	
	long entertainmentPrice = 500;//AA
	long winePrice = 300;//A 
	long furniturePrice = 200;//BA
	long meatPrice = 200;//B
	long beansPrice = 100;//C
	long ricePrice = 50;//D
	long clothsPrice = 10;//D	
	
	/**
	 * Goods total production (from this city)
	 */
	int entertainment = 1000;//AA
	int wine = 1000;//A
	int furniture = 200;//BA
	int meat = 1000;//B
	int beans = 1000;//C
	int rice = 1000;//D
	int cloths = 1000;//D	
	
	
	/**
	 * Total monthly production
	 */
	long productionEntertainment = 0;//AA
	long productionWine = 0;//A
	long productionFurniture = 0;//BA
	long productionMeat = 0;//B
	long productionBeans = 0;//C
	long productionRice = 0;//D
	long productionCloths = 0;//D		
	
	/**
	 * Total monthly demanding
	 */
	long demandEntertainment = 0;//AA
	long demandWine = 0;//A
	long demandFurniture = 0;//BA
	long demandMeat = 0;//B
	long demandBeans = 0;//C
	long demandRice = 0;//D
	long demandCloths = 0;//D
	
	/**
	 * Salaries, the salaries suffer variations according to the economy balance.
	 * TODO for while fixed
	 */
	int SalaryAA = 10000;
	int SalaryA = 4000;
	int SalaryBA = 1000;
	int SalaryB = 800;
	int SalaryC = 300;
	int SalaryD = 100;	
	
	CityMap map = new CityMap();
	
	@Embedded
	Constructions constructions;
	
	public ObjectId getGamerId() {
		return gamerId;
	}

	public void setGamerId(ObjectId gamerId) {
		this.gamerId = gamerId;
	}

	public ObjectId getId() {
		return id;
	}

	public void setId(ObjectId id) {
		this.id = id;
	}

	public CityMap getMap() {
		return map;
	}

	public void setMap(CityMap map) {
		this.map = map;
	}
	
	public long getSalaryByFamilyClass(FamilyClass familyClass) {
		long gain = 0;
		
		switch (familyClass) {
		case AA:
			gain = getSalaryAA();
			break;
		case A:
			gain = getSalaryA();
			break;
		case BA:
			gain = getSalaryBA();
			break;
		case B:
			gain = getSalaryB();
			break;
		case C:
			gain = getSalaryC();
			break;								
		case D:
			gain = getSalaryD();
			break;
		}
		
		return gain;
	}
	
	public int getSalaryAA() {
		return SalaryAA;
	}

	public void setSalaryAA(int salaryAA) {
		SalaryAA = salaryAA;
	}

	public int getSalaryA() {
		return SalaryA;
	}

	public void setSalaryA(int salaryA) {
		SalaryA = salaryA;
	}

	public int getSalaryBA() {
		return SalaryBA;
	}

	public void setSalaryBA(int salaryBA) {
		SalaryBA = salaryBA;
	}

	public int getSalaryB() {
		return SalaryB;
	}

	public void setSalaryB(int salaryB) {
		SalaryB = salaryB;
	}

	public int getSalaryC() {
		return SalaryC;
	}

	public void setSalaryC(int salaryC) {
		SalaryC = salaryC;
	}

	public int getSalaryD() {
		return SalaryD;
	}

	public void setSalaryD(int salaryD) {
		SalaryD = salaryD;
	}	
	
	public long getIndustryTax() {
		return industryTax;
	}

	public void setIndustryTax(long industryTax) {
		this.industryTax = industryTax;
	}

	public long getCitizenTax() {
		return citizenTax;
	}

	public void setCitizenTax(long citizenTax) {
		this.citizenTax = citizenTax;
	}
	
	public void resetTreasure() {
		treasureContribution = 0;
		treasureExpenses = 0;
	}
	
	public void increaseExpenses(long value) {
		treasureExpenses = treasureExpenses + value; 		
	}
	
	public void increaseTreasure(long value) {
		 treasureContribution += value;
	}
	
	public long getTreasureContribution() {
		return treasureContribution;
	}
	
	public long getTreasureExpenses() {
		return treasureExpenses;
	}

	public long getEntertainmentPrice() {
		return entertainmentPrice;
	}

	public void setEntertainmentPrice(long entertainmentPrice) {
		this.entertainmentPrice = entertainmentPrice;
	}

	public long getWinePrice() {
		return winePrice;
	}

	public void setWinePrice(long winePrice) {
		this.winePrice = winePrice;
	}

	public long getMeatPrice() {
		return meatPrice;
	}

	public void setMeatPrice(long meatPrice) {
		this.meatPrice = meatPrice;
	}

	public long getBeansPrice() {
		return beansPrice;
	}

	public void setBeansPrice(long beansPrice) {
		this.beansPrice = beansPrice;
	}

	public long getRicePrice() {
		return ricePrice;
	}

	public void setRicePrice(long ricePrice) {
		this.ricePrice = ricePrice;
	}

	public long getClothsPrice() {
		return clothsPrice;
	}

	public void setClothsPrice(long clothsPrice) {
		this.clothsPrice = clothsPrice;
	}

	//GOODS
	
	public int getEntertainment() {
		return entertainment;
	}

	public void setEntertainment(int entertainment) {
		this.entertainment = entertainment;
	}
	
	public void increaseEntertainment(long entertainment) {
		this.entertainment += entertainment;
	}
	
	public long decreaseEntertainment(long entertainment) {
		long ret = 0;
		
		demandEntertainment+=entertainment;
		
		if(this.entertainment > entertainment) {
			this.entertainment -= entertainment;
			ret = entertainment;
		} else {
			ret = this.entertainment;
			this.entertainment=0;
		}
		
		return ret;			
	}

	public int getWine() {
		return wine;
	}

	public void setWine(int wine) {
		this.wine = wine;
	}
	
	public void increaseWine(long wine) {
		this.wine += wine;
	}
	
	public long decreaseWine(long wine) {
		long ret = 0;
		
		demandWine+=wine;
		
		if(this.wine > wine) {
			this.wine -= wine;
			ret = wine;
		} else {
			ret = this.wine;
			this.wine=0;
		}
		
		return ret;				
	}

	public long getMeat() {
		return meat;
	}

	public void setMeat(int meat) {
		this.meat = meat;
	}
	
	public void increaseMeat(long meat) {
		this.meat += meat;
	}
	
	public long decreaseMeat(long meat) {
		
		long ret = 0;
		
		demandMeat+=meat;
		
		if(this.meat > meat) {
			this.meat -= meat;
			ret = meat;
		} else {
			ret = this.meat;
			this.meat=0;
		}
		
		return ret;
	}

	public long getBeans() {
		return beans;
	}

	public void setBeans(int beans) {
		this.beans = beans;
	}
	
	public void increaseBeans(long beans) {
		this.beans += beans;
	}
	
	public long decreaseBeans(long beans) {
		long ret = 0;
		
		if(this.beans > beans) {
			this.beans -= beans;
			ret = beans;
		} else {
			ret = this.beans;
			this.beans=0;
		}
		
		return ret;		
	}

	public long getRice() {
		return rice;
	}

	public void setRice(int rice) {
		this.rice = rice;
	}
	
	public void increaseRice(long rice) {
		this.rice += rice;
	}
	
	public int decreaseRice(int rice) {
		int ret = 0;
		
		demandRice+=rice;
		
		if(this.rice > rice) {
			this.rice -= rice;
			ret = rice;
		} else {
			ret = this.rice;
			this.rice=0;
		}
		
		return ret;			
	}

	public long getCloths() {
		return cloths;
	}

	public void setCloths(int cloths) {
		this.cloths = cloths;
	}
	
	public void increaseCloths(long cloths) {
		this.cloths += cloths;
	}
	
	public long decreaseCloths(long cloths) {
		long ret = 0;
		
		demandCloths+=cloths;
		
		if(this.cloths > cloths) {
			this.cloths -= cloths;
			ret = cloths;
		} else {
			ret = this.cloths;
			this.cloths=0;
		}
		
		return ret;	
	}

	public void setTreasureContribution(long treasureContribution) {
		this.treasureContribution = treasureContribution;
	}

	public void setTreasureExpenses(long treasureExpenses) {
		this.treasureExpenses = treasureExpenses;
	}

	public long getFurniturePrice() {
		return furniturePrice;
	}

	public void setFurniturePrice(long furniturePrice) {
		this.furniturePrice = furniturePrice;
	}

	public long getFurniture() {
		return furniture;
	}

	public void setFurniture(int furniture) {
		this.furniture = furniture;
	}
	
	public void increaseFurniture(long furniture) {
		this.furniture += furniture;
	}
	
	public long decreaseFurniture(long furniture) {
		long ret = 0;
		
		demandFurniture+=furniture;
		
		if(this.furniture > furniture) {
			this.furniture -= furniture;
			ret = furniture;
		} else {
			ret = this.furniture;
			this.furniture=0;
		}
		
		return ret;			
	}

	public int getMaxPopulation() {
		return maxPopulation;
	}

	public void setMaxPopulation(int maxPopulation) {
		this.maxPopulation = maxPopulation;
	}

	public int getPopulation() {
		return population;
	}

	public void setPopulation(int population) {
		this.population = population;
	}
	
	public void increasePopulation(int population) {
		this.population += population;
	}
	
	public void increaseUneployed(int une) {
		this.uneployed += une;
	}
	
	public long decreaseUneployed(long une) {
		long ret = 0;
		
		if(this.uneployed > une) {
			this.uneployed -= une;
			ret = une;
		} else {
			ret = this.uneployed;
			this.uneployed = 0;
		}
		return ret;
	}
	
	public void increaseJobs(long une) {
		this.jobs += une;
	}
	
	public long decreaseJobs(int une) {
		long ret = 0;
		
		if(this.jobs > une) {
			this.jobs -= une;
			ret = une;
		} else {
			ret = this.jobs;
			this.jobs = 0;
		}
		return ret;
	}

	public void resetJobs() {
		uneployed=0;
		jobs=0;		
	}

	public void searchJobs(List<Citizen> citizens) {
		List<Construction> cons = constructions.toList();
		for(Citizen c : citizens) {
			c.setSalary(0);
			c.setWorks(0);
			for(Construction con : cons) {
				if(con.haveJob(c))
					break;
			}
		}
	}
	
	public int getPopulationExceded() {
		return populationExceded;
	}

	public void setPopulationExceded(int populationExceded) {
		this.populationExceded = populationExceded;
	}

	public void increasePopulationExceded(int populationExceded) {
		this.populationExceded += populationExceded;
	}	
	
	public long getUneployed() {
		return uneployed;
	}

	public void setUneployed(long uneployed) {
		this.uneployed = uneployed;
	}

	public long getJobs() {
		return jobs;
	}

	public void setJobs(long jobs) {
		this.jobs = jobs;
	}
	
	public void increaseProductionEntertainment(int production) {
		productionEntertainment += production;
	}
	public void increaseProductionWine(int production) {
		productionWine += production;
	}
	public void increaseProductionFurniture(int production) {
		productionFurniture += production;
	}
	public void increaseProductionMeat(int production) {
		productionMeat+= production;
	}
	public void increaseProductionBeans(int production) {
		productionBeans+= production;
	}
	public void increaseProductionRice(int production) {
		productionRice+= production;
	}
	public void increaseProductionCloths(int production) {
		productionCloths += production;
	}
	
	public void resetProduction() {
		productionEntertainment = 0;//AA
		productionWine = 0;//A
		productionFurniture = 0;//BA
		productionMeat = 0;//B
		productionBeans = 0;//C
		productionRice = 0;//D
		productionCloths = 0;//D
	}
	
	public void resetDemand() {
		demandEntertainment = 0;//AA
		demandWine = 0;//A
		demandFurniture = 0;//BA
		demandMeat = 0;//B
		demandBeans = 0;//C
		demandRice = 0;//D
		demandCloths = 0;//D
	}	
	
	public void calculateGoodsPrices() {
		//TODO
	}
	
	public void calculateSalaries() {
		//TODO
	}

	public long getProductionEntertainment() {
		return productionEntertainment;
	}

	public void setProductionEntertainment(long productionEntertainment) {
		this.productionEntertainment = productionEntertainment;
	}

	public long getProductionWine() {
		return productionWine;
	}

	public void setProductionWine(long productionWine) {
		this.productionWine = productionWine;
	}

	public long getProductionFurniture() {
		return productionFurniture;
	}

	public void setProductionFurniture(long productionFurniture) {
		this.productionFurniture = productionFurniture;
	}

	public long getProductionMeat() {
		return productionMeat;
	}

	public void setProductionMeat(long productionMeat) {
		this.productionMeat = productionMeat;
	}

	public long getProductionBeans() {
		return productionBeans;
	}

	public void setProductionBeans(long productionBeans) {
		this.productionBeans = productionBeans;
	}

	public long getProductionRice() {
		return productionRice;
	}

	public void setProductionRice(long productionRice) {
		this.productionRice = productionRice;
	}

	public long getProductionCloths() {
		return productionCloths;
	}

	public void setProductionCloths(long productionCloths) {
		this.productionCloths = productionCloths;
	}

	public long getDemandEntertainment() {
		return demandEntertainment;
	}

	public void setDemandEntertainment(long demandEntertainment) {
		this.demandEntertainment = demandEntertainment;
	}

	public long getDemandWine() {
		return demandWine;
	}

	public void setDemandWine(long demandWine) {
		this.demandWine = demandWine;
	}

	public long getDemandFurniture() {
		return demandFurniture;
	}

	public void setDemandFurniture(long demandFurniture) {
		this.demandFurniture = demandFurniture;
	}

	public long getDemandMeat() {
		return demandMeat;
	}

	public void setDemandMeat(long demandMeat) {
		this.demandMeat = demandMeat;
	}

	public long getDemandBeans() {
		return demandBeans;
	}

	public void setDemandBeans(long demandBeans) {
		this.demandBeans = demandBeans;
	}

	public long getDemandRice() {
		return demandRice;
	}

	public void setDemandRice(long demandRice) {
		this.demandRice = demandRice;
	}

	public long getDemandCloths() {
		return demandCloths;
	}

	public void setDemandCloths(long demandCloths) {
		this.demandCloths = demandCloths;
	}

	public Constructions getConstructions() {
		return constructions;
	}
	
	public void setConstructions(Constructions constructions) {
		this.constructions = constructions;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
