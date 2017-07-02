package model;

import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

/**
 * @author Luiz Agner <agner@alunos.utfpr.edu.br>
 * 
 * A class that helps the rendering process
 */

public class ImageHelper {
	
	/**
	 * Saves the given image
	 * 
	 * @param image image to be saved
	 * @param filename filename for image to be saved
	 */
	public static void save(BufferedImage image, String filename) {
		try {
			if (!ImageIO.write(image, getExtension(filename), new File(filename)))
				System.err.println("Could not find an appropriate image format writer for the extension: " + getExtension(filename));
		} catch (Exception e) {
			System.err.println("Could not write image file: " + e.getMessage());
		}
	}
	
	/**
	 * Displays the given image
	 * 
	 * @param image image to be displayed
	 * @param filename filename for image to be displayed
	 */
	public static void display(BufferedImage image, String filename) {
		ImageIcon ii = new ImageIcon(image);
		JOptionPane.showMessageDialog(null, ii, filename, JOptionPane.PLAIN_MESSAGE);	
	}

	/**
	 * Retrieve extension of a filename
	 * 
	 * @param filename the filename from which the extension should be extracted
	 * @return file extension
	 */
	public static String getExtension(String filename) {
		int i = filename.lastIndexOf('.');
		if (i > 0) {
			return filename.substring(i + 1);
		} else
			return "";
	}
	
}
