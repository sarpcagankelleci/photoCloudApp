package users;

import java.util.ArrayList;
import java.util.List;
import javax.swing.ImageIcon;

import java.awt.Image;
import java.awt.Toolkit;

public class Photo {
    protected String photoPath;
    protected User owner;
    protected boolean isShared;
    protected String description;
    protected int likes;
    protected int dislikes;
    protected List<String> comments;
    
    // Constructor for Photo Class
    public Photo(String photoPath, User owner) {
        this.photoPath = photoPath;
        this.owner = owner;
        this.description = ""; // Default empty
        this.likes = 0; // Default zero
        this.dislikes = 0; // Default zero
        this.comments = new ArrayList<>(); // Empty arraylist to store comments
    }
    
    public String getPhotoPath() { // Getter method for photoPath
        return photoPath;
    }
    
    public User getOwner() { // Getter method for owner
        return owner;
    }
    
    public String getDescription() { // Getter method for description of the photo
        return description;
    }
    
    public int getLikes() { // Getter method for the number of likes
        return likes;
    }
    
    public int getDislikes() { // Getter method for the number of dislikes
        return dislikes;
    }
    
    public List<String> getComments() { // Getter method for comments
        return comments;
    }
    
    public void setDescription(String description) { // Setter method for description of the photo
        this.description = description;
    }
    
    public void like() { // Method to increment like
        likes++;
    }
    
    public void dislike() { // Method to increment dislike
        dislikes++;
    }
    
    public void addComment(String comment) { // Method to add comments
        comments.add(comment);
    }
    
    public static ImageIcon imageScale(String photoPath) { // Method to scale an image to a proper size (267 x 188)
		Image image1 = Toolkit.getDefaultToolkit().getImage(photoPath);
		Image image2 = image1.getScaledInstance(267, 188, java.awt.Image.SCALE_SMOOTH);
		ImageIcon scaledImageIcon = new ImageIcon(image2);
		return scaledImageIcon;
	}
    
    public static ImageIcon imageScaleWithInput(String photoPath, int x, int y) { // Method to scale an image to the given size
		Image image1 = Toolkit.getDefaultToolkit().getImage(photoPath);
		Image image2 = image1.getScaledInstance(x, y, java.awt.Image.SCALE_SMOOTH);
		ImageIcon scaledImageIcon = new ImageIcon(image2);
		return scaledImageIcon;
	}

    public static Photo getPhoto(String photoPath, List<Photo> photos) { // Method to get the photo object with parameters of the photo path and the photo list
        for (Photo photo : photos) {
            if (photo.getPhotoPath().equals(photoPath)) {
                return photo; // Returns the photo object related with specified photoPath
            }
        }
        return null; // Returns null if photo is not found
    }

	public ImageIcon getThumbnail(Photo photo) { // Method to get thumbnail of a photo
		return imageScale(photo.getPhotoPath());
	}
   
}