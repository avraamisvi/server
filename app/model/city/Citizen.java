package model.city;

import model.City;
import model.Gamer;
import enums.FamilyClass;

public class Citizen {
	int id;
	int home;//house he lives
	int age = 10;//every citizen borns with 20 and die at 80
	int hungry;//if hungry reaches 100 he dies, next turn dies
	int works = -1;//his job place if -1 the he is not working
	int salary;//10.000
	
	static int CLASS_AA_FACTOR = (100/7);
	static int CLASS_A_FACTOR = (100/6);
	static int CLASS_BA_FACTOR = (100/5);
	static int CLASS_B_FACTOR = (100/4);
	static int CLASS_C_FACTOR = (100/3);
	static int CLASS_D_FACTOR = (100/2);
	
	FamilyClass familyClass = FamilyClass.D;
	int satisfaction = 100;
	
	public void initTurn() {
		hungry = 100;
		satisfaction = 0;
//		works = 0;??
//		salary=0;??
	}
	
	public void evalFamilyClassUpgrade(City city, House house) {
		if(familyClass.equals(FamilyClass.A)) {
			if(city.getSalaryAA() <= salary) {
				this.familyClass = FamilyClass.AA;
			} else {
				this.familyClass = FamilyClass.A;
			}
		} else {
			if(city.getSalaryBA() <= salary) {
				this.familyClass = FamilyClass.BA;
			} else if (city.getSalaryB() <= salary) {
				this.familyClass = FamilyClass.B;
			} else if(city.getSalaryC() <= salary) {
				this.familyClass = FamilyClass.C;
			} else {
				this.familyClass = FamilyClass.D;
			}
		}
	}	
	
	/**
	 * 
	 * @return true if die
	 */
	public boolean calculateAge() {
		age+=10;		
		return age > 70;
	}
	
	public int procreate(Gamer gamer, City city, House house) {
		int child = 2;
		
		if(satisfaction <= 50) {
			child--;
		}
		
		if(satisfaction <= 34) {
			child--;
		}		
		
		return child;
	}
	
	public void eatRice(){
		if(familyClass.equals(FamilyClass.D)) {
			hungry = 0;
			satisfaction += CLASS_D_FACTOR;
		} else if(familyClass.equals(FamilyClass.C)) {
			hungry = 0;
			satisfaction += CLASS_C_FACTOR;			
		} else if(familyClass.equals(FamilyClass.B)) {
			hungry = 0;
			satisfaction += CLASS_B_FACTOR;
		} else if(familyClass.equals(FamilyClass.BA)) {
			hungry = 0;
			satisfaction += CLASS_BA_FACTOR;			
		} else if(familyClass.equals(FamilyClass.A)) {
			hungry = 0;
			satisfaction += CLASS_A_FACTOR;
		} else if(familyClass.equals(FamilyClass.AA)) {
			hungry = 0;
			satisfaction += CLASS_AA_FACTOR;
		}
	}
	
	public void eatBeans(){
		if(familyClass.equals(FamilyClass.C)) {
			hungry = 0;
			satisfaction += CLASS_C_FACTOR;			
		} else if(familyClass.equals(FamilyClass.B)) {
			hungry = 0;
			satisfaction += CLASS_B_FACTOR;
		} else if(familyClass.equals(FamilyClass.BA)) {
			hungry = 0;
			satisfaction += CLASS_BA_FACTOR;			
		} else if(familyClass.equals(FamilyClass.A)) {
			hungry = 0;
			satisfaction += CLASS_A_FACTOR;
		} else if(familyClass.equals(FamilyClass.AA)) {
			hungry = 0;
			satisfaction += CLASS_AA_FACTOR;
		}
	}
	
	public void eatMeat(){
		
		if(familyClass.equals(FamilyClass.B)) {
			hungry = 0;
			satisfaction += CLASS_B_FACTOR;
		} else if(familyClass.equals(FamilyClass.BA)) {
			hungry = 0;
			satisfaction += CLASS_BA_FACTOR;			
		} else if(familyClass.equals(FamilyClass.A)) {
			hungry = 0;
			satisfaction += CLASS_A_FACTOR;
		} else if(familyClass.equals(FamilyClass.AA)) {
			hungry = 0;
			satisfaction += CLASS_AA_FACTOR;
		}
	}	
	
	//dish
	public void obtainFurniture(){
		
		if(familyClass.equals(FamilyClass.BA)) {
			satisfaction += CLASS_BA_FACTOR;			
		} else if(familyClass.equals(FamilyClass.A)) {
			satisfaction += CLASS_A_FACTOR;
		} else if(familyClass.equals(FamilyClass.AA)) {
			satisfaction += CLASS_AA_FACTOR;
		}
	}
	
	public void drinkWine(){
		
		if(familyClass.equals(FamilyClass.A)) {
			satisfaction += CLASS_A_FACTOR;
		} else if(familyClass.equals(FamilyClass.AA)) {
			satisfaction += CLASS_AA_FACTOR;
		}
	}
	
	public void buyCeramics(){
		
		if(familyClass.equals(FamilyClass.AA)) {
			satisfaction += CLASS_AA_FACTOR;
		}
	}	
	
	public void wear() {
		if(familyClass.equals(FamilyClass.D)) {
			satisfaction += CLASS_D_FACTOR;
		} else if(familyClass.equals(FamilyClass.C)) {
			satisfaction += CLASS_C_FACTOR;			
		} else if(familyClass.equals(FamilyClass.B)) {
			satisfaction += CLASS_B_FACTOR;
		} else if(familyClass.equals(FamilyClass.BA)) {
			satisfaction += CLASS_BA_FACTOR;			
		} else if(familyClass.equals(FamilyClass.A)) {
			satisfaction += CLASS_A_FACTOR;
		} else if(familyClass.equals(FamilyClass.AA)) {
			satisfaction += CLASS_AA_FACTOR;
		}		
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getHome() {
		return home;
	}
	public void setHome(int home) {
		this.home = home;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public int getHungry() {
		return hungry;
	}
	public void setHungry(int hungry) {
		this.hungry = hungry;
	}
	public int getWorks() {
		return works;
	}
	public void setWorks(int works) {
		this.works = works;
	}
	public int getSalary() {
		return salary;
	}
	public void setSalary(int salary) {
		this.salary = salary;
	}
	public FamilyClass getFamilyClass() {
		return familyClass;
	}
	public void setFamilyClass(FamilyClass familyClass) {
		this.familyClass = familyClass;
	}
	
	public boolean isDead() {
		
		if(hungry >= 100 || age > 70) {
			return true;
		}
		
		return false;
	}
	
	public boolean isRetired() {
		return age > 60;
	}
	
	public void update(Gamer gamer, City city, House house) {
		
		if(!isDead()) {
			this.familyClass = house.familyClass;
			
			calculateSavingsTaxes(gamer, city, house);
			//c.setSalary(0);
			//c.setWorks(0);			
		}
		
		if(satisfaction > 100) {
			satisfaction = 100;
		}
	}
	
	public void searchJob(Gamer gamer, City city) {
		city.searchJob(gamer, this);
	}
	
	protected void calculateSavingsTaxes(Gamer gamer, City city, House house) {
		
		if(salary > 0) {		
			long tax = (city.getCitizenTax()*salary)/100;
			salary = (int) (salary-tax);
			
			house.savings+=salary;
			
			city.increaseTreasure(tax);
			gamer.increaseTreasure(tax);
		}
		evalFamilyClassUpgrade(city, house);
	}		
}
