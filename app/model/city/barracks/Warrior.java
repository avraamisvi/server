package model.city.barracks;

public class Warrior {
	
	int level;//the experience level of this warrior
	int type;
	int power;
	int life;
	int velocity;
	int cost;
	int rage;//distancia para acertar o inimigo
	
	public int getLevel() {
		return level;
	}
	public void setLevel(int level) {
		this.level = level;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public int getPower() {
		return power;
	}
	public void setPower(int power) {
		this.power = power;
	}
	public int getLife() {
		return life;
	}
	public void setLife(int life) {
		this.life = life;
	}
	public int getVelocity() {
		return velocity;
	}
	public void setVelocity(int velocity) {
		this.velocity = velocity;
	}
	public int getCost() {
		return cost;
	}
	public void setCost(int cost) {
		this.cost = cost;
	}
	public int getRage() {
		return rage;
	}
	public void setRage(int rage) {
		this.rage = rage;
	}
}
