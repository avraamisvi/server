package model;

import java.util.List;

import model.city.Citizen;
import model.city.CityMap;
import model.city.Construction;
import model.city.Constructions;
import model.city.House;

import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Embedded;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import controllers.serializers.ObjectIdString;
import enums.ConstructionType;
import enums.EmperiumConstants;
import enums.FamilyClass;

@Entity
public class City {
	
	/**
	 * City walls level
	 */
	int wallsLevel;
	
	/**
	 * Gamer Id
	 */
	ObjectId gamerId;//FIXME
	
	String name;
	
	/**
	 * Own Id
	 */
	@Id
	@JsonSerialize(using=ObjectIdString.class)
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
	long industryTax = 30;
	
	long educationalTax = 10;
	
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
	long ceramicsPrice = 100;//AA
	long winePrice = 37;//A 
	long furniturePrice = 14;//BA
	long meatPrice = 11;//B
	long beansPrice = 8;//C
	long ricePrice = 3;//D
	long clothsPrice = 1;//D
	
	long grapesPrice = 8;	
	long cottonPrice = 8;	
	
	/**
	 * Goods total production (from this city)
	 */
	int ceramics = 0;//AA
	int wine = 0;//A
	int furniture = 0;//BA
	int meat = 0;//B
	int beans = 0;//C
	int rice = 100;//D
	int cloths = 100;//D	
	
	/**
	 * Total monthly production
	 */
	long productionCeramics = 0;//AA
	long productionWine = 0;//A
	long productionFurniture = 0;//BA
	long productionMeat = 0;//B
	long productionBeans = 0;//C
	long productionRice = 0;//D
	long productionCloths = 0;//D	
	
	/**
	 * Total monthly demanding
	 */
	long demandCeramics = 0;//AA
	long demandWine = 0;//A
	long demandFurniture = 0;//BA
	long demandMeat = 0;//B
	long demandBeans = 0;//C
	long demandRice = 0;//D
	long demandCloths = 0;//D
	
	long demandCotton;
	long demandGrapes;
	long demandWood;
	
	/**
	 * Salaries, the salaries suffer variations according to the economy balance.
	 * TODO for while fixed
	 */
	int SalaryAA = 1620;
	int SalaryA = 540;
	int SalaryBA = 180;
	int SalaryB = 90;
	int SalaryC = 30;
	int SalaryD = 10;	
	
	CityMap map = new CityMap();
	
	@Embedded
	Constructions constructions;

	private int demandSulfur;
	private int demandIron;
	private int demandRock;

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

	public long getCeramicsPrice() {
		return ceramicsPrice;
	}

	public void setCeramicsPrice(long entertainmentPrice) {
		this.ceramicsPrice = entertainmentPrice;
	}

	public long getGrapesPrice() {
		return grapesPrice;
	}

	public void setGrapesPrice(long grapesPrice) {
		this.grapesPrice = grapesPrice;
	}

	public long getCottonPrice() {
		return cottonPrice;
	}

	public void setCottonPrice(long cottonPrice) {
		this.cottonPrice = cottonPrice;
	}

	public long getDemandGrapes() {
		return demandGrapes;
	}

	public void setDemandGrapes(long demandGrapes) {
		this.demandGrapes = demandGrapes;
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
	
	public int getCeramics() {
		return ceramics;
	}

	public void setCeramics(int entertainment) {
		this.ceramics = entertainment;
	}
	
	public void increaseCeramics(long entertainment) {
		this.ceramics += entertainment;
	}
	
	public long decreaseCeramics(long entertainment) {
		long ret = 0;
		
		demandCeramics+=entertainment;
		
		if(this.ceramics > entertainment) {
			this.ceramics -= entertainment;
			ret = entertainment;
		} else {
			ret = this.ceramics;
			this.ceramics=0;
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

	public void increaseCotton(Gamer gamer, int cotton) {
		gamer.increaseCotton(cotton);
	}
	
	public int decreaseCotton(Gamer gamer,int cotton) {
		
		demandCotton+=cotton;
		
		return gamer.decreaseCotton(cotton);	
	}
	
	public void increaseGrapes(Gamer gamer,int grapes) {
		gamer.increaseGrapes(grapes);
	}
	
	public int decreaseGrapes(Gamer gamer,int grapes) {
		demandGrapes+=grapes;
		
		return gamer.decreaseGrapes(grapes);	
	}
	
	public int decreaseWood(Gamer gamer,int wood) {
		demandWood+=wood;
		
		return gamer.decreaseWood(wood);	
	}
	
	public int decreaseSulfur(Gamer gamer,int val) {
		demandSulfur+=val;
		
		return gamer.decreaseSulfur(val);	
	}
	
	public int decreaseIron(Gamer gamer,int val) {
		demandIron+=val;
		
		return gamer.decreaseIron(val);	
	}		
	
	public int decreaseRock(Gamer gamer,int val) {
		demandRock+=val;
		
		return gamer.decreaseRock(val);	
	}		
	
	public long getDemandCotton() {
		return demandCotton;
	}
	
	public void setDemandCotton(long demandCotton) {
		this.demandCotton = demandCotton;
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
//		long ret = 0;
//		
//		if(this.jobs > une) {
//			this.jobs -= une;
//			ret = une;
//		} else {
//			ret = this.jobs;
//			this.jobs = 0;
//		}
		return 0;
	}

	public void resetJobs() {
		uneployed=0;
		jobs=0;		
	}
	
	public void searchJob(Gamer gamer, Citizen citizen) {
		List<Construction> cons = constructions.toList();
		citizen.setSalary(0);
		citizen.setWorks(EmperiumConstants.CITIZEN_NOT_WORK);
		for(Construction con : cons) {
			if(con.getAvaliableJobs() > 0) {
				if(con.haveJob(gamer, this, citizen)) {
					System.out.println("Cidadão empregado:" + citizen.getId() + " Casa:" + citizen.getHome());
					break;
				}
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
	
	public void increaseProductionCeramics(int production) {
		productionCeramics += production;
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
		productionCeramics = 0;//AA
		productionWine = 0;//A
		productionFurniture = 0;//BA
		productionMeat = 0;//B
		productionBeans = 0;//C
		productionRice = 0;//D
		productionCloths = 0;//D
	}
	
	public void resetDemand() {
		demandCeramics = 0;//AA
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

	public long getProductionCeramics() {
		return productionCeramics;
	}

	public void setProductionCeramics(long productionEntertainment) {
		this.productionCeramics = productionEntertainment;
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

	public long getDemandCeramics() {
		return demandCeramics;
	}

	public void setDemandCeramics(long demandEntertainment) {
		this.demandCeramics = demandEntertainment;
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
	
	//TODO melhorar	
	public void updateIndices() {
		List<Construction> cons = getConstructions().toList();
		resetJobs();
		maxPopulation = 0;
		population = 0;
		for(Construction c : cons) {
			if(c==null)
				continue;
			if(c.getType().equals(ConstructionType.House)) {
				population += ((House)c).getHousePopulation();
				maxPopulation += ((House)c).getMaxPopulation();
			}
			jobs += c.getAvaliableJobs();
		}
		
		uneployed = Math.abs(jobs - population);
	}

	public long getDemandWood() {
		return demandWood;
	}

	public void setDemandWood(long demandWood) {
		this.demandWood = demandWood;
	}

	public int getDemandSulfur() {
		return demandSulfur;
	}

	public void setDemandSulfur(int demandSulfur) {
		this.demandSulfur = demandSulfur;
	}

	public int getDemandIron() {
		return demandIron;
	}

	public void setDemandIron(int demandIron) {
		this.demandIron = demandIron;
	}

	public int getDemandRock() {
		return demandRock;
	}

	public void setDemandRock(int demandRock) {
		this.demandRock = demandRock;
	}

	public long getEducationalTax() {
		return educationalTax;
	}

	public void setEducationalTax(long educationalTax) {
		this.educationalTax = educationalTax;
	}

	public int getWallsLevel() {
		return wallsLevel;
	}

	public void setWallsLevel(int wallsLevel) {
		this.wallsLevel = wallsLevel;
	}
}
