package model.messages;

import model.City;
import model.Gamer;

public class GamerCityDataMessage implements Message {
	
	public String name = "GameCityData";
	public City city;
	public Gamer gamer;
	
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
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
}
