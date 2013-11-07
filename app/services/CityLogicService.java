package services;

import java.util.List;

import model.City;
import model.Gamer;
import model.city.Construction;

/**
 * Process the city's logic
 * 
 * @author abraao
 *
 */
public class CityLogicService {
	
	Gamer gamer;
	
	public CityLogicService(Gamer gamer) {
		this.gamer = gamer;
	}
	
	public void processJobs(Gamer gamer, City city) {
		city.resetJobs();
		List<Construction> construs = city.getConstructions().toList();
		for (Construction constru :  construs) {
			try {
				constru.updateJobs(gamer, city);
			} catch(Exception e) {
				e.printStackTrace();//FIXME this
			}
		}
////		city.updateIndices();
////		
////		printDebug(city);
//		System.out.println("==============================================");
	}
	
	/**
	 * Runs every 24 hours
	 * @param city
	 * @param gamer
	 * @throws Exception 
	 */
	public void processEconomyTaxes(Gamer gamer, City city) throws Exception {
				
		gamer.resetPopulation();
		gamer.resetGoods();
		gamer.resetExpenses();
		processJobs(gamer, city);
		
		//needed for recreate the currently treasure contribution
		city.resetTreasure();
		city.resetProduction();
		city.resetDemand();
		city.setPopulation(0);//inicializa para calcular
		city.setMaxPopulation(0);//inicializa para calcular		
		
		List<Construction> construs = city.getConstructions().toList();
		for (Construction constru :  construs) {
			if (constru != null) {
				try {
					constru.update(gamer, city);
					constru.printDebug();
				} catch(Exception e) {
					e.printStackTrace();//FIXME
				}
			}
		}
		
		try {
			city.updateIndices();
		} catch(Exception e) {
			e.printStackTrace();//FIXME
		}

		printDebug(city);
		
		//creating the indices
		gamer.increasePopulation(city.getPopulation());
		gamer.increaseExpenses(city.getTreasureExpenses());
		gamer.increaseBeans(city.getBeans());
		gamer.increaseCloths(city.getCloths());
		gamer.increaseCeramics(city.getCeramics());
		gamer.increaseFurniture(city.getFurniture());
		gamer.increaseMeat(city.getMeat());
		gamer.increaseWine(city.getWine());
		gamer.increaseRice(city.getRice());
		
	}
	
	public void printDebug(City city) {
		System.out.println("-------------CITY ID ------------------");
		System.out.println("City Treasure Contribution: " + city.getTreasureContribution());
		System.out.println("City Treasure Expenses: " + city.getTreasureExpenses());		
		System.out.println("Uneployed: " + city.getUneployed());		
		System.out.println("Rice Demand: " + city.getDemandRice());
		System.out.println("Beans Demand: " + city.getDemandBeans());
		System.out.println("Cloths Demand: " + city.getDemandCloths());
		System.out.println("Entertainment Demand: " + city.getDemandCeramics());
		System.out.println("Furniture Demand: " + city.getDemandFurniture());
		System.out.println("Wine Demand: " + city.getDemandWine());
		
		System.out.println("Wine: " + city.getWine());
		System.out.println("Rice: " + city.getRice());
		System.out.println("Beans: " + city.getBeans());
		System.out.println("Cloths: " + city.getCloths());
		System.out.println("Ceramics: " + city.getCeramics());
		System.out.println("Furniture: " + city.getFurniture());
		
		System.out.println("Population: " + city.getPopulation());
		System.out.println("Jobs: " + city.getJobs());
		
		if(city.getPopulation() < city.getJobs())
			System.out.println("Vagas: " + (city.getJobs() - city.getPopulation()));
		else		
			System.out.println("Uneployed: " + (city.getPopulation() - city.getJobs()));		
	}
}
