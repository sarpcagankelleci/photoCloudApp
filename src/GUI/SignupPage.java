package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.regex.Pattern;

import users.LoggedInUser;
import users.User;
import users.UserTier;

public class SignupPage extends JFrame {
	private static final long serialVersionUID = 1L; 
	private JTextField nicknameField;
	private JPasswordField passwordField;
	private JTextField realNameField;	    
	private JTextField surnameField;    
	private JSpinner ageSpinner; 
	private JTextField emailField;
	private JPanel mainPanel;
	
	public SignupPage() {
		
		// GUI implementation of the SignUp Page
		
		// Adding a main panel 
		mainPanel = new JPanel(new GridLayout(7, 2, 10, 10));  
		mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		
		// Adding JLabel for parameters
		JLabel nicknameLabel = new JLabel("Nickname:");       
		JLabel passwordLabel = new JLabel("Password:");      
		JLabel realNameLabel = new JLabel("Real Name:");      
		JLabel surnameLabel = new JLabel("Surname:");	        
		JLabel ageLabel = new JLabel("Age:");	       
		JLabel emailLabel = new JLabel("Email:");
		
		// Adding fields for parameters
		nicknameField = new JTextField();
		passwordField = new JPasswordField();
		realNameField = new JTextField();
		surnameField = new JTextField();
		ageSpinner = new JSpinner();
		emailField = new JTextField();

		// Adding JLabels and fields to the main panel
		mainPanel.add(nicknameLabel);
		mainPanel.add(nicknameField);
		mainPanel.add(passwordLabel);
		mainPanel.add(passwordField);
		mainPanel.add(realNameLabel);
		mainPanel.add(realNameField);
		mainPanel.add(surnameLabel);      
		mainPanel.add(surnameField);       
		mainPanel.add(ageLabel);      
		mainPanel.add(ageSpinner);      
		mainPanel.add(emailLabel);
		mainPanel.add(emailField);

		// Adding a button to activate the sign up process and assigning an action listener
		JButton signupButton = new JButton("Sign Up");
		signupButton.addActionListener(new ActionListener() {
		@Override 
		public void actionPerformed(ActionEvent e) {
			if (validateForm()) { // Validation of parameters
				createAccount(); // Creation of a new account 
				navigateToDiscoverPage(); // Navigation to the discover page
		    }
		}
		});
		
		// Adding a button to go back to the login page
		JButton loginPageButton = new JButton("Go Back to Login Page");
		loginPageButton.addActionListener(new ActionListener() {
		@Override 
		public void actionPerformed(ActionEvent e) {
			navigateToLoginPage();
			
		}
		});
		
		// Adding buttons to the main panel
		mainPanel.add(signupButton); 
		mainPanel.add(loginPageButton); 
		
		getContentPane().add(mainPanel); 
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("SignUp Page");
        setSize(500, 300);
        setLocationRelativeTo(null);
        setVisible(true);
		}
	
	// Main method to invoke the Sign Up Page
	public static void main(String[] args) { 
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new SignupPage();
            }
        });
    }
	
	// Method to check the validation of parameters
	public boolean validateForm() { 

        String nickname = nicknameField.getText();
        String password = new String(passwordField.getPassword());
        String realName = realNameField.getText();
        String surname = surnameField.getText();
        int age = (int) ageSpinner.getValue();
        String email = emailField.getText();

        // Validation processes of parameters
        if (nickname.isEmpty() || surname.isEmpty() ||  password.isEmpty() || realName.isEmpty() || email.isEmpty()) {
            JOptionPane.showMessageDialog(this, "You should fill all required fields.");
            return false;
        }
        
        // Validation process of email
        String emailValidate = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";  // Email validation special string
        if (!Pattern.matches(emailValidate, email)) {
            JOptionPane.showMessageDialog(this, "The given email address is invalid.");
            return false;
        }
        
        // Validation process of password
        if (password.length() < 4) { 
            JOptionPane.showMessageDialog(this, "Password should be at least 4 characters long.");
            return false;
        }
        return true;
    }

    public void createAccount() {
    	// Getting parameters from fields
        String nickname = nicknameField.getText();
        String password = new String(passwordField.getPassword());
        String realName = realNameField.getText();
        String surname = surnameField.getText();
        int age = (int) ageSpinner.getValue();
        String email = emailField.getText();
        
        // Creating a new user object with given parameters
        User newUser = new User(nickname, password, realName, surname, age, email, UserTier.FREE);
		User.addUserToList(newUser); // Adding the new user to the user list
        JOptionPane.showMessageDialog(this, "Your account has been created successfully!");
        LoggedInUser.setLoggedInUser(newUser);
    }

    public void navigateToDiscoverPage() { // Method to navigate to the discover page
    	JOptionPane.showMessageDialog(this, "Successfully signed up! We are navigating you to Discover page.");
    	ArrayList<User> users = DiscoverPage.getUserData();  // Retrieving data of user list
    	User user = LoggedInUser.getLoggedInUser(); // Retriving data of logged in user
        DiscoverPage discoverPage = new DiscoverPage(users, user); 
        setVisible(false); 
        discoverPage.showDiscoverPage();
    }

    public void setVisible(boolean b) { // Method to set the sign up page visible
        super.setVisible(b);
    }
    
    public void navigateToLoginPage() { // Method to navigate to the Login Page
        JOptionPane.showMessageDialog(SignupPage.this, "Navigating to the Login page.");
        LoginPage loginPage = new LoginPage();
        setVisible(false);
        loginPage.setVisible(true);
    }
	
}


