package users;

public class LoggedInUser {
	// Public class to retrieve the user object that logged in to the application
    public static User loggedInUser;
    
    public static User getLoggedInUser() { // Getter method for logged in user
        return loggedInUser;
    }

    public static void setLoggedInUser(User user) { // Setter method for logged in user
        loggedInUser = user;
    }
}