package model.messages;

import model.City;
import model.Gamer;
import model.city.Construction;

public class CreateWarriorReplyMessage{
	
	public int iron = 0;
	public int clay = 0;
	public int wood = 0;
	public int sulfur = 0;
	public int rock = 0;
	public int cotton = 0;
	public int grapes = 0;
	public int knowledge = 0;
	public Boolean created = false;
	public Boolean full = false;
	public Construction construction;
	public City city;
	public Gamer gamer;
	
	public String getName() {
		return "created_warrior";
	}

	public void setName(String name) {
		// TODO Auto-generated method stub
	}

	public int getIron() {
		return iron;
	}

	public void setIron(int iron) {
		this.iron = iron;
	}

	public int getClay() {
		return clay;
	}

	public void setClay(int clay) {
		this.clay = clay;
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

	public int getCotton() {
		return cotton;
	}

	public void setCotton(int cotton) {
		this.cotton = cotton;
	}

	public int getGrapes() {
		return grapes;
	}

	public void setGrapes(int grapes) {
		this.grapes = grapes;
	}

	public int getKnowledge() {
		return knowledge;
	}

	public void setKnowledge(int knowledge) {
		this.knowledge = knowledge;
	}
	public Boolean getCreated() {
		return created;
	}
	public void setCreated(Boolean created) {
		this.created = created;
	}
	public Construction getConstruction() {
		return construction;
	}
	public void setConstruction(Construction construction) {
		this.construction = construction;
	}
	public City getCity() {
		return city;
	}
	public void setCity(City city) {
		this.city = city;
	}
	public Gamer getGamer() {
		return gamer;
	}
	public void setGamer(Gamer gamer) {
		this.gamer = gamer;
	}
	public Boolean getFull() {
		return full;
	}
	public void setFull(Boolean full) {
		this.full = full;
	}
}
