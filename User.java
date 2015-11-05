import java.awt.Image;
import java.io.Serializable;
import java.util.ArrayList;



public class User implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 674177071109141474L;
	private String firstName;
	private String lastName;
	private int ID;
	private String username;
	private String password;
	private String email;
	private ArrayList <String> friends;
	private Image profilePic;
	private ArrayList <Event> events;
	

	//Constructors	
	public User(){ 
		this.firstName = "";
		this.lastName = "";
	}
	
	public User(User user){
		this.username=user.getUsername();
		this.firstName = user.getFirstName();
		this.lastName = user.getLastName();
		this.email=user.getEmail();
		this.password=user.getPassword();
		this.events=new ArrayList<Event>();
		this.friends=new ArrayList<String>();
	}
	
	public User (String userName,String fname, String lname, String email,String password) {
		this.username=userName;
		this.firstName = fname;
		this.lastName = lname;
		this.events=new ArrayList<Event>();
		this.friends=new ArrayList<String>();
		this.email=email;
		this.password=password;
	
	
	}

	//First Name (getter/setter)
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	//Last Name (getter/setter)	
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	//ID (getter/setter)	
	public int getID() {
		return ID;
	}
	public void setID(int iD) {
		ID = iD;
	}

	//username (getter/setter)
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}

	//password (getter/setter)	
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}

	//friends (getter)
	public ArrayList <String> getFriends() {
		return friends;
	}
	
	//profile pic (getter/setter)
	public Image getProfilePic() {
		return profilePic;
	}
	public void setProfilePic(Image profilePic) {
		this.profilePic = profilePic;
	}
	
	//events (getter)
	public ArrayList <Event> getEvents() {
		return events;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}

	public String toString(){
		return "Name :"+getFirstName()+"\nLastName :"+getLastName()+"\nUserName :"+
				getUsername()+"\nEmail :"+getEmail()+"Friends";
	}

}
