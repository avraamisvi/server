package model.city;

import java.util.ArrayList;
import java.util.List;

import org.mongodb.morphia.annotations.Embedded;

@Embedded
public class Constructions {
	List<Road> roads = new ArrayList<Road>();
	List<House> houses = new ArrayList<House>();
	List<ClothsFabric> cloths = new ArrayList<ClothsFabric>();
	List<Farm> farms = new ArrayList<Farm>();
	List<PolicePost> policePosts = new ArrayList<PolicePost>();
	List<FiremanPost> firemanPosts = new ArrayList<FiremanPost>();
	List<MinePool> mines = new ArrayList<MinePool>();
	List<LumberJack> lumbers = new ArrayList<LumberJack>();
	List<WineCellar> wineCellars = new ArrayList<WineCellar>(); 
	List<FurnitureFabric> furnitures = new ArrayList<FurnitureFabric>(); 
	List<Market> markets = new ArrayList<Market>();	
	List<Bank> banks = new ArrayList<Bank>(); 
	List<University> universities = new ArrayList<University>();
	List<School> schools = new ArrayList<School>();
	List<Mansion> mansions = new ArrayList<Mansion>();
		
	Barracks barracks;
	Shipyard shipyard;
	Infirmary infirmary;
	MachineryFactory machineryFactory;
	ArcherCamp archerCamp;
	KnightsCamp knightsCamp;
	Airport airport;
	
	Palace palace;
	
	public List<ClothsFabric> getCloths() {
		return cloths;
	}
	public void setCloths(List<ClothsFabric> cloths) {
		this.cloths = cloths;
	}
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
	public List<MinePool> getMines() {
		return mines;
	}
	public void setMines(List<MinePool> mines) {
		this.mines = mines;
	}
	public List<LumberJack> getLumbers() {
		return lumbers;
	}
	public void setLumbers(List<LumberJack> lumbers) {
		this.lumbers = lumbers;
	}
	public List<WineCellar> getWineCellars() {
		return wineCellars;
	}
	public void setWineCellars(List<WineCellar> wineCellars) {
		this.wineCellars = wineCellars;
	}
	public List<FurnitureFabric> getFurnitures() {
		return furnitures;
	}
	public void setFurnitures(List<FurnitureFabric> furnitures) {
		this.furnitures = furnitures;
	}
	public List<Bank> getBanks() {
		return banks;
	}
	public void setBanks(List<Bank> banks) {
		this.banks = banks;
	}
	public List<Market> getMarkets() {
		return markets;
	}
	public void setMarkets(List<Market> markets) {
		this.markets = markets;
	}
	public List<University> getUniversities() {
		return universities;
	}
	public void setUniversities(List<University> universities) {
		this.universities = universities;
	}
	public List<Mansion> getMansions() {
		return mansions;
	}
	public void setMansions(List<Mansion> mansions) {
		this.mansions = mansions;
	}
	public Barracks getBarracks() {
		return barracks;
	}
	public void setBarracks(Barracks barracks) {
		this.barracks = barracks;
	}
	public Palace getPalace() {
		return palace;
	}
	public void setPalace(Palace palace) {
		this.palace = palace;
	}
	public Shipyard getShipyard() {
		return shipyard;
	}
	public void setShipyard(Shipyard shipyard) {
		this.shipyard = shipyard;
	}
	public Infirmary getInfirmary() {
		return infirmary;
	}
	public void setInfirmary(Infirmary infirmary) {
		this.infirmary = infirmary;
	}
	public MachineryFactory getMachineryFactory() {
		return machineryFactory;
	}
	public void setMachineryFactory(MachineryFactory machineryFactory) {
		this.machineryFactory = machineryFactory;
	}
	public ArcherCamp getArcherCamp() {
		return archerCamp;
	}
	public void setArcherCamp(ArcherCamp archerCamp) {
		this.archerCamp = archerCamp;
	}
	public KnightsCamp getKnightsCamp() {
		return knightsCamp;
	}
	public void setKnightsCamp(KnightsCamp knightsCamp) {
		this.knightsCamp = knightsCamp;
	}
	public List<School> getSchools() {
		return schools;
	}
	public void setSchools(List<School> schools) {
		this.schools = schools;
	}
	public Airport getAirport() {
		return airport;
	}
	public void setAirport(Airport airport) {
		this.airport = airport;
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
		case Cloths:
			cloths.add((ClothsFabric) construction);
			break;			
		case WineCellar:
			wineCellars.add((WineCellar) construction);
			break;			
		case LumberJack:
			lumbers.add((LumberJack) construction);
			break;
		case Furniture:
			furnitures.add((FurnitureFabric) construction);
			break;
		case Mine:
			mines.add((MinePool) construction);
			break;
		case Mansion:
			mansions.add((Mansion) construction);
			break;
		case Bank:
			banks.add((Bank) construction);
			break;	
		case Market:
			markets.add((Market) construction);
			break;
		case Barracks:
			barracks = (Barracks) construction;
			break;
		case Palace:
			palace = (Palace) construction;
			break;			
		case University:
			universities.add((University) construction);
			break;
		case Shipyard:
			shipyard = (Shipyard) construction;
			break;
		case Infirmary:
			infirmary = (Infirmary) construction;
			break;
		case ArcherCamp:
			archerCamp = (ArcherCamp) construction;
			break;
		case Knights:
			knightsCamp = (KnightsCamp) construction;
			break;			
		case Machinery:
			machineryFactory = (MachineryFactory) construction;
			break;			
		case School:
			schools.add((School) construction);
			break;			
		case Airport:
			airport = (Airport) construction;
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
		ret.addAll(cloths);
		ret.addAll(furnitures);
		ret.addAll(mines);
		ret.addAll(lumbers);
		ret.addAll(wineCellars);
		ret.addAll(markets);
		ret.addAll(mansions);
		ret.addAll(universities);
		ret.addAll(schools);
		
		if(barracks != null)
			ret.add(barracks);
		
		if(shipyard != null)
			ret.add(shipyard);
		
		if(infirmary!= null)
			ret.add(infirmary);
		
		if(archerCamp!= null)
			ret.add(archerCamp);
		
		if(machineryFactory!= null)
			ret.add(machineryFactory);
		
		if(knightsCamp!= null)
			ret.add(knightsCamp);
		
		if(airport!= null)
			ret.add(airport);
		
		if(palace != null)
			ret.add(palace);
		
		ret.addAll(houses);//houses needs to be the last one, because of the jobs processing
		
		return ret;
	}
}
