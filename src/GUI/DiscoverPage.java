package GUI;

import users.LoggedInUser;
import users.Photo;
import users.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class DiscoverPage extends JFrame {
    private static final long serialVersionUID = 1L;
	private static User loggedInUser;
    private JPanel mainPanel;

    public DiscoverPage(ArrayList<User> users, User loggedInUser) {

        // Main panel 
        mainPanel = new JPanel(new GridLayout(0, 3, 10, 10));
        mainPanel.setBackground(Color.BLACK); 
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Loops for grid layout
        for (User user : users) {
        	for (int j = 1; j <= user.getUploadedPhoto().size(); j++) {
            if (user.getUploadedPhoto() == null) {
                continue;
            }
            JPanel cellPanel = new JPanel();
            cellPanel.setLayout(new BorderLayout());
            cellPanel.setBackground(Color.BLACK); 

        	Photo photo = user.getUploadedPhoto().get(j-1);
        	ImageIcon thumbnail = Photo.imageScale(photo.getPhotoPath());
        	JLabel thumbnailLabel = new JLabel(thumbnail);
        	thumbnailLabel.setHorizontalAlignment(JLabel.CENTER);
        	thumbnailLabel.putClientProperty("photo", photo);
        	thumbnailLabel.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    openPhotoView(photo);
                }
            });
        	
        	cellPanel.add(thumbnailLabel, BorderLayout.CENTER);

            JLabel nicknameLabel = new JLabel(user.getNickname());
            nicknameLabel.setHorizontalAlignment(JLabel.CENTER);
            nicknameLabel.setFont(new Font("Arial", Font.BOLD, 16));
            nicknameLabel.setForeground(Color.WHITE); 
            nicknameLabel.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    openUserProfileViewer(user);
                }
            });
            
            cellPanel.add(nicknameLabel, BorderLayout.SOUTH);
            mainPanel.add(cellPanel);
        }
        }

        JScrollPane scrollPane = new JScrollPane(mainPanel);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        JButton profileButton = new JButton("My Profile");
        Dimension buttonSize = new Dimension(100, 30); 
        profileButton.setPreferredSize(buttonSize);
        profileButton.setBackground(Color.GRAY); 
        profileButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                openUserProfile(LoggedInUser.getLoggedInUser());
                dispose();
            }
        });

        getContentPane().setLayout(new BorderLayout());
        getContentPane().setBackground(Color.BLACK); 
        getContentPane().add(scrollPane, BorderLayout.CENTER);
        getContentPane().add(profileButton, BorderLayout.SOUTH);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setTitle("Discover Page");
        setSize(1400, 800);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public void openPhotoView(Photo photo) {
        if (photo == null) {
            JOptionPane.showMessageDialog(DiscoverPage.this, "Invalid photo");
            return;
        }

        JDialog photoDialog = new JDialog(this, "Photo View", Dialog.ModalityType.APPLICATION_MODAL);
        photoDialog.setSize(800, 800);
        photoDialog.setResizable(false);

        ImageIcon photoIcon = Photo.imageScaleWithInput(photo.getPhotoPath(), 300, 300);
        JLabel photoLabel = new JLabel(photoIcon);
        photoLabel.setHorizontalAlignment(JLabel.CENTER);

        JTextArea descriptionTextArea = new JTextArea(photo.getDescription());
        descriptionTextArea.setEditable(false);
        JScrollPane descriptionScrollPane = new JScrollPane(descriptionTextArea);
        descriptionScrollPane.setPreferredSize(new Dimension(380, 60));

        JLabel userLabel = new JLabel("Posted by: " + photo.getOwner().getNickname());
        userLabel.setFont(new Font("Arial", Font.BOLD, 18));

        JLabel tierLabel = new JLabel("User Tier: " + photo.getOwner().getUserTier());
        tierLabel.setFont(new Font("Arial", Font.BOLD, 14));
        
        JLabel profilePhotoLabel = new JLabel();
        ImageIcon profilePhotoIcon = Photo.imageScaleWithInput(photo.getOwner().getProfilePhotoPath(), 100, 100);
        profilePhotoLabel.setIcon(profilePhotoIcon);
        profilePhotoLabel.setPreferredSize(new Dimension(100, 100));

        JButton likeButton = new JButton("Like");
        JButton dislikeButton = new JButton("Dislike");

        JLabel likesLabel = new JLabel("Likes: " + photo.getLikes());
        JLabel dislikesLabel = new JLabel("Dislikes: " + photo.getDislikes());
        likesLabel.setFont(new Font("Arial", Font.BOLD, 12));
        dislikesLabel.setFont(new Font("Arial", Font.BOLD, 12));

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
        commentScrollPane.setPreferredSize(new Dimension(380, 60));

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
        userPanel.add(profilePhotoLabel, BorderLayout.EAST);

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

    public void openUserProfile(User user) { // method to open user profile 
	    ProfilePage userProfilePage = new ProfilePage(user, user.getUploadedPhoto());
	    userProfilePage.setVisible(true);
    	}
    
    public void openUserProfileViewer(User user) { // method to open user profile in viewer mode
	    ProfilePageViewer userProfilePageViewer = new ProfilePageViewer(user, user.getUploadedPhoto());
	    userProfilePageViewer.setVisible(true);
	}
    
    public static ArrayList<User> getUserData() {
    	ArrayList<User> users = User.getUserList();
        return users;
    }

	public void showDiscoverPage() {
	    setVisible(true);
	}
	
	public void closeDiscoverPage() {
		dispose();
	}
	
	public static void refreshPage(ArrayList<User> users) { // Method to refresh the discover Page
        DiscoverPage discoverPage = new DiscoverPage(users, loggedInUser);
        discoverPage.setVisible(true);
    }
	
	public JPanel getMainPanel() {
		return mainPanel;
	    }

	public static void removeFromDiscoverPage(DiscoverPage discoverPage, Photo photo) { // Method to remove from Discover Page
	    JPanel mainPanel = discoverPage.getMainPanel();

	    // Iterating over the grid layout to find the specified photo
	    for (Component component : mainPanel.getComponents()) {
	        if (!(component instanceof JPanel)) {
	            continue;
	        }

	        JPanel cellPanel = (JPanel) component;

	        Component[] components = cellPanel.getComponents();
	        if (components.length < 2 || !(components[0] instanceof JLabel)) {
	            continue;
	        }

	        JLabel thumbnailLabel = (JLabel) components[0];
	        ImageIcon thumbnailIcon = (ImageIcon) thumbnailLabel.getIcon();
	        if (thumbnailIcon == null) {
	            continue;
	        }

	        Photo thumbnailPhoto = (Photo) thumbnailLabel.getClientProperty("photo");
	        if (thumbnailPhoto == photo) {
	            mainPanel.remove(cellPanel);
	            mainPanel.revalidate();
	            mainPanel.repaint();
	            break;
	        }
	    }
	}

	public static void addToDiscoverPage(DiscoverPage discoverPage, Photo photo) { // Method to add to Discover Page
	
	    JPanel mainPanel = discoverPage.getMainPanel();
	    JPanel cellPanel = new JPanel();
	    cellPanel.setLayout(new BorderLayout());

	    JLabel thumbnailLabel = new JLabel();
	    thumbnailLabel.setIcon(photo.getThumbnail(photo)); 
	    thumbnailLabel.setHorizontalAlignment(SwingConstants.CENTER);
	    thumbnailLabel.setPreferredSize(new Dimension(100, 100));
	    thumbnailLabel.putClientProperty("photo", photo); // Storing the photo as client property to use after
	    cellPanel.add(thumbnailLabel, BorderLayout.CENTER);
	
	    mainPanel.add(cellPanel);
	    mainPanel.revalidate();
	    mainPanel.repaint();
	}
	
	// Main method to invoke
	public static void main(String[] args) {
	        SwingUtilities.invokeLater(() -> {
	            ArrayList<User> users = getUserData(); // Retrieve user data
	            DiscoverPage discoverPage = new DiscoverPage(users, loggedInUser); // Pass the users as an argument
	            discoverPage.setVisible(true);
	        });
	    }

}
