import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Calendar;


class myThread implements Runnable {
	
	private Socket connectionSocket;
	private static DataOutputStream outToClient;
	private static BufferedReader inFromClient;
	private static Connection connection;
	private static ObjectInputStream inStream;


	public myThread(Socket connectionSocket2) {
		this.connectionSocket=connectionSocket2;
	}

	public void run(){
		try {
			try {
				connectToDatabase();	//this method connects to the database
			} catch (SQLException e) {
				e.printStackTrace();
			}
			inFromClient = new BufferedReader(new InputStreamReader(
					connectionSocket.getInputStream()));
			// Allocate an output stream to send data back to the client //
			outToClient = new DataOutputStream(
					connectionSocket.getOutputStream());
			
			inStream = new ObjectInputStream(
					connectionSocket.getInputStream());

			String response = inFromClient.readLine();
			
			//we are repeating the same user in order to test rapidly the thread
			//in the coming version it will be selected from the gui 
			//this is to simplify the work with the console and scanners
			User addUser = new User("Joojo", "joe", "Haddad",
					"eiad@aaaa.com", "asdasd");
			User jad = new User("qew", "qwe", "wahqwasdeqab",
					"jasdasdd@asd.com", "asdqasdwdqwd");
			
			if (response.equals("1")) {
				login();
			} else if (response.equals("3")) {
				outToClient.writeBytes("" + getUserID(addUser)+"\n");
			} else if (response.equals("4")) {
				Calendar cal = Calendar.getInstance();
				cal.set(2015, 3, 12, 8, 0, 0);
				Event event = new Event("Party", " Beirut", cal, "WEL3ane");
				outToClient.writeBytes(addEventToDatabase(event));
			} else if (response.equals("5")) {

				try {
					outToClient
					.writeBytes(addUserToDatabase((User) inStream
							.readObject()));
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				}
			} else if (response.equals("6")) {

				outToClient.writeBytes(addUserToDatabase(addUser));
				outToClient.writeBytes(addUserToDatabase(jad));

				outToClient.writeBytes(addFriendsToDatabase(addUser, jad));
				outToClient.writeBytes(addFriendsToDatabase(jad, addUser));
			} else if (response.equals("7")) {
				outToClient.writeBytes("" + areFriends(addUser, jad)+"\n");
			} else if (response.equals("8")) {
				int id=Integer.parseInt(inFromClient.readLine());
				System.out.println(id);
				if(getUser(id).getUsername()!=null){
					outToClient.writeBytes("" + getUser(id).toString()+"\n");
				}
				else{
					outToClient.writeBytes("User not existing");
				}

			} else if (response.equals("9")) {
				outToClient.writeBytes("" + getFriendsOf(addUser));
			}
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	} 

	public static void login() {  //this method performs the necessary action for a login
		User trying = new User();
		String selectQuery = "";
		System.out.println("eneter");
		try {
			trying = (User) inStream.readObject();
			selectQuery = "SELECT * FROM user WHERE username='"	//this is the query for the mysql database
					+ trying.getUsername() + "';";

		} catch (IOException e1) {

			e1.printStackTrace();
		} catch (ClassNotFoundException e1) {

			e1.printStackTrace();
		}

		try {
			Statement statement = connection.createStatement();
			ResultSet rs = statement.executeQuery(selectQuery);
			rs.next();

			if (rs.getString(3).equals(trying.getPassword())) {
				try {

					outToClient.writeBytes("You are logged in\n");
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			rs.close();

		} catch (SQLException e) {
			try {
				outToClient.writeBytes("Wrong username or password\n");
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		}
	}

	public static void connectToDatabase() throws SQLException {
		System.out
		.println("-------- MySQL JDBC Connection Testing ------------");

		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			System.out.println("Where is your MySQL JDBC Driver?");
			e.printStackTrace();
			return;
		}

		System.out.println("MySQL JDBC Driver Registered!");

		connection = null;
		try {
			//load the driver from the localhost
			connection = DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/mycaldata", "root",
					"joehaddad");

		} catch (SQLException e) {
			System.out.println("Connection Failed! Check output console");
			e.printStackTrace();
			return;
		}

		if (connection != null) {
			System.out.println("You made it, take control your database now!");

		} else {
			System.out.println("Failed to make connection!");
		}
		//
	}

	public static int getUserID(User user) {		//this method returns the ID of a given user
		String queryStr = "SELECT * FROM user ;";
		String result = "";
		int idDB = -1;
		Statement stmt;
		try {
			stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery(queryStr);
			rs.next();
			result = rs.getString(4);
			idDB = rs.getInt(1);
			if (result == null) {
				return -1;
			}
			while (!result.equals(user.getFirstName()) && result != null) {
				idDB = rs.getInt(1);
				result = rs.getString(4);

				rs.next();

			}
			rs.close();
		} catch (SQLException e) {

			e.printStackTrace();
		}System.out.println(idDB);
		return idDB;
	}

	public static String addUserToDatabase(User user) {		//this method adds a user to the database
		String InsertqueryStr = "SELECT email FROM user ;";
		String queryStr = "INSERT INTO user (username,password,name,lastname,email)"
				+ "VALUES ('"
				+ user.getUsername()
				+ "','"
				+ user.getPassword()
				+ "','"
				+ user.getFirstName()
				+ "','"
				+ user.getLastName()
				+ "','" + user.getEmail() + "');";

		try {
			Statement statement = connection.createStatement();
			ResultSet rs = statement.executeQuery(InsertqueryStr);
			while (rs.next()) {
				if (rs.getString(1).equals(user.getEmail())) {
					return "user already exist\n";
				}
			}
			rs.close();
		} catch (SQLException e) {

			e.printStackTrace();
		}
		try {
			Statement statement = connection.createStatement();
			statement.executeUpdate(queryStr);

		} catch (SQLException e) {

			e.printStackTrace();
		}
		return "User Added Successfully\n";
	}

	public static String addEventToDatabase(Event event) {	//this method adds an event to the database
		String InserqueryStr = "INSERT INTO events (Name,Location,DateTime,Description) "
				+ "VALUES ('"
				+ event.getEventName()
				+ "','"
				+ event.getLocation()
				+ "','"
				+ event.getCal()
				+ "','"
				+ event.getDescription() + "');";

		try {
			Statement statement = connection.createStatement();
			statement.executeUpdate(InserqueryStr);

		} catch (SQLException e) {

			e.printStackTrace();
		}
		return "Event added Successfully\n";
	}

	public static String addFriendsToDatabase(User one, User two) {	//this method adds the acknowledgement that two users are friends to the database 
		String queryStr = "INSERT INTO friends (id,friend_id) " + "VALUES ('"
				+ getUserID(one) + "','" + getUserID(two) + "');";

		String selectQuery = "SELECT id,friend_id FROM friends ;";

		try {
			Statement statement = connection.createStatement();
			ResultSet rs = statement.executeQuery(selectQuery);
			int id;
			int friend_id;
			while (rs.next()) {
				id = rs.getInt(1);
				friend_id = rs.getInt(2);
				if (id == getUserID(two) && friend_id == getUserID(one)) {
					return "Users already Friends\n";
				}
			}
			rs.close();
		} catch (SQLException e) {

			e.printStackTrace();
		}
		try {
			Statement statement = connection.createStatement();
			statement.executeUpdate(queryStr);

		} catch (SQLException e) {

			e.printStackTrace();
		}
		return "Friends added Successfully\n";
	}

	public static Boolean areFriends(User one, User two) {	//this methods checks whether two users are friends or not
		String selectQuery = "SELECT id,friend_id FROM friends ;";
		try {
			Statement statement = connection.createStatement();
			ResultSet rs = statement.executeQuery(selectQuery);
			int id;
			int friend_id;
			while (rs.next()) {
				id = rs.getInt(1);
				friend_id = rs.getInt(2);
				if ((id == two.getID() && friend_id == one.getID() || (id == one
						.getID() && friend_id == two.getID()))) {
					return true;
				}
			}
			rs.close();
		} catch (SQLException e) {

			e.printStackTrace();
		}
		return false;
	}

	public static String getFriendsOf(User user) {	//this method lists all the friends of a certain user
		String selectQuery = "SELECT id,friend_id FROM Friends ;";
		try {
			Statement statement = connection.createStatement();
			ResultSet rs = statement.executeQuery(selectQuery);
			int id;
			int friend_id;
			User friend = new User();

			while (rs.next()) {

				id = rs.getInt(1);
				friend_id = rs.getInt(2);

				if (getUserID(user) == id) {

					friend = new User(getUser(friend_id));
					user.getFriends().add(
							friend.getFirstName() + " " + friend.getLastName()
							+ "\n");
				} else if (getUserID(user) == friend_id) {

					friend = getUser(id);
					user.getFriends().add(
							friend.getFirstName() + " " + friend.getLastName()
							+ "\n");
				}
			}
			rs.close();

		} catch (SQLException e) {

			e.printStackTrace();
		}
		String result = "";
		for (String str : user.getFriends()) {
			result += str ;

		}
		return result;
	}

	public static User getUser(int ID) {	//this method returns the user class given its id in the database
		String selectQuery = "SELECT * FROM user WHERE id=" + ID + ";";
		User newUser = new User();
		try {
			Statement statement = connection.createStatement();
			ResultSet rs = statement.executeQuery(selectQuery);
			while (rs.next()) {
				newUser.setEmail(rs.getString(6));
				newUser.setUsername(rs.getString(2));
				newUser.setFirstName(rs.getString(4));
				newUser.setLastName(rs.getString(5));
				newUser.setPassword(rs.getString(3));
			}
			rs.close();

		} catch (SQLException e) {

			e.printStackTrace();
		}
		return newUser;
	}
}
