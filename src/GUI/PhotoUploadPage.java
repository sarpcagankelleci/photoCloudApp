package GUI;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import users.User;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PhotoUploadPage extends JFrame {
    private static final long serialVersionUID = 1L;
	private JTextField photoPathTextField;
    private JButton browseButton;
    private JButton uploadButton;

    public PhotoUploadPage() {
        initializeUI();
        Listeners();
    }

    private void initializeUI() {
    	
    	// GUI implementation
        setTitle("Photo Upload");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(400, 150);
        setLocationRelativeTo(null);

        JPanel contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(10, 10, 10, 10));
        setContentPane(contentPane);
        contentPane.setLayout(new BorderLayout(10, 0));

        photoPathTextField = new JTextField();
        contentPane.add(photoPathTextField, BorderLayout.CENTER);
        
        uploadButton = new JButton("Upload");
        contentPane.add(uploadButton, BorderLayout.SOUTH);

        browseButton = new JButton("Browse");
        contentPane.add(browseButton, BorderLayout.EAST);
        
    }

    public void Listeners() {
        browseButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                int result = fileChooser.showOpenDialog(PhotoUploadPage.this);
                if (result == JFileChooser.APPROVE_OPTION) {
                    String selectedFilePath = fileChooser.getSelectedFile().getAbsolutePath();
                    photoPathTextField.setText(selectedFilePath);
                }
            }
        });

       uploadButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
      
                String photoPath = photoPathTextField.getText();
                if (!photoPath.isEmpty()) {
                    User user = ProfilePage.getUser();
					user.uploadPhoto(photoPath);
					ProfilePage.populatePhotosPanel();
				
                    JOptionPane.showMessageDialog(PhotoUploadPage.this, "Photo uploaded successfully! You can remove the uploaded photo to the discover page.");
                    dispose();
                     
                } else {
                    JOptionPane.showMessageDialog(PhotoUploadPage.this, "Make a selection.");
                }
            }
        });
    }

    // Main method to invoke
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                PhotoUploadPage photoUploadPage = new PhotoUploadPage();
                photoUploadPage.setVisible(true);
            }
        });
    }
}
