import java.awt.EventQueue;
import java.awt.Image;

import javax.swing.JFrame;

import java.awt.CardLayout;

import javax.swing.JPanel;
import javax.swing.JLabel;

import java.awt.Font;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JButton;

import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.JCheckBox;
import javax.swing.JProgressBar;

import java.io.*;
import java.net.*;
import java.util.Scanner;




public class Menu {

	private JFrame frame;
	private JTextField usernameField_signin;
	private JPasswordField passwordField_signin;
	private JTextField fnameField_signup;
	private JTextField usernameField_signup;
	private JTextField emailField_signup;
	private JTextField lnameField_signup;
	private JPasswordField passwordField_signup;
	private JPasswordField confirmPasswordField_signup;
	private static DataOutputStream outToServer;
	private static  BufferedReader inFromServer;
	private static Socket clientSocket;
	private static ObjectOutputStream objectOutputToServer;

	
	 //Launch the application.
	 
	public static void main(String[] args) {

		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Menu window = new Menu();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		System.out.println("Client now running");

		// a client socket //
		clientSocket = null;
		try {
			
			clientSocket = new Socket("localhost", 10000);
			outToServer = new DataOutputStream(clientSocket
					.getOutputStream());
			objectOutputToServer=new ObjectOutputStream(clientSocket.getOutputStream());
			inFromServer = new BufferedReader(
					new InputStreamReader(clientSocket.getInputStream()));
			//when this function is commented the log in and signup work through the GUI directly
			//if uncommented the program will be stuck in the while loop of this function waiting for an input from the console
			//uncomment to check additional features we implemented after milestone1 
			
			//makeChoice();
			
			//these statements print into a pop up box all the content of the buffer
			String rs=inFromServer.readLine();
			while(rs!=null){
				JOptionPane.showMessageDialog(null, rs);
				System.out.println(rs);
				rs=inFromServer.readLine();
			}
		} catch (UnknownHostException ex) {// catch the exceptions //
			ex.printStackTrace();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	
	 //Create the application.
	 
	public static void makeChoice(){
		Scanner scanner=new Scanner(System.in);
		System.out.println("Choose one of the following:\n3 for getting user id\n4 for adding an event\n6 For adding two friends"
				+ "\n7 for are friends\n8for getting user\n9 for getting friends");
		int response=scanner.nextInt();//checks what the user entered and do the corresponding option
		
		
		if (response ==4){
			try {
				outToServer.writeBytes("4" + '\n');

			} catch (IOException e) {

				e.printStackTrace();
			}
		}
		else if (response ==3){
			try {
				outToServer.writeBytes("3" + '\n');

			} catch (IOException e) {

				e.printStackTrace();
			}
		}
		else if(response==6){
			try {
				outToServer.writeBytes("6" + '\n');

			} catch (IOException e) {

				e.printStackTrace();
			}
		}
		else if (response==7){
			try {
				outToServer.writeBytes("7" + '\n');

			} catch (IOException e) {

				e.printStackTrace();
			}
		}else if (response==8){
			try {
				outToServer.writeBytes("8\n");
				System.out.println("Enter the user ID");
				outToServer.writeBytes(""+scanner.nextInt()+"\n"); //asks the user for the id 
			} catch (IOException e) {

				e.printStackTrace();
			}
		}else if (response==9){
			try {
				outToServer.writeBytes("9" + '\n');

			} catch (IOException e) {

				e.printStackTrace();
			}
		}
		else{
			System.out.println("error");
			return;
		}
	}
	public Menu() {
		initialize();
	}

	
	 //Initialize the contents of the frame.
	
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 750, 400);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new CardLayout(0, 0));

		final JPanel panelMenu = new JPanel();
		frame.getContentPane().add(panelMenu, "name_3572292479197");
		panelMenu.setLayout(null);

		final JPanel panelSignUp = new JPanel();
		frame.getContentPane().add(panelSignUp, "name_3955567711503");
		panelSignUp.setLayout(null);

		fnameField_signup = new JTextField();
		fnameField_signup.setBounds(120, 94, 220, 28);
		panelSignUp.add(fnameField_signup);
		fnameField_signup.setColumns(10);

		JLabel lblNewLabel = new JLabel("Join myCal today.");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Kokonor", Font.BOLD, 30));
		lblNewLabel.setBounds(240, 10, 270, 36);
		panelSignUp.add(lblNewLabel);

		JLabel lblNewLabel_1 = new JLabel("First name:");
		lblNewLabel_1.setBounds(38, 100, 70, 16);
		panelSignUp.add(lblNewLabel_1);

		usernameField_signup = new JTextField();
		usernameField_signup.setBounds(120, 154, 220, 28);
		panelSignUp.add(usernameField_signup);
		usernameField_signup.setColumns(10);

		emailField_signup = new JTextField();
		emailField_signup.setBounds(490, 154, 220, 28);
		panelSignUp.add(emailField_signup);
		emailField_signup.setColumns(10);

		final JLabel lblEmail = new JLabel("E-mail:");
		lblEmail.setBounds(432, 160, 46, 16);
		panelSignUp.add(lblEmail);

		final JProgressBar strengthBar = new JProgressBar();
		strengthBar.setBounds(312, 285, 146, 20);
		panelSignUp.add(strengthBar);

		lnameField_signup = new JTextField();
		lnameField_signup.setColumns(10);
		lnameField_signup.setBounds(490, 94, 220, 28);
		panelSignUp.add(lnameField_signup);

		final JLabel errorLabel = new JLabel("");
		errorLabel.setBackground(Color.WHITE);
		errorLabel.setFont(new Font("Times New Roman", Font.BOLD | Font.ITALIC, 20));
		errorLabel.setForeground(Color.RED);
		errorLabel.setHorizontalAlignment(SwingConstants.CENTER);
		errorLabel.setBounds(0, 57, 750, 25);
		panelSignUp.add(errorLabel);

		JLabel lblUsername = new JLabel("Username:");
		lblUsername.setBounds(38, 160, 70, 16);
		panelSignUp.add(lblUsername);

		JLabel lblNewLabel_2 = new JLabel("Password strength");
		lblNewLabel_2.setFont(new Font("Lucida Grande", Font.ITALIC, 10));
		lblNewLabel_2.setBounds(340, 271, 90, 13);
		panelSignUp.add(lblNewLabel_2);

		JLabel lblConfirmPassword = new JLabel("Confirm\n");
		lblConfirmPassword.setBounds(421, 214, 55, 16);
		panelSignUp.add(lblConfirmPassword);

		JLabel lblPassword_1 = new JLabel("Password:");
		lblPassword_1.setBounds(415, 229, 63, 16);
		panelSignUp.add(lblPassword_1);

		passwordField_signup = new JPasswordField();
		passwordField_signup.setBounds(120, 226, 220, 22);
		panelSignUp.add(passwordField_signup);

		confirmPasswordField_signup = new JPasswordField();
		confirmPasswordField_signup.setBounds(490, 221, 220, 22);
		panelSignUp.add(confirmPasswordField_signup);

		JLabel label_6 = new JLabel("Last name:");
		label_6.setBounds(410, 100, 68, 16);
		panelSignUp.add(label_6);

		final JLabel label_7 = new JLabel("Password:");
		label_7.setBounds(45, 229, 63, 16);
		panelSignUp.add(label_7);

		JButton btnSubmit = new JButton("Submit");
		btnSubmit.setFont(new Font("Lucida Grande", Font.BOLD, 15));
		btnSubmit.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {		//SUBMIT BUTTON CLICKED


				String p4=new String(passwordField_signup.getPassword());
				String p5=new String(confirmPasswordField_signup.getPassword());
				if(!p4.equals(p5)){
					System.out.println("equal");
					errorLabel.setText("Passwords NOT matching");
					return;
				}
				if(!p4.equals("")){
					strengthBar.setValue(strengthBar.getValue()+25);
				}
				else{
					strengthBar.setValue(0);	
				}
				if(p4.matches(".*\\d+.*")){
					strengthBar.setValue(strengthBar.getValue()+25);
				}
				else{
					strengthBar.setValue(strengthBar.getValue()-25);
				}
				if(p4.length()>5){
					strengthBar.setValue(strengthBar.getValue()+25);
				}
				User user=new User();
				user.setUsername(usernameField_signup.getText());
				user.setPassword(p4);
				user.setFirstName(fnameField_signup.getText());
				user.setLastName(lnameField_signup.getText());
				user.setEmail(emailField_signup.getText());
				try {
					outToServer.writeBytes("5\n");
					objectOutputToServer.writeObject(user);
				} catch (IOException e1) {

					e1.printStackTrace();
				}
			}
		});
		btnSubmit.setBounds(311, 317, 150, 40);
		panelSignUp.add(btnSubmit);

		JButton btnBack = new JButton("< Back");
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				panelSignUp.setVisible(false);
				panelMenu.setVisible(true);
			}
		});
		btnBack.setFont(new Font("Lucida Grande", Font.PLAIN, 12));
		btnBack.setBounds(-1, -1, 75, 35);
		panelSignUp.add(btnBack);



		final JPanel panel_extra = new JPanel();	//extra panel in case needed
		frame.getContentPane().add(panel_extra, "name_3959565603530");	//NOT being used
		panel_extra.setLayout(null);		

		JLabel titleLabel = new JLabel("myCal");
		titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
		titleLabel.setFont(new Font("Kokonor", Font.BOLD, 40));
		titleLabel.setBounds(0, 10, 750, 65);
		panelMenu.add(titleLabel);

		usernameField_signin = new JTextField();
		usernameField_signin.setColumns(10);
		usernameField_signin.setBounds(503, 85, 200, 28);
		panelMenu.add(usernameField_signin);

		passwordField_signin = new JPasswordField();
		passwordField_signin.setBounds(503, 125, 200, 28);
		panelMenu.add(passwordField_signin);

		JLabel label_1 = new JLabel("Username:");
		label_1.setFont(new Font("Lucida Grande", Font.PLAIN, 15));
		label_1.setBounds(415, 89, 76, 19);
		panelMenu.add(label_1);

		JLabel label_2 = new JLabel("Password:");
		label_2.setFont(new Font("Lucida Grande", Font.PLAIN, 15));
		label_2.setBounds(418, 129, 73, 19);
		panelMenu.add(label_2);

		JButton btnSignIn = new JButton("Sign in");
		btnSignIn.setBackground(Color.RED);
		btnSignIn.setFont(new Font("Chalkboard SE", Font.BOLD, 18));
		btnSignIn.setBounds(503, 200, 200, 45);
		panelMenu.add(btnSignIn);
		btnSignIn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {	//SIGN IN BUTTON CLICKED

				User user=new User();
				String p4=new String(passwordField_signin.getPassword());

				user.setUsername(usernameField_signin.getText());
				user.setPassword(p4);

				try {
					outToServer.writeBytes("1\n");
					objectOutputToServer.writeObject(user);
				} catch (IOException e1) {

					e1.printStackTrace();
				}
			}
		});

		JLabel lblDontHaveAn = new JLabel("Don't have an account?");
		lblDontHaveAn.setFont(new Font("Lucida Grande", Font.ITALIC, 13));
		lblDontHaveAn.setBounds(415, 293, 160, 16);
		panelMenu.add(lblDontHaveAn);

		JButton btnSignUp = new JButton("Sign up!");
		btnSignUp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {	//SIGN UP BUTTON CLICKED
				panelSignUp.setVisible(true);
				panelMenu.setVisible(false);
			}
		});
		btnSignUp.setBounds(583, 285, 120, 35);
		panelMenu.add(btnSignUp);

		JLabel label_4 = new JLabel("Terms of service / Privacy Policy");
		label_4.setForeground(Color.BLUE);
		label_4.setFont(new Font("Lucida Grande", Font.PLAIN, 11));
		label_4.setBounds(530, 345, 173, 14);
		panelMenu.add(label_4);

		JLabel lblMycal = new JLabel("\u00A9 2015 myCal, Inc");
		lblMycal.setFont(new Font("Lucida Grande", Font.PLAIN, 9));
		lblMycal.setEnabled(false);
		lblMycal.setBounds(575, 361, 85, 11);
		panelMenu.add(lblMycal);

		JCheckBox chckbxNewCheckBox = new JCheckBox("Remember me");
		chckbxNewCheckBox.setFont(new Font("Lucida Grande", Font.PLAIN, 11));
		chckbxNewCheckBox.setBounds(552, 153, 108, 23);
		panelMenu.add(chckbxNewCheckBox);

		JLabel label = new JLabel("");
		label.setHorizontalAlignment(SwingConstants.CENTER);
		Image img = new ImageIcon(getClass().getResource("/rsz_11jad-logo-mycal.png")).getImage();
		label.setIcon(new ImageIcon(img));
		label.setBounds(60, 74, 246, 260);
		panelMenu.add(label);


	}
}
