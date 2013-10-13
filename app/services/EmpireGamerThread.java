package services;

import model.Gamer;
import play.mvc.*;

public class EmpireGamerThread {

	private Gamer gamer; 
	private boolean exit;
	
	public void execute() {
		gamer = new Gamer();
	}
	
	public void processMessage(WebSocket.Out<String> out) {
		while(!exit) {
			try {
				Thread.sleep(500);
				
				
				
				
				
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
