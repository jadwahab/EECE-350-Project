
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;


public class Event {
	private String eventName;
	private ArrayList <Integer> usersAttending;
	private String Description;
	private String Location;
	private Calendar cal;

	public Event(String name,String location,  Calendar cal, String descript){
		this.eventName=name;
		this.Description=descript;
		this.Location=location;
		this.cal=cal;
		
	}
	//usersAttending (getter)
	public ArrayList <Integer> getUsersAttending() {
		return usersAttending;

	}
	public String getDescription() {
		return Description;
	}
	public void setDescription(String description) {
		Description = description;
	}
	public String getLocation() {
		return Location;
	}
	public void setLocation(String location) {
		Location = location;
	}
	public String getEventName() {
		return eventName;
	}
	public void setEventName(String eventName) {
		this.eventName = eventName;
	}
	
	public String getCal() {
		SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return sdf.format(this.cal.getTime());
	}
	public void setCal(int year,int month,int day,int hour,int minute,int second) {
		this.cal.set(year, month, day, hour, minute, second);

	}
	


}
