package model;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

/**
 * @author Luiz Agner <agner@alunos.utfpr.edu.br>
 * 
 * A class that renders an image representing the graph graphically
 */

public class GraphPrinter {
	
	private int BORDER, SIZE;
	private Dimension dim;
	private Graphics2D graphic;
	private BufferedImage img;
	private String path;
	private ArrayList<Vertex> vertex;
	
	/**
	 * Constructor that defines the printer and the image
	 * 
	 * @param BORDER defines a border size for the image
	 * @param SIZE defines a SIZExSIZE image
	 * @param path given path and image name
	 * @param vertex the vertexes of the graph
	 */
	public GraphPrinter(int BORDER, int SIZE, String path, ArrayList<Vertex> vertex) {
		this.BORDER = BORDER;
		this.SIZE = SIZE;
		this.path = path;
		this.vertex = vertex;
	}
	
	/**
	 * Processes and saves the image
	 */
	public void print() {

	    SIZE = SIZE - 2*BORDER;
	    dim = new Dimension(1000, 800);
	    
	    img = new BufferedImage(dim.width, dim.height, BufferedImage.TYPE_INT_RGB);
	    graphic = img.createGraphics();
	    graphic.setColor(Color.BLACK);
	    graphic.fillRect(0, 0, dim.width, dim.height);

	    for(Vertex v1 : vertex) {
	    	graphic.setColor(Color.WHITE);
	    	for(Vertex v2 : v1.getAdjacent()){
	    		if(v1.getId() < v2.getId())
	    			 //draws the line between v1 and v2
	    			graphic.drawLine(v1.getX()+5, v1.getY()+5, v2.getX()+5, v2.getY()+5);
	    	}
	    }
	    
	    //some colors
	    int[][] rgb = {	{255, 0, 0}, 
	    				{0, 255, 0},
    				 	{0, 0, 255},
    				 	{255, 255, 0},
    				 	{255, 0, 255},
    				 	{0, 255, 255},
    				 	{255, 255, 255}};
	    
	    for(Vertex v : vertex) {
	    	//draws the vertex
	    	graphic.setColor(new Color(rgb[v.getColor()][0], rgb[v.getColor()][1], rgb[v.getColor()][2])); 
	    	graphic.fillOval(v.getX(), v.getY(), 10, 10); 
	    	//writes the vertex's index
	    	//graphic.setColor(Color.GREEN);
	    	Font font = new Font ("Monospaced", Font.BOLD, 15);
         	graphic.setFont(font);
	    	graphic.drawString("V" + Integer.toString(v.getId()), v.getX()-1, v.getY()-4); 
	    }
	    
	    //saves the image
	    ImageHelper.save(img, path);
		
	}
	
	/**
	 * Displays the image
	 */
	public void display() {
		//displays the image
		ImageHelper.display(img, path);
	}
	
}
