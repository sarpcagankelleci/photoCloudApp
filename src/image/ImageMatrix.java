package image;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.UUID;

import javax.imageio.ImageIO;

public class ImageMatrix {

	private String id;
	private int[][] img;

	private int width;
	private int height;

	/**
	 * Builds up ImageMatrix from BufferedImage
	 * 
	 * @param BufferedImage image
	 * @see BufferedImage
	 */
	public ImageMatrix(BufferedImage image) {
		this.id = UUID.randomUUID().toString();
		this.img = convertImageToPixelArray(image);
		this.width = img.length;
		this.height = img[0].length;
	}

	public ImageMatrix(int width, int height) {
		this.id = UUID.randomUUID().toString();
		this.img = generateEmptyImageArray(width, height);
		this.width = img.length;
		this.height = img[0].length;

	}

	private int[][] generateEmptyImageArray(int width, int height) {
		return new int[width][height];
	}

	private int[][] convertImageToPixelArray(BufferedImage image) {
		int[][] im = new int[image.getWidth()][image.getHeight()];
		for (int i = 0; i < image.getWidth(); i++)
			for (int j = 0; j < image.getHeight(); j++) {
				im[i][j] = image.getRGB(i, j);
			}
		return im;
	}

	public BufferedImage getBufferedImage() {
		BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				bufferedImage.setRGB(i, j, img[i][j]);
			}
		}
		return bufferedImage;
	}

	public String getId() {
		return id;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public int getRGB(int x, int y) {
		return img[x][y];
	}

	public int setRGB(int x, int y, int rgb) {
		img[x][y] = rgb;
		return rgb;
	}

	public int getRed(int x, int y) {
		return (img[x][y] >> 16) & 0xFF;
	}

	public int getGreen(int x, int y) {
		return (img[x][y] >> 8) & 0xFF;
	}

	public int getBlue(int x, int y) {
		return img[x][y] & 0xFF;
	}

	public static int convertRGB(int red, int green, int blue) {
		return (red << 16 | green << 8 | blue);
	}

	public static ImageMatrix loadImage(String photoPath) {
		try {
			BufferedImage originalImage = ImageIO.read(new File(photoPath));
			ImageMatrix image = new ImageMatrix(originalImage);
			return image;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	 public String getPhotoPath() {
	        try {
	            File tempFile = File.createTempFile("temp", ".jpg"); // Create a temporary file
	            ImageIO.write(this.getBufferedImage(), "jpg", tempFile); // Write the ImageMatrix to the temporary file
	            return tempFile.getAbsolutePath(); // Return the absolute path of the temporary file
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	        return null; // Return null if an error occurs
	    }
	}
	

	
	



