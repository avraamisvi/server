package model.city;

import java.util.ArrayList;
import java.util.List;

import org.mongodb.morphia.annotations.Embedded;

@Embedded
public class Constructions {
	List<Road> roads = new ArrayList<Road>();
	List<House> houses = new ArrayList<House>();
	List<Farm> farms = new ArrayList<Farm>();
	List<PolicePost> policePosts = new ArrayList<PolicePost>();
	List<FiremanPost> firemanPosts = new ArrayList<FiremanPost>();
	
	public List<House> getHouses() {
		return houses;
	}
	public void setHouses(List<House> houses) {
		this.houses = houses;
	}
	public List<Farm> getFarms() {
		return farms;
	}
	public void setFarms(List<Farm> farms) {
		this.farms = farms;
	}
	public List<PolicePost> getPolicePosts() {
		return policePosts;
	}
	public void setPolicePosts(List<PolicePost> policePosts) {
		this.policePosts = policePosts;
	}
	public List<FiremanPost> getFiremanPosts() {
		return firemanPosts;
	}
	public void setFiremanPosts(List<FiremanPost> firemanPosts) {
		this.firemanPosts = firemanPosts;
	}
	
	public List<Road> getRoads() {
		return roads;
	}
	public void setRoads(List<Road> roads) {
		this.roads = roads;
	}
	
	public void add(Construction construction) {
		switch (construction.type) {
		case House:
			houses.add((House) construction);
			break;
		case Farm:
			farms.add((Farm) construction);
			break;
		case FiremanPost:
			firemanPosts.add((FiremanPost) construction);
			break;
		case PolicePost:
			policePosts.add((PolicePost) construction);
			break;
		case Road:
			roads.add((Road) construction);
			break;			
		default:
			break;
		}
	}
	
	public List<Construction> toList() {
		List<Construction> ret = new ArrayList<Construction>();
		
		ret.addAll(roads);
		ret.addAll(policePosts);
		ret.addAll(firemanPosts);
		ret.addAll(farms);
		ret.addAll(houses);
		
		return ret;
	}
}
