package users;

public class Administrator extends User { // Inheritance in polymorphism

	// Constructor for administrator class
	public Administrator(String nickname, String password, String realName, String realSurname, int age, String emailAddress, UserTier userTier) { 
		
		super(nickname, password, realName, realSurname, age, emailAddress, UserTier.PROFESSIONAL); // default PROFESSIONAL user tier type
	}
	
	public void removePhotoAdmin(Photo photo) { // Method to remove a photo from discover page and profile page
		User ownerOfPhoto = photo.getOwner();
		ownerOfPhoto.deletePhoto(photo);
	}
}
