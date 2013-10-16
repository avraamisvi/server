package model.city;

/**
 * Represents a row and a coll in the map. 
 * @author abraao
 *
 */
public class MapPositionPoint {
	
	public int x;
	public int y;
	
	public MapPositionPoint() {		
	}
	
	public MapPositionPoint(int x, int y) {
		super();
		this.x = x;
		this.y = y;
	}
	
	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}
}
