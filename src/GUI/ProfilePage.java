package GUI;

import users.LoggedInUser;
import users.Photo;
import users.User;
import users.UserTier;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

import image.Filter;
import image.ImageMatrix;
import image.Filter.FilterType;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class ProfilePage extends JFrame {
    private static final long serialVersionUID = 1L;
	public static User user;
    public static List<Photo> photos;

    private JLabel nameLabel;
    private JLabel emailLabel;
    private JLabel realNameLabel;
    private JLabel profilePhotoLabel;
    private static JPanel photosPanel;
    private JButton editButton;
    private JPasswordField passwordField;
    private JButton applyFilterButton;
    private JComboBox<Integer> filteringDegreeComboBox;
    private JComboBox<String> chooseFilterComboBox;


    public ProfilePage(User user, List<Photo> photos) {
        ProfilePage.setUser(user);
        ProfilePage.setPhotos(photos);

        initializeUI();
        populateProfileData();
        populatePhotosPanel();
    }

    private void initializeUI() {
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
        emailLabel = new JLabel();
        emailLabel.setFont(new Font(".AppleSystemUIFont", Font.PLAIN, 13));
        emailLabel.setPreferredSize(new Dimension(100, 20));
        userInfoPanel.add(emailLabel);

        realNameLabel = new JLabel();
        realNameLabel.setFont(new Font(".AppleSystemUIFont", Font.PLAIN, 13));
        realNameLabel.setPreferredSize(new Dimension(100, 20));
        userInfoPanel.add(realNameLabel);

        ImageIcon profilePhoto = Photo.imageScaleWithInput(user.getProfilePhotoPath(), 100, 100);
        if (profilePhoto != null) {
            profilePhotoLabel.setIcon(profilePhoto);
            userInfoPanel.add(profilePhotoLabel);
        }
        
        JScrollPane photosScrollPane = new JScrollPane();
        contentPane.add(photosScrollPane, BorderLayout.CENTER);

        photosPanel = new JPanel();
        photosPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        photosScrollPane.setViewportView(photosPanel);

        editButton = new JButton("Edit Profile");
        editButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                openProfileEditingDialog(ProfilePage.getUser());
                
            }
        });
        
        JButton uploadButton = new JButton("Upload Photo");
        uploadButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	PhotoUploadPage photoUploadPage = new PhotoUploadPage();
                photoUploadPage.setVisible(true);
                populatePhotosPanel();
            
            }
        });
        
        JButton discoverPageButton = new JButton("Go to Discover Page");
        discoverPageButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	LoggedInUser.getLoggedInUser();
				DiscoverPage discoverPage = new DiscoverPage(User.getUserList(), LoggedInUser.getLoggedInUser());
            	discoverPage.setVisible(true);
            
            }
        });

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT)); 
        buttonPanel.add(uploadButton);
        buttonPanel.add(editButton);
        buttonPanel.add(discoverPageButton);
        contentPane.add(buttonPanel, BorderLayout.SOUTH);

    }

    private void populateProfileData() {
        nameLabel.setText(getUser().getNickname());
        emailLabel.setText("Email: " + getUser().getEmailAddress());
        
        ImageIcon profilePhotoIcon = Photo.imageScaleWithInput(getUser().getProfilePhotoPath(), 100, 100);
        profilePhotoLabel.setIcon(profilePhotoIcon);
           
        String age = Integer.toString(getUser().getAge());
        realNameLabel.setText("Name: " + getUser().getRealName() + " " +getUser().getRealSurname()+ " " + "Age: " + age );
   
    }


    public static void populatePhotosPanel() {
        photosPanel.removeAll();

        for (Photo photo : user.getUploadedPhoto()) {
            JPanel photoPanel = new JPanel();
            photoPanel.setLayout(new BorderLayout());
            photoPanel.setPreferredSize(new Dimension(150, 150));
            photoPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));

            ImageIcon thumbnail = Photo.imageScale(photo.getPhotoPath());
            JLabel thumbnailLabel = new JLabel(thumbnail);
            photoPanel.add(thumbnailLabel, BorderLayout.CENTER);
            photoPanel.setToolTipText(photo.getDescription());
            photoPanel.addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseClicked(java.awt.event.MouseEvent evt) {
                	final JDialog photoDialog = new JDialog();
                    photoDialog.setSize(800, 800);
                    photoDialog.setResizable(false);
                 ImageIcon photoIcon = Photo.imageScaleWithInput(photo.getPhotoPath(), 500, 500);
                    JLabel photoLabel = new JLabel(photoIcon);
                    photoLabel.setHorizontalAlignment(JLabel.CENTER);

                    JButton addToDiscoverButton = new JButton("Add to Discover Page");
                    addToDiscoverButton.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            // Add the photo to the Discover page
                        	DiscoverPage discoverPage = new DiscoverPage(User.getUserList(), LoggedInUser.getLoggedInUser());
                            DiscoverPage.addToDiscoverPage(discoverPage, photo);
                        }
                    });

                    JButton removeFromDiscoverButton = new JButton("Remove from Discover Page");
                    removeFromDiscoverButton.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            // Remove the photo from the Discover page
                            DiscoverPage discoverPage = new DiscoverPage(User.getUserList(), LoggedInUser.getLoggedInUser());
                            DiscoverPage.removeFromDiscoverPage(discoverPage, photo);
                        }
                    });
                    
                    JButton modifyButton = new JButton("Modify Photo");
                    modifyButton.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                        	ProfilePage profilePage = new ProfilePage(user, user.getUploadedPhoto());
                        	profilePage.applyFilterPage(photo);
                            
                        }
                    });
                    
                    JButton deleteButton = new JButton("Delete Photo");
                    deleteButton.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            int confirm = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete this photo?",
                                    "Confirm Deletion", JOptionPane.YES_NO_OPTION);
                            if (confirm == JOptionPane.YES_OPTION) {
                                user.deletePhoto(photo);
                                populatePhotosPanel();
                                
                                DiscoverPage discoverPage = new DiscoverPage(User.getUserList(), LoggedInUser.getLoggedInUser());
                                DiscoverPage.removeFromDiscoverPage(discoverPage, photo);
                                photoDialog.dispose();
                                
                                
                            }
                        }
                    });


                    JPanel contentPanel = new JPanel(new BorderLayout());
                    contentPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));


                    JPanel discoverButtonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
                    discoverButtonPanel.add(addToDiscoverButton);
                    discoverButtonPanel.add(removeFromDiscoverButton);
                    discoverButtonPanel.add(modifyButton, BorderLayout.SOUTH);
                    discoverButtonPanel.add(deleteButton);


                    JPanel photoContentPanel = new JPanel(new BorderLayout());
                    photoContentPanel.add(photoLabel, BorderLayout.CENTER);

                    contentPanel.add(photoContentPanel, BorderLayout.CENTER);

                    contentPanel.add(discoverButtonPanel, BorderLayout.NORTH);

                    photoDialog.setContentPane(contentPanel);
                    photoDialog.setVisible(true);
                }
            });

            photosPanel.add(photoPanel);
        }

        photosPanel.revalidate();
        photosPanel.repaint();
    }


    public void openProfileEditingDialog(User user) {
        ProfilePage.user = user;

        setTitle("Profile Editing");
        setSize(400, 300);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel contentPane = new JPanel();
        contentPane.setLayout(new BorderLayout());
        setContentPane(contentPane);

        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        contentPane.add(formPanel, BorderLayout.CENTER);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.LINE_START;
        gbc.insets = new Insets(5, 5, 10, 5); // Increase bottom inset for spacing

        // Real Name
        JLabel realnameLabel = new JLabel("Real Name:");
        JTextField realnameTextField = new JTextField(user.getRealName());
        formPanel.add(realnameLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        formPanel.add(realnameTextField, gbc);

        // Surname
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 0.0;
        gbc.fill = GridBagConstraints.NONE;
        JLabel surnameLabel = new JLabel("Surname:");
        JTextField surnameTextField = new JTextField(user.getRealSurname());
        formPanel.add(surnameLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.weightx = 1.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        formPanel.add(surnameTextField, gbc);

        // Password
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.weightx = 0.0;
        gbc.fill = GridBagConstraints.NONE;
        JLabel passwordLabel = new JLabel("Password:");
        JPasswordField passwordField = new JPasswordField(user.getPassword());
        formPanel.add(passwordLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.weightx = 1.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        formPanel.add(passwordField, gbc);

        // Email
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.weightx = 0.0;
        gbc.fill = GridBagConstraints.NONE;
        JLabel emailLabel = new JLabel("Email:");
        JTextField emailTextField = new JTextField(user.getEmailAddress());
        formPanel.add(emailLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.weightx = 1.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        formPanel.add(emailTextField, gbc);

        // Age
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.weightx = 0.0;
        gbc.fill = GridBagConstraints.NONE;
        JLabel ageLabel = new JLabel("Age:");
        JTextField ageTextField = new JTextField(String.valueOf(user.getAge()));
        formPanel.add(ageLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 4;
        gbc.weightx = 1.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        formPanel.add(ageTextField, gbc);

        // Profile Photo
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.weightx = 0.0;
        gbc.fill = GridBagConstraints.NONE;
        JLabel profilePhotoLabel = new JLabel("Profile Photo:");
        JTextField profilePhotoTextField = new JTextField(user.getProfilePhotoPath());
        formPanel.add(profilePhotoLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 5;
        gbc.weightx = 1.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        formPanel.add(profilePhotoTextField, gbc);

        gbc.gridx = 2;
        gbc.gridy = 5;
        gbc.weightx = 0.0;
        gbc.fill = GridBagConstraints.NONE;
        JButton browseButton = new JButton("Browse");
        formPanel.add(browseButton, gbc);

        JButton saveButton = new JButton("Save");
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.add(saveButton);
        contentPane.add(buttonPanel, BorderLayout.SOUTH);

        // ActionListener for browseButton and saveButton goes here
        browseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                int result = fileChooser.showOpenDialog(null);
                if (result == JFileChooser.APPROVE_OPTION) {
                    String selectedFilePath = fileChooser.getSelectedFile().getPath();
                    profilePhotoTextField.setText(selectedFilePath);
                }
            }
        });


        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveChanges(realnameTextField.getText(), surnameTextField.getText(),
            	    ageTextField.getText(), emailTextField.getText(), profilePhotoTextField.getText());
            }
        });
    }

    

    private void saveChanges(String realName, String surname, String age, String newEmail, String profilePhotoPath) {
    	System.out.println(profilePhotoPath);

    	
    	if (passwordField != null) {
    		String newPassword = new String(passwordField.getPassword());
    		user.changePassword(newPassword);
    	}

        user.setRealName(realName);
        user.setSurname(surname);
        int age2 = Integer.parseInt(age);
        user.setAge(Integer.parseInt(age));
        user.setEmailAddress(newEmail);
        user.setProfilePhoto(profilePhotoPath);

        user.changePersonalInfo(realName, surname, age2, newEmail, profilePhotoPath);

        nameLabel.setText(user.getNickname());
        emailLabel.setText("Email: " + user.getEmailAddress());
        ImageIcon profilePhoto = Photo.imageScaleWithInput(profilePhotoPath, 100, 100);
        if (profilePhoto != null) {
            profilePhotoLabel.setIcon(profilePhoto);
            
        }
    	
        dispose();
        ProfilePage profilePage = new ProfilePage(LoggedInUser.getLoggedInUser(), LoggedInUser.getLoggedInUser().getUploadedPhoto());
        profilePage.setVisible(true);
    }

    public void applyFilterPage(Photo photo) {
    	   	
        JFrame frame = new JFrame("Image Filter Section");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.getContentPane().setLayout(new BorderLayout());

        applyFilterButton = new JButton("Apply Filter");

        filteringDegreeComboBox = new JComboBox<>(new Integer[]{1, 2, 3, 4, 5});
        chooseFilterComboBox = new JComboBox<>(new String[]{"BLUR", "SHARPEN", "GRAYSCALE", "EDGEDETECTION", "BRIGHTNESS", "CONTRAST"});


        JPanel buttonPanel = new JPanel();
        buttonPanel.add(applyFilterButton);


        JPanel controlPanel = new JPanel();
        controlPanel.setLayout(new FlowLayout());
        controlPanel.add(new JLabel("Filtering Degree:"));
        controlPanel.add(filteringDegreeComboBox);
        controlPanel.add(new JLabel("Choose Filter:"));
        controlPanel.add(chooseFilterComboBox);
        

        frame.getContentPane().add(buttonPanel, BorderLayout.SOUTH);
        frame.getContentPane().add(controlPanel, BorderLayout.NORTH);
        

        applyFilterButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                    int filteringDegree = (Integer) filteringDegreeComboBox.getSelectedItem();
                    String filter = (String) chooseFilterComboBox.getSelectedItem();
                    ImageMatrix originalImage = ImageMatrix.loadImage(photo.getPhotoPath());
                    applyFilters(originalImage, filter, filteringDegree);
            }
        });
        
        
        
        frame.pack();
        frame.setVisible(true);
    }


    private void applyFilters(ImageMatrix originalImage, String filterType, int filteringDegree) {
        UserTier userTier = getUserTier();
        FilterType[] availableFilters = getFiltersForTier(userTier);
        FilterType selectedFilter = getFilterType(filterType);

        if (selectedFilter != null && isFilterAvailable(availableFilters, selectedFilter)) {
            ImageMatrix filteredImage = Filter.applyFilter(originalImage, selectedFilter, filteringDegree);
            showFilteredImageView(filteredImage);
            
            
        }
        else {
        	String errorMessage = "Selected filter is not available for the user's tier.";
            JOptionPane.showMessageDialog(null, errorMessage, "Filter Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void showFilteredImageView(ImageMatrix filteredImage) {
        JFrame frame = new JFrame("Filtered Image");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(500, 500);
        frame.getContentPane().setLayout(new BorderLayout());
        
        JLabel filteredImageLabel = new JLabel(Photo.imageScaleWithInput(filteredImage.getPhotoPath(), 400, 400));
        frame.getContentPane().add(filteredImageLabel, BorderLayout.CENTER);

        JButton shareButton = new JButton("Share to Profile Page");
        shareButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String photoPath = filteredImage.getPhotoPath();
                if (!photoPath.isEmpty()) {
                    User user = ProfilePage.getUser();
                    user.uploadPhoto(photoPath);
                    ProfilePage.populatePhotosPanel();
                    JOptionPane.showMessageDialog(null, "Filtered image shared successfully!", "Save Image", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });

        JButton saveButton = new JButton("Save Filtered Image");
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setDialogTitle("Save Filtered Image");
                fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
                fileChooser.setFileFilter(new FileNameExtensionFilter("JPEG Images", "jpg"));

                int result = fileChooser.showSaveDialog(null);
                if (result == JFileChooser.APPROVE_OPTION) {
                    File outputFile = fileChooser.getSelectedFile();
                    try {
                        ImageIO.write(filteredImage.getBufferedImage(), "jpg", outputFile);
                        JOptionPane.showMessageDialog(null, "Filtered image saved successfully!", "Save Image", JOptionPane.INFORMATION_MESSAGE);
                    } catch (IOException ex) {
                        String errorMessage = "Error while saving the filtered image.";
                        JOptionPane.showMessageDialog(null, errorMessage, "Save Image Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(shareButton);
        buttonPanel.add(saveButton);

        frame.getContentPane().add(buttonPanel, BorderLayout.SOUTH);
        frame.setVisible(true);
    }

    private FilterType getFilterType(String filterType) {
        switch (filterType) {
            case "BLUR":
                return FilterType.BLUR;
            case "SHARPEN":
                return FilterType.SHARPEN;
            case "BRIGHTNESS":
                return FilterType.BRIGHTNESS;
            case "CONTRAST":
                return FilterType.CONTRAST;
            case "GRAYSCALE":
                return FilterType.GRAYSCALE;
            case "EDGEDETECTION":
                return FilterType.EDGEDETECTION;
            default:
                return null;
        }
    }

    private boolean isFilterAvailable(FilterType[] availableFilters, FilterType selectedFilter) {
        for (FilterType filter : availableFilters) {
            if (filter == selectedFilter) {
                return true;
            }
        }
        return false;
    }



    private FilterType[] getFiltersForTier(UserTier userTier) {
        List<FilterType> availableFilters = new ArrayList<>();

        switch (userTier) {
            case FREE:
                availableFilters.add(FilterType.BLUR);
                availableFilters.add(FilterType.SHARPEN);
                break;
            case HOBBYIST:
                availableFilters.add(FilterType.BLUR);
                availableFilters.add(FilterType.SHARPEN);
                availableFilters.add(FilterType.BRIGHTNESS);
                availableFilters.add(FilterType.CONTRAST);
                break;
            case PROFESSIONAL:
                availableFilters.add(FilterType.BLUR);
                availableFilters.add(FilterType.SHARPEN);
                availableFilters.add(FilterType.BRIGHTNESS);
                availableFilters.add(FilterType.CONTRAST);
                availableFilters.add(FilterType.GRAYSCALE);
                availableFilters.add(FilterType.EDGEDETECTION);
                break;
            default:
                break;
        }

        return availableFilters.toArray(new FilterType[0]);
    }

    public UserTier getUserTier() {
        return ProfilePage.getUser().getUserTier();
    }
   
    
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
    protected static User getUser() { // method to get user
        return user;
    }
    
    protected static List<Photo> getPhotos() { // method to get photos
    	return photos;
    }

	public static void setUser(User user) { // method to set user
		ProfilePage.user = user;
	}

	public static void setPhotos(List<Photo> photos) { // method to set photos
		ProfilePage.photos = photos;
	}
}
