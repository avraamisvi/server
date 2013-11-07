package model.city.barracks;

import java.util.List;

import enums.WarriorsConstants;

/**
 * Army to batle other armies or assault a city
 * @author abraao
 *
 */
public class Army {

	List<Group> position1; 
	List<Group> position2; 
	List<Group> position3; 
	List<Group> position4; 
	List<Group> position5;
	
	int formation = WarriorsConstants.PARALLEL;
	
	public List<Group> getPosition1() {
		return position1;
	}
	public void setPosition1(List<Group> position1) {
		this.position1 = position1;
	}
	public List<Group> getPosition2() {
		return position2;
	}
	public void setPosition2(List<Group> position2) {
		this.position2 = position2;
	}
	public List<Group> getPosition3() {
		return position3;
	}
	public void setPosition3(List<Group> position3) {
		this.position3 = position3;
	}
	public List<Group> getPosition4() {
		return position4;
	}
	public void setPosition4(List<Group> position4) {
		this.position4 = position4;
	}
	public List<Group> getPosition5() {
		return position5;
	}
	public void setPosition5(List<Group> position5) {
		this.position5 = position5;
	}
	public int getFormation() {
		return formation;
	}
	public void setFormation(int formation) {
		this.formation = formation;
	}
}
