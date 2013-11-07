package controllers.messages;

import enums.ConstructionType;
import enums.GoodsTypeProduction;
import enums.MineType;

/**
 * A message from the client.
 * 
 * @author abraao
 *
 */
public class CreateMessage {
	String name;
	String id;
	ConstructionType type;
	GoodsTypeProduction prodType;
	MineType mineType;
	int[] position;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public ConstructionType getType() {
		return type;
	}
	public void setType(ConstructionType type) {
		this.type = type;
	}
	public int[] getPosition() {
		return position;
	}
	public void setPosition(int[] position) {
		this.position = position;
	}
	public GoodsTypeProduction getProdType() {
		return prodType;
	}
	public void setProdType(GoodsTypeProduction prodType) {
		this.prodType = prodType;
	}
	public MineType getMineType() {
		return mineType;
	}
	public void setMineType(MineType mineType) {
		this.mineType = mineType;
	}
}
