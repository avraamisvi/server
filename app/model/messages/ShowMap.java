package model.messages;

public class ShowMap implements Message {
	
	public String name;
	
	@Override
	public String getName() {
		return "ShowMap";
	}

	@Override
	public void setName(String name) {
		this.name = name;
	}
}
