package model.city;

/**
 * Represents a row and a col in the map. 
 * @author abraao
 *
 */
public class MapPositionPoint {
	
	public int x;//COL
	public int y;//ROW
	
	public MapPositionPoint() {		
	}
	
	/**
	 * 
	 * @param x Row
	 * @param y Col
	 */
	public MapPositionPoint(int row, int col) {
		super();
		this.x = col;
		this.y = row;
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
