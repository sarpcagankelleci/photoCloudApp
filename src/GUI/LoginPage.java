package GUI;

import javax.swing.*;

import users.LoggedInUser;
import users.User;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class LoginPage extends JFrame {
	private static final long serialVersionUID = 1L;
	private JTextField nicknameField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JButton signupButton;
    private JLabel lblNewLabel;
   

    public LoginPage() {
    	
	//GUI implementation
	
	    setTitle("Login Page");
	    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    setSize(600, 400);
	    getContentPane().setLayout(new GridLayout(4, 2));
	
	    JLabel titleLabel = new JLabel("Welcome To PhotoCloud!", SwingConstants.CENTER);
	    titleLabel.setFont(new java.awt.Font(".AppleSystemUIFont", java.awt.Font.PLAIN, 13));
	    
	    titleLabel.setBackground(Color.WHITE);
	    getContentPane().add(titleLabel);
	                    
	    lblNewLabel = new JLabel("author@sarpcagankelleci");
	    lblNewLabel.setFont(new java.awt.Font(".AppleSystemUIFont", java.awt.Font.PLAIN, 13));
	    lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
	    getContentPane().add(lblNewLabel);
	    JLabel label = new JLabel("Nickname:");
	    
	    getContentPane().add(label);
	    label.setBackground(Color.WHITE); 
	    label.setFont(new java.awt.Font(".AppleSystemUIFont", java.awt.Font.PLAIN, 13));
	
	    nicknameField = new JTextField();
	    getContentPane().add(nicknameField);
	    JLabel label_1 = new JLabel("Password:");
	    label_1.setBackground(Color.WHITE); 
	    label_1.setFont(new java.awt.Font(".AppleSystemUIFont", java.awt.Font.PLAIN, 13));
	    getContentPane().add(label_1);
	            
	    passwordField = new JPasswordField();
	    getContentPane().add(passwordField);
	    
	    signupButton = new JButton("SignUp");
	    signupButton.setFont(new java.awt.Font(".AppleSystemUIFont", java.awt.Font.PLAIN, 13));
	  
	        getContentPane().add(signupButton);
	        
	        signupButton.addActionListener(new ActionListener() {
	        @Override
	        public void actionPerformed(ActionEvent e) {
	            // Navigate the user to the Signup page
	        navigateToSignupPage();
	    }
	    });
	
	    loginButton = new JButton("Login");
	    loginButton.setFont(new java.awt.Font(".AppleSystemUIFont", java.awt.Font.PLAIN, 13));
	    getContentPane().add(loginButton);
	    
	    loginButton.addActionListener(new ActionListener() {
	    @Override
	    public void actionPerformed(ActionEvent e) {
	        String nickname = nicknameField.getText();
	        String password = new String(passwordField.getPassword());
	

	        if (validateCredentials(nickname, password)) {
	       	
	            navigateToDiscoverPage(); // Navigate the user to the profile page or Discover page
	            
	        } else {

	            JOptionPane.showMessageDialog(LoginPage.this, "Invalid nickname or password!", "Error", JOptionPane.ERROR_MESSAGE);
	        }
	        }
    });
    }

    public boolean validateCredentials(String nickname, String password) {
    	for (User user : User.getUserList()) {
            if (user.getNickname().equals(nickname) && user.getPassword().equals(password)) {
            	LoggedInUser.setLoggedInUser(user);
                return true; // Valid credentials
            }
        }
        return false; // Invalid credentials
    }

    public void navigateToDiscoverPage() {
    	 JOptionPane.showMessageDialog(LoginPage.this, "Login successful! Navigating to the discover page.");
    	 
    	 ArrayList<User> users = DiscoverPage.getUserData();
    	 User user = LoggedInUser.getLoggedInUser();
         DiscoverPage discoverPage = new DiscoverPage(users, user); 
         setVisible(false);
         discoverPage.showDiscoverPage();
        } 

    public void navigateToSignupPage() {
        JOptionPane.showMessageDialog(LoginPage.this, "Navigating to the SignUp page.");
        SignupPage signUpPage = new SignupPage();
        setVisible(false);
        signUpPage.setVisible(true);
    }
    
    // Main method to invoke
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                LoginPage loginPage = new LoginPage();
                loginPage.setVisible(true);
            }
        });
    }
}

