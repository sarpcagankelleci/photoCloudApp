package image;

public class Filter {
	public enum FilterType { // enum type of filters to detect the available filters for user tiers
        BLUR,
        SHARPEN,
        GRAYSCALE,
        EDGEDETECTION,
        BRIGHTNESS,
        CONTRAST,
        
    }

    public static ImageMatrix applyFilter(ImageMatrix image, FilterType filterType, int filteringDegree) {
       if (filterType == FilterType.BLUR) {
            return applyBlurFilter(image, filteringDegree); 
            
        } else if (filterType == FilterType.SHARPEN) {
        	return applySharpenFilter(image, filteringDegree);
        	
        } else if (filterType == FilterType.GRAYSCALE) {
            return applyGrayscaleFilter(image, filteringDegree);
            
        } else if (filterType == FilterType.EDGEDETECTION) {
        	return applyEdgeDetectionFilter(image, filteringDegree);
        	
        } else if (filterType == FilterType.BRIGHTNESS) {
        	return applyBrightnessFilter(image, filteringDegree);
        	
        } else if (filterType == FilterType.CONTRAST) {
        	return applyContrastFilter(image, filteringDegree);
        	
        } else {
            throw new IllegalArgumentException("Invalid!!!");
        }
    }

	public static ImageMatrix applyBlurFilter(ImageMatrix image, int filteringDegree) { // Method for Blur Filter 
	    ImageMatrix filteredImage = new ImageMatrix(image.getWidth(), image.getHeight()); // Creating a new image object
	    
	    // Changing the kernel size according to the filtering degree input
	    int kernelSize;
	    
	    if (filteringDegree == 1) {
	    	kernelSize = 3;
	    }
	    else if (filteringDegree == 2) {
	    	kernelSize = 5;
	    }
	    else if (filteringDegree == 3) {
	    	kernelSize = 7;
	    }
	    else if (filteringDegree == 4) {
	    	kernelSize = 9;
	    }
	    else if (filteringDegree == 5) {
	    	kernelSize = 11;
	    }
	    else {
	        throw new IllegalArgumentException("Invalid filtering degree");
	    }
	    
	    // Iterating over each pixel of the image
	    for (int i = 0; i < image.getWidth(); i++) { 
	        for (int j = 0; j < image.getHeight(); j++) { 
	        	
	        	// The sum of RGB values are initially zero 
	        	int numOfNeighboringPixels = 0;
	            int sumOfRed = 0;
	            int sumOfGreen = 0;
	            int sumOfBlue = 0;

	            // Iterating over each neighbor pixels of the image
	            // Adding and subtracting kernelSize to implement the blur filter correctly
	            for (int k = i - kernelSize; k <= i + kernelSize; k++) { 
	                for (int l = j - kernelSize; l <= j + kernelSize; l++) {
	                	
	                    // Checking the bounds of the image
	                    if (k >= 0 && k < image.getWidth() && l >= 0 && l < image.getHeight()) {
	                    	sumOfRed += image.getRed(k, l);
	                    	sumOfGreen += image.getGreen(k, l);
	                        sumOfBlue += image.getBlue(k, l);
	                        numOfNeighboringPixels ++;
	                    }
	                }
	            }
	            // Calculating the average value of the neighboring pixels
	            int redAvg = sumOfRed / numOfNeighboringPixels;;
	            int greenAvg = sumOfGreen / numOfNeighboringPixels;;
	            int blueAvg = sumOfBlue / numOfNeighboringPixels;;
	            
	            // Setting the pixel value of the result image
	            int RGBofImage = ImageMatrix.convertRGB(redAvg, greenAvg, blueAvg);
	            filteredImage.setRGB(i, j, RGBofImage);
	        }
	    }
	    return filteredImage;
	}
	
	public static ImageMatrix applySharpenFilter(ImageMatrix image, int filteringDegree) { // Method for Sharpen Filter
	    	
	        ImageMatrix blurredImage = applyBlurFilter(image, filteringDegree); // Applying the Blur Filter
	        ImageMatrix subtractedImage = subtractImages(image, blurredImage); // Subtracting the blurred image from the original image
	        ImageMatrix sharpenedImage = addImages(image, subtractedImage); // Adding adjusted details back to original image

	        return sharpenedImage;
	    }
	
	// Necessary methods of the Sharpen Filter
	protected static ImageMatrix subtractImages(ImageMatrix image1, ImageMatrix image2) { // SubtractImages method for Sharpen Filter
	    	int widthOfImage = image1.getWidth();
	    	int heightOfImage = image1.getHeight();
	    	
	    	ImageMatrix subtractedImage = new ImageMatrix(widthOfImage, heightOfImage); // Creating a new Image Matrix

	    	// Pixel-wise subtraction of images
	    	for (int i = 0; i < widthOfImage; i++) {
	    		for (int j = 0; j < heightOfImage; j++) {      

	    	        // Subtracting the RGB values of the pixels
	    	        int newRed = Math.max(0, image1.getRed(i, j) - image2.getRed(i, j));
	    	        int newGreen = Math.max(0, image1.getGreen(i, j) - image2.getGreen(i, j));
	    	        int newBlue = Math.max(0, image1.getBlue(i, j) - image2.getBlue(i, j));

	    	        // Setting the new RGB values in the result image
	    	        int newRGB = ImageMatrix.convertRGB(newRed, newGreen, newBlue);
	    	        subtractedImage.setRGB(i, j, newRGB);
	    	        }
	    	    }
	    	    return subtractedImage;
	    	}
	
	 // Similar process with subtractImages
	 protected static ImageMatrix addImages(ImageMatrix image1, ImageMatrix image2) { // AddImages method for Sharpen Filter
		 int width = image1.getWidth();
		 int height = image1.getHeight();

		 ImageMatrix addedImage = new ImageMatrix(width, height);

	    	    // Perform pixel-wise addition
	    	    for (int i = 0; i < width; i++) {
	    	        for (int j = 0; j < height; j++) {

	    	            // Adding the RGB values of the pixels
	    	            int newRed = Math.min(255, image1.getRed(i, j) + image2.getRed(i, j));
	    	            int newGreen = Math.min(255, image1.getGreen(i, j) + image2.getGreen(i, j));
	    	            int newBlue = Math.min(255, image1.getBlue(i, j) + image2.getBlue(i, j));

	    	            // Setting the new RGB values in the result image
	    	            int newRGB = ImageMatrix.convertRGB(newRed, newGreen, newBlue);
	    	            addedImage.setRGB(i, j, newRGB);
	    	        }
	    	    }

	    	    return addedImage;
	    	}
	    
	 public static ImageMatrix applyGrayscaleFilter(ImageMatrix image, int filteringDegree) { // Method for Grayscale Filter
	        int width = image.getWidth();
	        int height = image.getHeight();
	        
	        // According to filtering Degree, I assign a percentage of filtering
	        int filteringDegreePercentage;
	        
	        if (filteringDegree == 1) {
	        	filteringDegreePercentage = 20;
		    }
		    else if (filteringDegree == 2) {
		    	filteringDegreePercentage = 40;
		    }
		    else if (filteringDegree == 3) {
		    	filteringDegreePercentage = 60;
		    }
		    else if (filteringDegree == 4) {
		    	filteringDegreePercentage = 80;
		    }
		    else if (filteringDegree == 5) {
		    	filteringDegreePercentage = 100;
		    }
		    else {
		        throw new IllegalArgumentException("Invalid filtering degree: " + filteringDegree);
		    }
	        
	        // Iterating over each pixel of the photo
	        for (int x = 0; x < width; x++) {
	            for (int y = 0; y < height; y++) {

	                int red = image.getRed(x, y);
	                int green = image.getGreen(x, y);
	                int blue = image.getBlue(x, y);
	                
	                // Adding the red, green and blue values and dividing by 3 
	                int grayValue = (red + green + blue) / 3;
	                grayValue = ((255 - grayValue) * filteringDegreePercentage) / 100;  // Special operation for the grayValue
	              
	                image.setRGB(x, y, ImageMatrix.convertRGB(grayValue, grayValue, grayValue));
	            }
	        }

	        return image;
	    }
			
	 public static ImageMatrix applyEdgeDetectionFilter(ImageMatrix image, int filteringDegree) { // Method for Edge Detection Filter
	    ImageMatrix grayscaleImage = applyGrayscaleFilter(image, filteringDegree);
	    ImageMatrix blurredImage = applyBlurFilter(grayscaleImage, filteringDegree);
	    ImageMatrix edgeDetectionImage = applySobelOperator(blurredImage); // Using Sobel Operator for Edge Detection
	
	    // Applying the thresholding to identify significant edges
	    int threshold = 10;
	    applyThresholding(edgeDetectionImage, threshold);
	
	    return edgeDetectionImage;
	    
	 }
	 
	 // Necessary methods of the Edge Detection Filter
	 protected static ImageMatrix applySobelOperator(ImageMatrix image) { // Method for Sobel Operator of Edge Detection Filter
		 
		 		// Initializing Sobel Operator matrices
		        int[][] sobelX = {
		            {-1, 0, 1},
		            {-2, 0, 2},
		            {-1, 0, 1}};

		        int[][] sobelY = {
		            {-1, -2, -1},
		            {0, 0, 0},
		            {1, 2, 1}};

		        int width = image.getWidth();
		        int height = image.getHeight();
		        ImageMatrix edgeDetectionImage = new ImageMatrix(width, height); // Creating a Image Matrix object with given width and height

		        // Sobel method
		        for (int i = 1; i < width - 1; i++) {
		            for (int j = 1; j < height - 1; j++) {
		                int gx = calculateGradient(image, i, j, sobelX);
		                int gy = calculateGradient(image, i, j, sobelY);
		                int gradient = (int) Math.sqrt(gx * gx + gy * gy);
		                
		                edgeDetectionImage.setRGB(i, j, ImageMatrix.convertRGB(gradient, gradient, gradient));
		            }
		        }

		        return edgeDetectionImage;
		    }

	 protected static int calculateGradient(ImageMatrix image, int x, int y, int[][] kernel) { // Method for calculating gradient of edge detection filter
		        int gradientOfImage = 0; // Initially zero

		        for (int i = -1; i <= 1; i++) {
		            for (int j = -1; j <= 1; j++) {
		                int pixel = image.getRGB(x + i, y + j); // Retrieving the RGB value of the pixel located at (x + i, y + j) 
		                int gray = (pixel >> 16) & 0xFF;  // Converting the RGB color to grayscale.
		                gradientOfImage += gray * kernel[i + 1][j + 1];
		            }
		        }

		        return gradientOfImage;
		    }

	 protected static void applyThresholding(ImageMatrix image, int threshold) { // Method for applying tresholding of Edge Detection Filter
		    int width = image.getWidth();
		    int height = image.getHeight();

		    for (int i = 0; i < width; i++) {
		        for (int j = 0; j < height; j++) {
		            int pixel = image.getRGB(i, j);
		            int gray = (pixel >> 16) & 0xFF; 

		            if (gray >= threshold) {
		            	
		                // Setting the pixel to white with RGB values
		                image.setRGB(i, j, ImageMatrix.convertRGB(255, 255, 255));
		            } else {
		            	
		                // Setting the pixel to black with RGB values
		                image.setRGB(i, j, ImageMatrix.convertRGB(0, 0, 0));
		            }
		        }
		    }
		}
	
	    public static ImageMatrix applyBrightnessFilter(ImageMatrix image, int filteringDegree) { // Method for brightness filter
	        int width = image.getWidth();
	        int height = image.getHeight();
	        
	        // According to filtering Degree, I assign a percentage of filtering
	        int filteringDegreePercentage;
	        
	        if (filteringDegree == 1) {
	        	filteringDegreePercentage = 20;
		    }
		    else if (filteringDegree == 2) {
		    	filteringDegreePercentage = 40;
		    }
		    else if (filteringDegree == 3) {
		    	filteringDegreePercentage = 60;
		    }
		    else if (filteringDegree == 4) {
		    	filteringDegreePercentage = 80;
		    }
		    else if (filteringDegree == 5) {
		    	filteringDegreePercentage = 100;
		    }
		    else {
		        throw new IllegalArgumentException("Invalid filtering degree: " + filteringDegree);
		    }
	        
	        ImageMatrix filteredImage = new ImageMatrix(width, height);

	        for (int x = 0; x < width; x++) {
	            for (int y = 0; y < height; y++) {
	            	// Getting the current RGB values of the pixel
	                int currentRed = image.getRed(x, y);
	                int currentGreen = image.getGreen(x, y);
	                int currentBlue = image.getBlue(x, y);

	                int adjustedRed = currentRed + filteringDegreePercentage;
	                int adjustedGreen = currentGreen + filteringDegreePercentage;
	                int adjustedBlue = currentBlue + filteringDegreePercentage;
	                
	                // Checking whether the RGB values are within the range
	                adjustedRed = Math.max(0, Math.min(255, adjustedRed));
	                adjustedGreen = Math.max(0, Math.min(255, adjustedGreen));
	                adjustedBlue = Math.max(0, Math.min(255, adjustedBlue));

	                filteredImage.setRGB(x, y, ImageMatrix.convertRGB(adjustedRed, adjustedGreen, adjustedBlue));
	            }
	        }
	        
	        return filteredImage;
	    }
	
	    public static ImageMatrix applyContrastFilter(ImageMatrix image, double filteringDegree) { // Method for Contrast Filter
	    	
	        int width = image.getWidth();
	        int height = image.getHeight();
	        
	        // According to filtering Degree, I assign a percentage of filtering
	        int filteringDegreePercentage;
	        
	        if (filteringDegree == 1) {
	        	filteringDegreePercentage = 20;
		    }
		    else if (filteringDegree == 2) {
		    	filteringDegreePercentage = 40;
		    }
		    else if (filteringDegree == 3) {
		    	filteringDegreePercentage = 60;
		    }
		    else if (filteringDegree == 4) {
		    	filteringDegreePercentage = 80;
		    }
		    else if (filteringDegree == 5) {
		    	filteringDegreePercentage = 100;
		    }
		    else {
		        throw new IllegalArgumentException("Invalid filtering degree: " + filteringDegree);
		    }

	        ImageMatrix filteredImage = new ImageMatrix(width, height);

	        for (int x = 0; x < width; x++) {
	            for (int y = 0; y < height; y++) {
	            	
	                // Getting the current RGB values of the pixel
	            	int currentBlue = image.getBlue(x, y);
	                int currentRed = image.getRed(x, y);
	                int currentGreen = image.getGreen(x, y);
	                
	                // Contrast adjustment 
	                int newBlue = adjustContrast(currentBlue, filteringDegreePercentage);
	                int newRed = adjustContrast(currentRed, filteringDegreePercentage);
	                int newGreen = adjustContrast(currentGreen, filteringDegreePercentage);
	                
	                // Checking whether the RGB values are within the range
	                newRed = Math.max(0, Math.min(255, newRed));
	                newGreen = Math.max(0, Math.min(255, newGreen));
	                newBlue = Math.max(0, Math.min(255, newBlue));

	                filteredImage.setRGB(x, y, ImageMatrix.convertRGB(newRed, newGreen, newBlue));
	            }
	        }

	        return filteredImage;
	    }

	    protected static int adjustContrast(int value, double amount) { // Method to adjust the contrast
	        double contrastAmount = (100.0 + amount) / 100.0;
	        contrastAmount *= contrastAmount;
	        return (int) Math.round(((value / 255.0 - 0.5) * contrastAmount + 0.5) * 255.0); // Special operation to adjust the contrast
	    }
	}






