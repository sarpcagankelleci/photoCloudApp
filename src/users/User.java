package users;

import java.util.ArrayList;
import java.util.List;

public class User {
	protected String nickname;
	protected String password;
	protected String realName;
	protected String realSurname;
	protected String emailAddress;
	protected int age;
	public UserTier userTier;
	protected String profilePhotoPath;
	public List<Photo> uploadedPhotos;
	public List<Photo> sharedPhotos;
	public static ArrayList<User> users = new ArrayList<>();
	
	// Constructor for User class
	public User(String nickname, String password, String realName, String realSurname, int age, String emailAddress, UserTier userTier) { 
		this.nickname = nickname;
		this.password = password;
		this.realName = realName;
		this.realSurname = realSurname;
		this.age = age;
		this.emailAddress = emailAddress;
		this.userTier = userTier;
		this.profilePhotoPath = "image/defaultprofilephoto.jpeg"; // Default profile photo path 
		this.uploadedPhotos = new ArrayList<>(); // Empty arraylist for uploadedPhotos 
		this.sharedPhotos = new ArrayList<>(); // Empty arraylist for sharedPhotos
	}
	
	public String getNickname() { // Getter method for nickname
		return nickname;
	}
	
	public String getPassword() { // Getter method for password
		return password;
	}
	
	public String getRealName() { // Getter method for real name
		return realName;
	}
	
	public String getRealSurname() { // Getter method for surname
		return realSurname;
	}
	
	public int getAge() { // Getter method for age
		return age;
	}
	
	public String getEmailAddress() { // Getter method for email address
		return emailAddress;
	}
	
	public UserTier getUserTier() { // Getter method for user tier
		return userTier;
	}
	
	public String getProfilePhotoPath() { // Getter method for profile photo path
		return profilePhotoPath;
	}
	
	public List<Photo> getUploadedPhoto() { // Getter method for uploaded photos list
		return uploadedPhotos;
	}
	
	public List<Photo> getSharedPhoto() { // Getter method for shared photos list
		return sharedPhotos;
	}
	
	public static ArrayList<User> getUserList() { // Getter method for user list
		return users;
	}
	
	public void setRealName(String realName) { // Setter method for the real name
		this.realName = realName;
	}

	public void setSurname(String realSurname) { // Setter method for the real surname
		this.realSurname = realSurname;
	}

	public void setAge(int age) { // Setter method for the age of the user
		this.age = age;
	}
	
	public void setProfilePhoto(String profilePhotoPath) { // Setter method for profile photo path
		this.profilePhotoPath = profilePhotoPath;
	}
	
	public void setEmailAddress(String emailAddress) { // Setter method for email address
		this.emailAddress = emailAddress;
	}
	
	 public static User getUserFromNickname(String nickname) { // Method to return user object from the nickname parameter
	        for (User user : getUserList()) {
	            if (user.getNickname().equals(nickname)) {
	                return user; // Returns specified user object with given nickname
	            }
	        }
	        return null; // Return null if there is no user with given nickname
	    }
	
	public static void addUserToList(User user) { // Method to add a user to user list
        users.add(user);
    }
	
	public void changePassword(String newPassword) { // Method to change password 
		this.password = newPassword;
	}
	
	public void changePersonalInfo(String realName, String realSurname, int age, String emailAdress, String profilePhotoPath) { // Method for changing Personal information
		this.realName = realName;
		this.realSurname = realSurname;
		this.age = age;
		this.emailAddress = emailAdress;
		this.profilePhotoPath = profilePhotoPath;
	}
	
	public Photo getPhoto(String photoPath) { // Method for getting a photo object from the photo path
		Photo photo = new Photo(photoPath, this);
		return photo;
	}
	
	public void uploadPhoto(String photoPath) { // Method for uploading photos to uploadedPhoto list
		 Photo photo = new Photo(photoPath, this);
	        uploadedPhotos.add(photo);
	}
	
	public void sharePhoto(String photoPath) { // Method for adding photos to sharedPhoto list
		Photo photo = new Photo(photoPath, this);
			sharedPhotos.add(photo);	
	}
	
	public void deletePhoto(Photo photo) { // Method for deleting photos from both uploadedPhotos and sharedPhotos lists
		uploadedPhotos.remove(photo);
		sharedPhotos.remove(photo);
	}
    
    public void likePhoto(Photo photo) { // Method to like a photo
        photo.like();
    }
    
    public void dislikePhoto(Photo photo) { // Method to dislike a photo
        photo.dislike();
    }
    
    public void commentOnPhoto(User user, Photo photo, String comment) { // Method to comment on a photo
        photo.addComment(user.getNickname()+ ": " + comment);
    }
    
    public List<Photo> removePhotoFromSharedPhoto(Photo photo, List<Photo> sharedPhotos) { // Method to remove selected photo from the shared photo list
	    sharedPhotos.remove(photo);
	    return sharedPhotos;
	}
    
    public Photo getLastUploadedPhoto() { // Method to return the last uploaded photo
	    if (uploadedPhotos.isEmpty()) {
	        return null; // Return null if there is no uploaded photo
	    }
	    
	    int last = uploadedPhotos.size() - 1;
	    return uploadedPhotos.get(last); // Return the last uploaded photo of a user
	}
	
}