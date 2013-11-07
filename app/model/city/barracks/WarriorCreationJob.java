package model.city.barracks;

import java.util.Date;

import org.joda.time.DateTime;

/**
 * Job for create a new warrior
 * @author abraao
 *
 */
public class WarriorCreationJob {
	
	Date start;
	int type;
	int timeToBuild;
	
	public WarriorCreationJob(){
	}
	
	public WarriorCreationJob(int type, int timeToBuild) {
		this.type = type;
		this.start = DateTime.now().toDate();
		this.timeToBuild = timeToBuild;
	}
	
	public Date getStart() {
		return start;
	}
	public void setStart(Date start) {
		this.start = start;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	
	public boolean elapsed() {
		
		DateTime now = DateTime.now();
		DateTime lstart = new DateTime(start);
		
		if(now.isAfter(lstart.plusSeconds(timeToBuild))) {
			return true;
		}
		
		return false;
	}
}
