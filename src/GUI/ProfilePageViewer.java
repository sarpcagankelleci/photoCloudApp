package GUI;

import users.LoggedInUser;
import users.Photo;
import users.User;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class ProfilePageViewer extends JFrame { // Class for viewing a profile page that does not belong to logged in user
    private static final long serialVersionUID = 1L;
	public static User user;
    public static List<Photo> photos;

    private JLabel nameLabel;
    private JLabel profilePhotoLabel;
    private JPanel photosPanel;
    private JLabel realNameLabel;
    public ProfilePageViewer(User user, List<Photo> photos) {
        ProfilePageViewer.setUser(user); 
        ProfilePageViewer.setPhotos(photos);

        showGUI(); // GUI implementation
        profileData(); // Profile Information
        photosPanel(); // Photos
    }
    
	// Main method to invoke the profile page
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                User user = getUser(); 
                List<Photo> photos = getPhotos(); 
                ProfilePage profilePage = new ProfilePage(user, photos);
                profilePage.setVisible(true);
            }
        });
    }

    public void profileData() {
    	// Showing user's profile information
    	nameLabel.setText(getUser().getNickname());
 
    	ImageIcon profilePhotoIcon = Photo.imageScaleWithInput(getUser().getProfilePhotoPath(), 100, 100);
    	profilePhotoLabel.setIcon(profilePhotoIcon);
        
    	String age = Integer.toString(getUser().getAge());
     
    	realNameLabel.setText("Name: " + getUser().getRealName() + " " +getUser().getRealSurname()+ " " + "Age: " + age );

    }

    public void photosPanel() {
        photosPanel.removeAll();

        for (Photo photo : user.getUploadedPhoto()) {
        	
        	// Adding photos to the photoPanel
            JPanel photoPanel = new JPanel();
            photoPanel.setLayout(new BorderLayout());
            photoPanel.setPreferredSize(new Dimension(150, 150));
            photoPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
            
            ImageIcon icon = Photo.imageScale(photo.getPhotoPath());
            JLabel iconLabel = new JLabel(icon);
            photoPanel.add(iconLabel, BorderLayout.CENTER);
            photoPanel.setToolTipText(photo.getDescription());
            
            // Adding a mouse listener for clicking on the photo
            photoPanel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
            	
            	//Same GUI implementation of clicking on the thumbnail
            	JDialog photoDialog = new JDialog();
                photoDialog.setSize(800, 800);
                photoDialog.setResizable(false);

                ImageIcon photoIcon = Photo.imageScaleWithInput(photo.getPhotoPath(), 300, 300);
                JLabel photoLabel = new JLabel(photoIcon);
                photoLabel.setHorizontalAlignment(JLabel.CENTER);

                JTextArea descriptionTextArea = new JTextArea(photo.getDescription());
                descriptionTextArea.setEditable(false);
                JScrollPane descriptionScrollPane = new JScrollPane(descriptionTextArea);
                descriptionScrollPane.setPreferredSize(new Dimension(480, 60));

                JLabel userLabel = new JLabel("Posted by: " + photo.getOwner().getNickname());
                userLabel.setFont(new Font("Arial", Font.BOLD, 18));

                JLabel tierLabel = new JLabel("User Tier: " + photo.getOwner().getUserTier());
                tierLabel.setFont(new Font("Arial", Font.BOLD, 15));

                JButton likeButton = new JButton("Like");
                JButton dislikeButton = new JButton("Dislike");

                JLabel likesLabel = new JLabel("Likes: " + photo.getLikes());
                JLabel dislikesLabel = new JLabel("Dislikes: " + photo.getDislikes());
                likesLabel.setFont(new Font("Arial", Font.BOLD, 13));
                dislikesLabel.setFont(new Font("Arial", Font.BOLD, 13));

                likeButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        photo.like();
                        likesLabel.setText("Likes: " + photo.getLikes());
                    }
                });

                dislikeButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        photo.dislike();
                        dislikesLabel.setText("Dislikes: " + photo.getDislikes());
                    }
                });

                JTextArea commentTextArea = new JTextArea();
                JScrollPane commentScrollPane = new JScrollPane(commentTextArea);
                commentScrollPane.setPreferredSize(new Dimension(480, 60));

                JButton postCommentButton = new JButton("Post Comment");
                postCommentButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        String comment = commentTextArea.getText();
                        (LoggedInUser.getLoggedInUser()).commentOnPhoto(LoggedInUser.getLoggedInUser(), photo, comment);
                    }
                });

                JTextArea commentDisplayTextArea = new JTextArea();
                commentDisplayTextArea.setEditable(false);
                JScrollPane commentDisplayScrollPane = new JScrollPane(commentDisplayTextArea);
                commentDisplayScrollPane.setPreferredSize(new Dimension(480, 180));

                ArrayList<String> comments = new ArrayList<>(photo.getComments());
                for (String comment : comments) {
                    commentDisplayTextArea.append(comment + "\n");
                }

                JPanel contentPanel = new JPanel(new BorderLayout());
                contentPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

                JPanel likesDislikesPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
                likesDislikesPanel.add(likeButton);
                likesDislikesPanel.add(dislikeButton);
                likesDislikesPanel.add(likesLabel);
                likesDislikesPanel.add(dislikesLabel);

                JPanel commentPanel = new JPanel(new BorderLayout());
                commentPanel.add(commentDisplayScrollPane, BorderLayout.CENTER);
                commentPanel.add(commentScrollPane, BorderLayout.NORTH);
                commentPanel.add(postCommentButton, BorderLayout.SOUTH);

                JPanel userPanel = new JPanel(new BorderLayout());
                userPanel.add(userLabel, BorderLayout.NORTH);
                userPanel.add(tierLabel, BorderLayout.SOUTH);

                JPanel northPanel = new JPanel(new BorderLayout());
                northPanel.add(userPanel, BorderLayout.NORTH);
                northPanel.add(likesDislikesPanel, BorderLayout.SOUTH);

                JPanel descriptionPanel = new JPanel(new BorderLayout());
                descriptionPanel.add(descriptionScrollPane, BorderLayout.CENTER);
                descriptionPanel.add(northPanel, BorderLayout.NORTH);

                JPanel photoPanel = new JPanel(new BorderLayout());
                photoPanel.add(photoLabel, BorderLayout.CENTER);
                photoPanel.add(descriptionPanel, BorderLayout.NORTH);

                contentPanel.add(photoPanel, BorderLayout.CENTER);
                contentPanel.add(commentPanel, BorderLayout.SOUTH);

                photoDialog.setContentPane(contentPanel);
                photoDialog.setVisible(true);
             }
        });

        photosPanel.add(photoPanel);
      }

        photosPanel.revalidate();
        photosPanel.repaint();
    }

    public void showGUI() {
    	// GUI implementation of the profile page
    	setTitle("Profile Page");
    	setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(800, 700);

        JPanel contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(10, 10, 10, 10));
        setContentPane(contentPane);
        contentPane.setLayout(new BorderLayout(0, 10));

        JPanel userInfoPanel = new JPanel();
        userInfoPanel.setLayout(new GridLayout(2, 2)); 
        contentPane.add(userInfoPanel, BorderLayout.NORTH);
                
        profilePhotoLabel = new JLabel();
        profilePhotoLabel.setHorizontalAlignment(SwingConstants.LEFT);
        profilePhotoLabel.setPreferredSize(new Dimension(100, 100));
        userInfoPanel.add(profilePhotoLabel);
        
        nameLabel = new JLabel();
        nameLabel.setFont(new Font(".AppleSystemUIFont", Font.BOLD, 13));
        nameLabel.setPreferredSize(new Dimension(100, 20));
        userInfoPanel.add(nameLabel);

        realNameLabel = new JLabel();
        realNameLabel.setFont(new Font(".AppleSystemUIFont", Font.PLAIN, 13));
        realNameLabel.setPreferredSize(new Dimension(100, 20));
        userInfoPanel.add(realNameLabel);
                
        ImageIcon profilePhoto = Photo.imageScaleWithInput("image/defaultprofilephoto.jpeg", 100, 100); // default
        if (profilePhoto != null) {
            profilePhotoLabel.setIcon(profilePhoto);
            userInfoPanel.add(profilePhotoLabel);
        }
        
        JScrollPane photosScrollPane = new JScrollPane(); // ScrollPane for more photos
        contentPane.add(photosScrollPane, BorderLayout.CENTER);

        photosPanel = new JPanel();
        photosPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        photosScrollPane.setViewportView(photosPanel);
        
    }
 
    protected static User getUser() { // Method to get user
        return user;
    }
    
    protected static List<Photo> getPhotos() { // Method to get photos
    	return photos;
    }

	public static void setUser(User user) { // Method to set user
		ProfilePageViewer.user = user;
	}

	public static void setPhotos(List<Photo> photos) { // Method to set photos 
		ProfilePageViewer.photos = photos;
	}
}

