package model;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

/**
 * @author Luiz Agner <agner@alunos.utfpr.edu.br>
 * 
 * This class handles all the input and output data
 */

public class GraphIO {
	
	private static ArrayList<Vertex> graph;
	private static Vertex tempVertex;
	private static FileReader fr;
	private static BufferedReader textReader;
	private static FileWriter fw;
	private static BufferedWriter textWriter;
	private static String line, id;

	public static ArrayList<Vertex> build(String pathIn) throws IOException {
		
		graph = new ArrayList<>();
		fr = new FileReader(pathIn);
		textReader = new BufferedReader(fr);
		id = "";
		
		while((line = textReader.readLine()) != null) {
			tempVertex = null;
			line += '\n'; //ensure it has an EOF
			for(int i = 0 ; i < line.length() ; i++){
				//gets the id reading only numbers, if something outside the number's range is found then it 
				//means that it's read the whole id for that vertex and will continue to add it to the graph
				if((line.charAt(i) >= 48) && (line.charAt(i) <= 57)) { //between 0 and 9 on the ASCII table
					id += line.charAt(i);
				}
				//after getting the id
				else if(id.length() > 0) {
					//if null then it is the first vertex of the line
					if(tempVertex == null) {
						tempVertex = new Vertex(Integer.parseInt(id));
						//was this vertex already added to the graph?
						if(!graphContainsVertex(tempVertex))
							graph.add(tempVertex);
						else
							tempVertex = instanceOfVertex(tempVertex);
					} else {
						Vertex auxVertex = new Vertex(Integer.parseInt(id)); //the adjacent vertex
						if(!graphContainsVertex(auxVertex))
							graph.add(auxVertex);
						else {
							auxVertex = instanceOfVertex(auxVertex);
						}
						tempVertex.addAdjacent(auxVertex);
						auxVertex.addAdjacent(tempVertex);
					}
					id = "";
				}
			}
		}
		
		textReader.close();
		fr.close();
		
		return graph;
		
	}
	
	public static void save(String pathOut, ArrayList<Vertex> graph) throws IOException {
		
		fw = new FileWriter(pathOut);
		textWriter = new BufferedWriter(fw);
		String out = "";
		
		for(Vertex v : graph) {
			out += v + "\n";
		}
		
		textWriter.write(out);
		textWriter.close();
		fw.close();
		
	}
	
	public static boolean graphContainsVertex(Vertex vertex) {
		boolean has = false;
		for(Vertex v : graph) {
			if(!has)
				has = (v.getId() == vertex.getId());
		}
		return has;
	}
	
	public static Vertex instanceOfVertex(Vertex vertex) {
		for(Vertex v : graph) {
			if(v.getId() == vertex.getId())
				return v;
		}
		return null;
	}
	
}
