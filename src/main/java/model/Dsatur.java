package model;

import java.util.ArrayList;

/**
 * @author Luiz Agner <agner@alunos.utfpr.edu.br>
 * 
 * This class implements the actual DSATUR algorithm
 */

public class Dsatur {
	
	private static ArrayList<Vertex> computedVertex, possibleVertex;
	private static ArrayList<Boolean> color;
	private static Vertex tempVertex;
	
	public static ArrayList<Vertex> compute(ArrayList<Vertex> graph) {
		computedVertex = new ArrayList<>(); 
		possibleVertex = new ArrayList<>();
		color = new ArrayList<>();
		
		//first iteration only takes the vertex with the highest degree to compute
		graph.sort(new DegreeComparator()); //sort the vertexes according to their degree
		tempVertex = graph.get(0); //the first element is the one with the highest degree
		tempVertex.setColor(0); //sets the first color to it
		color.add(0, true); //update the list of colors
		tempVertex.updateAdjacents(0); //update its adjacent vertexes
		computedVertex.add(tempVertex); //add it to the list of computed vertexes
		graph.remove(tempVertex); //remove it from the main graph (now the graph is the remaining "subgraph")
		
		while(!graph.isEmpty()) {
			//the following iterations takes the vertex with the highest SATURATION degree
			graph.sort(new SatDegreeComparator()); //sort the vertexes according to their saturation degree
			int satAux = graph.get(0).getSatDegree(); //the first element is the one with the highest degree
			for(Vertex v : graph) { //but there might be more than one vertex with the same saturation degree 
				if(v.getSatDegree() == satAux)
					possibleVertex.add(v); //creates a list of the vertexes with the highest saturation degree
			}
			possibleVertex.sort(new DegreeComparator()); //sort it according to its degree, so there aren't any ties whatsoever
			tempVertex = possibleVertex.get(0); //maybe there're vertexes with the same degree as well but it doesn't matter, we can pick anyone then
			possibleVertex.clear(); //the vertex was already chosen, we can clear this list
			for(Vertex v : tempVertex.getAdjacent()) { //looks which colors the adjacent vertexes already have and sets that color as unavailable (false) 
				if(v.hasColor())
					color.set(v.getColor(), false);
			}
			
			boolean hasAvailableColor = false; 
			int availableColor = 0;
			//looks after the minimal color it can have
			for(int i = 0 ; i < color.size() && !hasAvailableColor ; i++) { 
				if(color.get(i) == true) {
					hasAvailableColor = true;
					availableColor = i;
				}
			}
			
			if(!hasAvailableColor) { //if there's none, then create a new color
				tempVertex.setColor(color.size());
				availableColor = color.size();
				color.add(availableColor, true);
			} else
				tempVertex.setColor(availableColor); //else set the minimal to the vertex
			
			tempVertex.updateAdjacents(availableColor);
			computedVertex.add(tempVertex);
			graph.remove(tempVertex);
			
			for(int i = 0 ; i < color.size() ; i++)
				color.set(i, true); //it assumes that the colors are all available for the next vertex
		}
		
		computedVertex.sort(new IdComparator()); //sort the vertexes according to its id
		
		return computedVertex;
		
	}
	
}
