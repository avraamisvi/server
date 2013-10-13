package model.city;

import org.mongodb.morphia.annotations.Embedded;

import enums.SoilType;

@Embedded
public class CityMap {
	
	long constructions[][];
	SoilType soil [][];// = new SoilType[0][0] 
	
	public CityMap() {
	}
	
	//GETS AND SETS
	
	public long[][] getConstructions() {
		return constructions;
	}

	public void setConstructions(long[][] constructions) {
		this.constructions = constructions;
	}

	public SoilType[][] getSoil() {
		return soil;
	}

	public void setSoil(SoilType[][] soil) {
		this.soil = soil;
	}	
}
