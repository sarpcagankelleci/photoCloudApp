package main;

import GUI.LoginPage;
import users.Administrator;
import users.BaseLogger;
import users.User;
import users.UserTier;

/************** Pledge of Honor ******************************************
I hereby certify that I have completed this programming project on my own without any help from anyone else. The effort in the project thus belongs completely to me. I did not search for a solution, or I did not consult any program written by others or did not copy any program from other sources. I read and followed the guidelines provided in the project description.
READ AND SIGN BY WRITING YOUR NAME SURNAME AND STUDENT ID
SIGNATURE: <Sarp Çağan Kelleci, 79482> *************************************************************************/
public class Main {
	public static void main(String[] args) {
		
		BaseLogger.info().log("PhotoCloud Application started.");
		
		long startTime = System.currentTimeMillis();
		
		User user1 = new User("Sarp", "Sarp123", "Sarp", "Kelleci", 20, "skelleci21@ku.edu.tr", UserTier.PROFESSIONAL);
		
		User.addUserToList(user1);
		
		long endTime = System.currentTimeMillis();
		long elapsedTime = endTime - startTime;
		BaseLogger.info().log("A new user object created and added to userlist: " + user1.getNickname() + " took: "+ elapsedTime + "ms");
		
		user1.uploadPhoto("image/a.jpg");
		user1.uploadPhoto("image/k.jpg");
		user1.sharePhoto("image/a.jpg");
		user1.sharePhoto("image/k.jpg");
		user1.getLastUploadedPhoto().setDescription("From today...");
		user1.setProfilePhoto("image/ss.jpg");

		
		long startTime2 = System.currentTimeMillis();
		
		User user2 = new User("Alper", "Alper123", "Alper", "Şahin", 19, "alpersahin@gmail.com", UserTier.PROFESSIONAL);
		
		User.addUserToList(user2);
		
		long endTime2 = System.currentTimeMillis();
		long elapsedTime2 = endTime2 - startTime2;
		BaseLogger.info().log("A new user object created and added to userlist: " + user2.getNickname() + " took: "+ elapsedTime2 + "ms");
		
		user2.uploadPhoto("image/b.jpg");
		user2.uploadPhoto("image/l.jpg");
		user2.sharePhoto("image/b.jpg");
		user2.sharePhoto("image/l.jpg");
		user2.likePhoto(user1.getLastUploadedPhoto());
		user2.dislikePhoto(user1.getLastUploadedPhoto());
		user2.commentOnPhoto(user2, user1.getLastUploadedPhoto(), "nice photo");
		user2.getLastUploadedPhoto().setDescription("Carpe Diem");
		user2.setProfilePhoto(user2.getProfilePhotoPath());
		user2.setProfilePhoto("image/defaultprofilephoto.jpeg");
		
		long startTime3 = System.currentTimeMillis();
		
		User user3 = new User("Serbay","Serbay123","Serbay","Uğur", 20,"kugur21@ku.edu.tr", UserTier.PROFESSIONAL);
		
		User.addUserToList(user3);
		long endTime3 = System.currentTimeMillis();
		long elapsedTime3 = endTime3 - startTime3;
		BaseLogger.info().log("A new user object created and added to userlist: " + user3.getNickname() + " took: "+ elapsedTime3 + "ms");
		
		user3.uploadPhoto("image/c.jpg");
		user3.uploadPhoto("image/m.jpg");
		user3.sharePhoto("image/c.jpg");
		user3.sharePhoto("image/m.jpg");
		
		user3.setProfilePhoto("image/defaultprofilephoto.jpeg");
		user3.getLastUploadedPhoto().setDescription("Wow!");
		
		long startTime4 = System.currentTimeMillis();
		
		User user4 = new User("Talin","Talin123","Talin","Harun", 20 ,"tharun21@ku.edu.tr", UserTier.HOBBYIST);
		
		User.addUserToList(user4);
		long endTime4 = System.currentTimeMillis();
		long elapsedTime4 = endTime4 - startTime4;
		BaseLogger.info().log("A new user object created and added to userlist: " + user4.getNickname() + " took: "+ elapsedTime4 + "ms");
		
		user4.uploadPhoto("image/d.jpg");
		user4.sharePhoto("image/d.jpg");
		user4.setProfilePhoto("image/defaultprofilephoto.jpeg");
		
		long startTime5 = System.currentTimeMillis();
		
		User user5 = new User("Sinem", "Sinem123", "Sinem", "İşcan", 20, "siscan21@ku.edu.tr", UserTier.FREE);
		
		User.addUserToList(user5);
		long endTime5 = System.currentTimeMillis();
		long elapsedTime5 = endTime5 - startTime5;
		BaseLogger.info().log("A new user object created and added to userlist: " + user5.getNickname() + " took: "+ elapsedTime5 + "ms");
		
		user5.uploadPhoto("image/e.jpg");
		user5.sharePhoto("image/e.jpg");
		
		user5.setProfilePhoto("image/defaultprofilephoto.jpeg");
		
		long startTime6 = System.currentTimeMillis();
		
		User user6 = new User("Mustafa","Mustafa123","Mustafa","Aksu", 20, "mustafaaksu@gmail.com", UserTier.HOBBYIST);
		
		User.addUserToList(user6);
		long endTime6 = System.currentTimeMillis();
		long elapsedTime6 = endTime6 - startTime6;
		BaseLogger.info().log("A new user object created and added to userlist: " + user6.getNickname() + " took: "+ elapsedTime6 + "ms");
		
		user6.uploadPhoto("image/j.jpg");
		user6.sharePhoto("image/j.jpg");
		user6.setProfilePhoto("image/defaultprofilephoto.jpeg");
		
		long startTime7 = System.currentTimeMillis();
		
		User user7 = new User("Ali","Ali123","Ali","Altınel", 20, "aaltinel21@ku.edu.tr", UserTier.HOBBYIST);
		
		User.addUserToList(user7);
		long endTime7 = System.currentTimeMillis();
		long elapsedTime7 = endTime7 - startTime7;
		BaseLogger.info().log("A new user object created and added to userlist: " + user7.getNickname() + " took: "+ elapsedTime7 + "ms");
		
		user7.uploadPhoto("image/f.jpg");
		user7.sharePhoto("image/f.jpg");
		user7.setProfilePhoto("image/defaultprofilephoto.jpeg");
		
		long startTime8 = System.currentTimeMillis();
		
		User user8 = new User("Derin","Derin123","Derin","Kelleci", 16, "derinkelleci@gmail.com", UserTier.FREE);
		
		User.addUserToList(user8);
		long endTime8 = System.currentTimeMillis();
		long elapsedTime8 = endTime8 - startTime8;
		BaseLogger.info().log("A new user object created and added to userlist: " + user8.getNickname() + " took: "+ elapsedTime8 + "ms");
		
		user8.uploadPhoto("image/g.jpg");
		user8.sharePhoto("image/g.jpg");
		user8.setProfilePhoto("image/defaultprofilephoto.jpeg");
		
		long startTime9 = System.currentTimeMillis();
		
		User user9 = new User("Suel","Suel123","Suel","Akşahin", 19, "saksahin21@ku.edu.tr", UserTier.FREE);
		
		User.addUserToList(user9);
		long endTime9 = System.currentTimeMillis();
		long elapsedTime9 = endTime9 - startTime9;
		BaseLogger.info().log("A new user object created and added to userlist: " + user9.getNickname() + " took: "+ elapsedTime9 + "ms");
		
		user9.uploadPhoto("image/h.jpg");
		user9.sharePhoto("image/h.jpg");
		user9.setProfilePhoto("image/defaultprofilephoto.jpeg");
		
		long startTime10 = System.currentTimeMillis();
		
		Administrator admin = new Administrator("admin", "password", "Admin", "Admin", 30, "admin@admin.com", UserTier.PROFESSIONAL);
		
		User.addUserToList(admin);
		long endTime10 = System.currentTimeMillis();
		long elapsedTime10 = endTime10 - startTime10;
		BaseLogger.info().log("A new user object created and added to userlist: " + admin.getNickname() + " took: "+ elapsedTime10 + "ms");
		
		LoginPage Login = new LoginPage();
		Login.setVisible(true);
		
}
}
