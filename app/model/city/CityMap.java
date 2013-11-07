package model.city;

import org.mongodb.morphia.annotations.Embedded;

@Embedded
public class CityMap {
	
	long constructions[][];
	char soil [][];// = new SoilType[0][0] 
	
	public CityMap() {
	}
	
	//GETS AND SETS
	
	public long[][] getConstructions() {
		return constructions;
	}

	public void setConstructions(long[][] constructions) {
		this.constructions = constructions;
	}

	public char[][] getSoil() {
		return soil;
	}

	public void setSoil(char[][] soil) {
		this.soil = soil;
	}	
}
