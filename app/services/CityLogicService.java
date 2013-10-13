package services;

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
	
	
	public void processJobs(City city) {
		city.resetJobs();
		
		for (Construction constru : city.getConstructions() ) {			
			if(constru != null)
				constru.updateJobs(city);
		}
		
	}
	
	/**
	 * Runs every 24 hours
	 * @param city
	 * @param gamer
	 */
	public void processEconomyTaxes(City city) {
		
		processJobs(city);
		
		//needed for recreate the currently treasure contribution
		city.resetTreasure();
		city.resetProduction();
		city.resetDemand();
		city.setPopulation(0);//inicializa para calcular
		city.setMaxPopulation(0);//inicializa para calcular		
		
		for (Construction constru : city.getConstructions() ) {	
				
			if (constru != null) {
				constru.update(gamer, city);
				constru.printDebug();
			}
		}
		
		gamer.increaseExpenses(city.getTreasureExpenses());
		
		gamer.increaseBeans(city.getBeans());
		gamer.increaseCloths(city.getCloths());
		gamer.increaseEntertainment(city.getEntertainment());
		gamer.increaseFurniture(city.getFurniture());
		gamer.increaseMeat(city.getMeat());
		gamer.increaseWine(city.getWine());
		gamer.increaseRice(city.getRice());
		
		System.out.println("-------------CITY ID ------------------");
		System.out.println("City Treasure Contribution: " + city.getTreasureContribution());
		System.out.println("City Treasure Expenses: " + city.getTreasureExpenses());		
		System.out.println("Uneployed: " + city.getUneployed());		
		System.out.println("Rice Demand: " + city.getDemandRice());
		System.out.println("Beans Demand: " + city.getDemandBeans());
		System.out.println("Cloths Demand: " + city.getDemandCloths());
		System.out.println("Entertainment Demand: " + city.getDemandEntertainment());
		System.out.println("Furniture Demand: " + city.getDemandFurniture());
		System.out.println("Wine Demand: " + city.getDemandWine());
	}
}
